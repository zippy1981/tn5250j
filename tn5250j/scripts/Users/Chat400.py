######################################################################
#                            chat400                                 #
#====================================================================#
# Code      : Peter Moore (jorjun@mac.com)                           #
#             Patrick Bielen (bielen@stafa.nl)                       #
# ================================================================== #
# Functionality : Script to send/receive messages to AS/400 users    #
# Discussions	: tn5250j-scripting@lists.sourceforge.net            #
######################################################################

import sys

from java.lang import *
import com.ibm.as400.access as acc
import com.ibm.as400.resource as rsc
from javax import swing
import java.awt as awt

#**************************************************************************
CHATQ = "/QSYS.LIB/QGPL.LIB/chat400.DTAQ"
KEYLEN = 10
#==========================================================================
class Poller(Runnable):
#==========================================================================
	def __init__(self, parent):
		self.parent = parent

	def run(self):
		done = 0
		dq = acc.KeyedDataQueue(self.parent.as400, CHATQ)
		curUsr = self.parent.as400.getUserId() # Current User ID.
		if not dq.exists():
			dq.create(KEYLEN, 512)

		while not done:
			try:
				entry = dq.read(curUsr,-1,"EQ")
				self.parent.rpyTxt.append(entry.getString()+"\n")
				self.parent.rpyTxt.setCaretPosition(len(self.parent.rpyTxt.getText()))
				if self.parent.getState() == swing.JFrame.ICONIFIED:
					self.parent.state=(swing.JFrame.NORMAL)
				self.parent.chatTxt.requestFocus()
				self.parent.show()
			except:
				done=1
				exc=sys.exc_info()
				print "Thread interrupted at line ", exc[2].tb_lineno
				print exc[0]
				print "type = %s"%type(exc[0])

#==========================================================================
class Chat400(swing.JFrame, awt.event.WindowListener):
#==========================================================================
	def __init__(self):
		swing.JFrame.__init__(self, title=\
							  "CHAT400 - An AS/400 Instant Messenger", \
							  resizable=0)
		try:
			self.setDefaultCloseOperation(swing.WindowConstants.EXIT_ON_CLOSE) # JDK1.4?
		except:
			None 
	def run(server, name):
		self.as400 = acc.AS400(server, name)

		# Get user profile descriptions==> usrDct
		rUsrLst = rsc.RUserList(self.as400)  
		rUsrLst.open()
		rUsrLst.waitForComplete()
		self.usrDct = {}
		for idx in range(rUsrLst.getListLength()):
				tmp_rUsr = rUsrLst.resourceAt(idx)
				key_usr = tmp_rUsr.getAttributeValue(rsc.RUser.USER_PROFILE_NAME)
				if key_usr.startswith('Q') or key_usr == 'FAXSTAR':
					continue
				tmp_usrText = tmp_rUsr.getAttributeValue(rsc.RUser.TEXT_DESCRIPTION)
				self.usrDct[key_usr] = tmp_usrText
		rUsrLst.close()
		
		# Interactive job list		
		self.jobLst = rsc.RJobList(self.as400)
		self.jobLst.setSelectionValue(rsc.RJobList.PRIMARY_JOB_STATUSES, \
									  rsc.RJob.JOB_STATUS_ACTIVE)
		self.jobLst.setSelectionValue(rsc.RJobList.JOB_TYPE, \
									  rsc.RJob.JOB_TYPE_INTERACTIVE)
		self.jobLst.setSortValue([rsc.RJob.USER_NAME, rsc.RJob.JOB_NAME])

		# Thread of execution to receive instant messages
		self.polchat = Thread(Poller(self))

		# Form GUI
		self.contentPane.setLayout(awt.GridBagLayout())
		self.addWindowListener(self)
		self.chkActive = swing.JCheckBox("Show only Active Users", 1)

		self.chatTxt = swing.JTextArea(5, 30, lineWrap=1, wrapStyleWord=1)
		self.rpyTxt = swing.JTextArea(10, 30, lineWrap=1, wrapStyleWord=1)
		self.users = swing.JComboBox(preferredSize=(250, 25), minimumSize=(250, 25))

		self.showGui()

	def windowClosed(self, event):
		self.polchat.interrupt()
		self.dispose()
	def windowClosing(self, event):
		System.setProperty("chat400", "0")
	def windowActivated(self, event):
		None
	def windowDeactivated(self, event):
		None
	def windowOpened(self, event):
		None
	def windowIconified(self, event):
		None
	def windowDeiconified(self, event):
		None

	#**************************
	#	Retrieve send-user list
	#**************************
	def rtvIntJobs(self):
		# Get interactive job list
		self.jobLst.open()
		self.jobLst.waitForComplete()
		self.jobDct = {}
		for idx in range(self.jobLst.getListLength()):
			tmp_job = self.jobLst.resourceAt(idx)
			key_usr = tmp_job.getAttributeValue(rsc.RJob.USER_NAME)
			if not self.jobDct.has_key(key_usr):
				self.jobDct[key_usr] = tmp_job
		self.jobLst.close()
		
		keys = self.usrDct.keys() 
		keys.sort()
		for key_usr in keys:
			sts = ' '
			if self.chkActive.isSelected():		# Show only interactive users
				if not self.jobDct.has_key(key_usr):
					continue
			elif self.jobDct.has_key(key_usr):  # Show all users: interactive users highlighted
					sts = ' *'
		
			try:
				fullName = self.usrDct[key_usr]
			except:
				fullName  = "*NONE"

				menuItem = '%s: %-30s %s'%(key_usr,fullName, sts)  # Left adjust name :30 chars max=======

			self.users.addItem(menuItem)

	#**************************
	#	Send message
	#**************************
	def btnActSnd(self, event):
		cmd = acc.CommandCall(self.as400)
		curUsr = self.as400.getUserId()
		selected = self.users.getSelectedItem()
		sndUsr =selected.split(':')[0]
		chatTxt = self.chatTxt.getText()
		dq = acc.KeyedDataQueue(self.as400, CHATQ)
		if not dq.exists():
			dq.create(KEYLEN, 512)
		try:
			dq.write(sndUsr, "%s::%s"%(curUsr, chatTxt) )
			if not curUsr == sndUsr:
				self.rpyTxt.append("%s -> %s\n%s\n"%(curUsr, sndUsr, chatTxt))
				self.rpyTxt.setCaretPosition( len(self.rpyTxt.getText()) )
			self.statusTxt.text='Message send successfull'
			self.chatTxt.selectAll()
			self.chatTxt.cut()
		except:
			self.statusTxt.text='Message send Failed - Contact your system-operator.'
		self.chatTxt.requestFocus()

	#**************************
	def btnActRef(self, event):
		self.users.removeAllItems()
		self.rtvIntJobs()
	#**************************
	def showGui(self):
		self.rtvIntJobs()

		self.btnRef = swing.JButton("Refresh List", \
									actionPerformed = self.btnActRef, \
									minimumSize=(100,25), \
									preferredSize=(100, 25))
		self.btnRef.setMnemonic('R')
		self.btnSnd = swing.JButton("Send Message", \
									actionPerformed = self.btnActSnd)
		self.btnSnd.setMnemonic('S')
		self.label1 = swing.JLabel("Send To:", minimumSize=(50, 25), \
								   preferredSize=(50, 25))
		self.rootPane.setDefaultButton(self.btnSnd)
		self.rpyTxt.setEditable(0)		# <Scrollable message reply text area>
		self.statusTxt = swing.JTextField(text='Welcome to CHAT400 - An AS/400 Instant Messenger',\
										  editable=0, horizontalAlignment=swing.JTextField.CENTER)

		gbc = awt.GridBagConstraints()
		# Build the screen
		# Label 'send to'
		gbc.insets = awt.Insets(10, 10, 5, 5)
		self.contentPane.add(self.label1, gbc)
		# Combobox list of users
		gbc.insets = awt.Insets(10, 5, 5, 5)
		self.contentPane.add(self.users, gbc)
		# Refresh User-List Button
		gbc.insets = awt.Insets(10, 5, 5, 10)
		self.contentPane.add(self.btnRef, gbc)
		# Active Users checkbox
		gbc.gridx = 0
		gbc.gridwidth = 3
		gbc.insets = awt.Insets(0, 0, 5, 0)
		self.contentPane.add(self.chkActive, gbc)
		# Send Message Button
		gbc.gridx = 0
		gbc.gridwidth = 3
		gbc.insets = awt.Insets(5, 0, 5, 0)
		self.contentPane.add(self.btnSnd, gbc)
		# Build the SplitPane (2 scrollpanes)
		scrollPane1 = swing.JScrollPane(self.chatTxt, swing.JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, \
		swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER)
		scrollPane1.setViewportView(self.chatTxt)
		scrollPane2 = swing.JScrollPane(self.rpyTxt, swing.JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, \
		swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER)
		scrollPane2.setViewportView(self.rpyTxt)
		splitPane = swing.JSplitPane(swing.JSplitPane.VERTICAL_SPLIT, scrollPane1, scrollPane2)
		# Add the SplitPane
		gbc.gridx = 0
		gbc.gridwidth = 3
		gbc.fill = awt.GridBagConstraints.HORIZONTAL
		gbc.insets = awt.Insets(5, 10, 10, 10)
		self.contentPane.add(splitPane, gbc)
		# Add a status-textfield on the bottom, to display status or errors
		gbc.gridx = 0
		gbc.gridwidth = 3
		gbc.fill = awt.GridBagConstraints.HORIZONTAL
		gbc.insets = awt.Insets(0, 10, 10, 10)
		self.contentPane.add(self.statusTxt, gbc)

		self.pack()
		self.polchat.start()

		self.show()

isRunning = System.getProperty("chat400")
if not isRunning == '1':
	System.setProperty("chat400", "1")
	try:
		server = _session.getHostName()
		name = _session.getAllocDeviceName()
	except:
		server = '' ; name = ''
	chatter=Chat400()
	chatter.run(server, name)
#	if passw != '':
#		chatter.run(server, name, passw)
#	else:
#		chatter.run(server, name)