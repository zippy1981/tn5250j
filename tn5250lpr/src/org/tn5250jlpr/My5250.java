package org.tn5250jlpr;
/**
 * Title: tn5250Jlpr
 * Copyright:   Copyright (c) 2001
 * Company:
 * @author  Kenneth J. Pouncey
 * @version 0.1
 *
 * Description:
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this software; see the file COPYING.  If not, write to
 * the Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307 USA
 *
 */

import java.util.*;
import java.io.*;
import javax.swing.UIManager;
import javax.swing.*;
import org.tn5250jlpr.tools.*;
import org.tn5250jlpr.event.*;
import java.net.*;


public class My5250 implements BootListener,TN5250jConstants,SessionListener {

   // protected Gui5250Frame frame1;
   private String[] sessionArgs = null;
   private static Properties sessions = new Properties();
   // private static ImageIcon focused;
   // private static ImageIcon unfocused;
   // private static ImageIcon tnicon;

   private static BootStrapper strapper = null;
   private SessionManager manager;
   // private static Vector frames;
   // private static boolean useMDIFrames;

   My5250 () {
      try  {
         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      }
      catch(Exception e) {
      }

      loadSessions();

      // focused = createImageIcon("focused.gif");
      // unfocused = createImageIcon("unfocused.gif");
      // tnicon = createImageIcon("tnicon.jpg");

      // sets the starting frame type.  At this time there are tabs which is
      //    default and Multiple Document Interface.
      // startFrameType();

      // frames = new Vector();
// 
      // newView();

      setDefaultLocale();
      manager = new SessionManager();
      manager.setController(this);
   }

   /**
    * Check if there are any other instances of tn5250j running
    */
   static private boolean checkBootStrapper (String[] args) {

      try {
         Socket boot = new Socket("localhost", 3037);

         PrintWriter out = new PrintWriter(boot.getOutputStream(), true);

         // parse args into a string to send to the other instance of
         //    tn5250j
         String opts = null;
         for (int x = 0;x < args.length; x++) {
            if (opts != null)
               opts += args[x] + " ";
            else
               opts = args[x] + " ";
         }
         out.println(opts);
         out.flush();
         out.close();
         boot.close();
         return true;

      }
      catch (UnknownHostException e) {
         System.err.println("localhost not known.");
      }
      catch (IOException e) {
         System.err.println("No other instances of tn5250Jlpr running.");
      }

      return false;
   }

   public void bootOptionsReceived(BootEvent bootEvent) {

      System.out.println(" boot options received " + bootEvent.getNewSessionOptions());

      // If the options are not equal to the string 'null' then we have
      //    boot options
      if (!bootEvent.getNewSessionOptions().equals("null")) {
         // check if a session parameter is specified on the command line
         String[] args = new String[NUM_PARMS];
         parseArgs(bootEvent.getNewSessionOptions(), args);


         if (isSpecified("-s",args)) {

            String sd = getParm("-s",args);
            if (sessions.containsKey(sd)) {
               parseArgs(sessions.getProperty(sd), args);
               final String[] args2 = args;
               final String sd2 = sd;
               SwingUtilities.invokeLater(
                  new Runnable () {
                     public void run() {
                        newSession(sd2,args2);

                     }
                  }
               );
            }
         }
         else {

            if (args[0].startsWith("-")) {
               SwingUtilities.invokeLater(
                  new Runnable () {
                     public void run() {
                        startNewSession();

                     }
                  }
               );
            }
            else {
               final String[] args2 = args;
               final String sd2 = args[0];
               SwingUtilities.invokeLater(
                  new Runnable () {
                     public void run() {
                        newSession(sd2,args2);

                     }
                  }
               );
            }
         }
      }
      else {
         SwingUtilities.invokeLater(
            new Runnable () {
               public void run() {
                  startNewSession();

               }
            }
         );
      }
   }

   static public void main(String[] args) {

      // if (isSpecified("-MDI",args)) {
         // useMDIFrames = true;
      // }

      if (!isSpecified("-nc",args)) {

         if (!checkBootStrapper(args)) {

            // if we did not find a running instance and the -d options is
            //    specified start up the bootstrap deamon to allow checking
            //    for running instances
            if (isSpecified("-d",args)) {
               strapper = new BootStrapper();

               strapper.start();
            }
         }
         else {

            System.exit(0);
         }
      }

      My5250 m = new My5250();

      if (strapper != null)
         strapper.addBootListener(m);

      if (args.length > 0) {

         // if (isSpecified("-width",args) ||
               // isSpecified("-height",args)) {
            // int width = m.frame1.getWidth();
            // int height = m.frame1.getHeight();
// 
            // if (isSpecified("-width",args)) {
               // width = Integer.parseInt(m.getParm("-width",args));
            // }
            // if (isSpecified("-height",args)) {
               // height = Integer.parseInt(m.getParm("-height",args));
            // }
// 
            // m.frame1.setSize(width,height);
            // m.frame1.centerFrame();
// 
// 
         // }

          /**
           * @todo this crap needs to be rewritten it is a mess
           */
         if (args[0].startsWith("-")) {

            // check if a session parameter is specified on the command line
            if (isSpecified("-s",args)) {

               String sd = getParm("-s",args);
               if (sessions.containsKey(sd)) {
                  sessions.setProperty("emul.default",sd);
               }
               else {
                  // args = null;
		  System.exit(0);
               }

            }

            // check if a locale parameter is specified on the command line
            if (isSpecified("-L",args)) {

               Locale.setDefault(parseLocal(getParm("-L",args)));
               LangTool.init();
               if (args[0].startsWith("-")) {

                  m.sessionArgs = null;
               }
               else {
                  // m.frame1.setVisible(true);
                  LangTool.init();
                  m.sessionArgs = args;
               }
            }
            else {
               LangTool.init();
               if (isSpecified("-s",args))
                  m.sessionArgs = args;
               else
                  m.sessionArgs = null;
            }
         }
         else {

            if (isSpecified("-dn",args)) {
		    LangTool.init();
		    m.sessionArgs = args;
	    }
	    else 
		 System.exit(0);

	}
      }
      else {
         // LangTool.init();
         // m.sessionArgs = null;
	 System.exit(0);
      }

      if (m.sessionArgs != null) {

         // BEGIN
         // 2001/09/19 natural computing MR
         Vector os400_sessions = new Vector();
         Vector session_params = new Vector();

         for (int x = 0; x < args.length; x++) {

            if (args[x].equals("-s")) {
               x++;
               if (args[x] != null && sessions.containsKey(args[x])) {
                  os400_sessions.addElement(args[x]);
               }
	       else{
                  x--;
                  session_params.addElement(args[x]);
               }
            }
	    else{
               session_params.addElement(args[x]);
            }

         }

         for (int x = 0; x < session_params.size(); x++)
            m.sessionArgs[x] = session_params.elementAt(x).toString();

         m.startNewSession();

         for (int x = 1; x < os400_sessions.size(); x++ ) {
            String sel = os400_sessions.elementAt(x).toString();

            // if (!m.frame1.isVisible())
               // m.frame1.setVisible(true);

            m.sessionArgs = new String[NUM_PARMS];
            m.parseArgs(sessions.getProperty(sel),m.sessionArgs);
            m.newSession(sel,m.sessionArgs);
         }
         // 2001/09/19 natural computing MR
         // END
      }
      else {
         m.startNewSession();
      }

      while (true) {



      }
   }

   private void setDefaultLocale () {

      if (sessions.containsKey("emul.locale")) {
         Locale.setDefault(parseLocal((String)sessions.getProperty("emul.locale")));
      }

   }

   static private String getParm(String parm, String[] args) {

      for (int x = 0; x < args.length; x++) {

         if (args[x].equals(parm))
            return args[x+1];

      }
      return null;
   }

   static boolean isSpecified(String parm, String[] args) {

      if (args == null)
         return false;

      for (int x = 0; x < args.length; x++) {

         if (args[x] != null && args[x].equals(parm))
            return true;

      }
      return false;
   }

   static String getDefaultSession() {

      if (sessions.containsKey("emul.default")) {
         return (String)sessions.getProperty("emul.default");
      }
      else {
         return null;
      }
   }

   void startNewSession() {

      int result = 2;
      String sel = "";

      if (sessionArgs != null && !sessionArgs[0].startsWith("-"))
         sel = sessionArgs[0];
      else {
         sel = getDefaultSession();
      }

      Sessions sess = manager.getSessions();

      if (sel != null && sess.getCount() == 0
                  && sessions.containsKey(sel)){
         sessionArgs = new String[NUM_PARMS];
         parseArgs((String)sessions.getProperty(sel), sessionArgs);
      }

       if (sessionArgs == null  || sess.getCount() > 0
                || sessions.containsKey("emul.showConnectDialog")) {
 
          sel = getConnectSession();

          if (sel != null) {
             String selArgs = sessions.getProperty(sel);
             sessionArgs = new String[NUM_PARMS];
             parseArgs(selArgs, sessionArgs);
 
             newSession(sel,sessionArgs);
          }
          else {
             if (sess.getCount() == 0)
                System.exit(0);
          }
 
       }
       else {

         newSession(sel,sessionArgs);

       }
    }

/**
 * Method getConnectSession.
 * @return String
 */
private String getConnectSession() {
	return null;
}


   // private String getConnectSession () {
// 
      // Connect sc = new Connect(frame1,LangTool.getString("ss.title"),sessions);
// 
      // // load the new session information from the session property file
      // loadSessions();
      // return sc.getConnectKey();
   // }

   synchronized void newSession(String sel,String[] args) {

      Properties sesProps = new Properties();

      String propFileName = null;
      String session = args[0];

      // Start loading properties
      sesProps.put(SESSION_HOST,session);

      // if (isSpecified("-e",args))
         // sesProps.put(SESSION_TN_ENHANCED,"1");
// 
      if (isSpecified("-p",args)) {
         sesProps.put(SESSION_HOST_PORT,getParm("-p",args));
      }

      if (isSpecified("-f",args))
         propFileName = getParm("-f",args);

      if (isSpecified("-cp",args))
         sesProps.put(SESSION_CODE_PAGE ,getParm("-cp",args));

      if (isSpecified("-o",args))
         sesProps.put(SESSION_OUTPUT,getParm("-o",args));
// 
      // if (isSpecified("-132",args))
         // sesProps.put(SESSION_SCREEN_SIZE,SCREEN_SIZE_27X132_STR);
      // else
         // sesProps.put(SESSION_SCREEN_SIZE,SCREEN_SIZE_24X80_STR);

      // are we to use a socks proxy
      if (isSpecified("-usp",args)) {

         // socks proxy host argument
         if (isSpecified("-sph",args)) {
            sesProps.put(SESSION_PROXY_HOST ,getParm("-sph",args));
         }

         // socks proxy port argument
         if (isSpecified("-spp",args))
            sesProps.put(SESSION_PROXY_PORT ,getParm("-spp",args));
      }

      // check if device name is specified
      if (isSpecified("-dn",args))
         sesProps.put(SESSION_DEVICE_NAME ,getParm("-dn",args));

      Session s = manager.openSession(sesProps,propFileName,sel);

      // if (!frame1.isVisible())
         // frame1.setVisible(true);
      // else {
         // if (isSpecified("-noembed",args)) {
            // newView();
            // frame1.setVisible(true);
         // }
      // }
// 
      // if (isSpecified("-t",args))
         // frame1.addSessionView(sel,s);
      // else
         // frame1.addSessionView(session,s);

      s.connect();


   }


   // void closingDown(Session targetSession) {
// 
      // closingDown(getParentView(targetSession));
   // }

   // void closingDown(Gui5250Frame view) {
// 
      // Session jf = null;
      // Sessions sess = manager.getSessions();
// 
      // System.out.println("number of active sessions we have " + sess.getCount());
      // int x = 0;
// 
      // while (view.getSessionViewCount() > 0) {
// 
         // jf = view.getSessionAt(0);
// 
         // System.out.println("session found and closing down");
         // manager.closeSession(jf);
         // view.removeSessionView(jf);
         // System.out.println("disconnecting socket");
         // System.out.println("socket closed");
         // jf = null;
// 
      // }
// 
// 
      // sessions.setProperty("emul.frame" + view.getFrameSequence(),
                                    // view.getX() + "," +
                                    // view.getY() + "," +
                                    // view.getWidth() + "," +
                                    // view.getHeight());
// 
      // frames.remove(view);
      // view.dispose();
// 
      // System.out.println("number of active sessions we have after shutting down " + sess.getCount());
// 
      // if (sess.getCount() == 0) {
         // try {
            // FileOutputStream out = new FileOutputStream("sessions");
            // // save off the width and height to be restored later
            // sessions.setProperty("emul.width",Integer.toString(view.getWidth()));
            // sessions.setProperty("emul.height",Integer.toString(view.getHeight()));
// 
            // sessions.store(out,"------ Defaults --------");
         // }
         // catch (FileNotFoundException fnfe) {}
         // catch (IOException ioe) {}
// 
         // if (strapper != null) {
            // strapper.interrupt();
         // }
         // System.exit(0);
      // }
// 
// 
   // }

   // protected void closeSession(Session targetSession) {
// 
      // Gui5250Frame f = getParentView(targetSession);
      // if (f == null)
         // return;
      // int tabs = f.getSessionViewCount();
      // Sessions sessions = manager.getSessions();
      // Session session = null;
// 
      // if (tabs > 1) {
// 
         // if ((sessions.item(targetSession)) != null) {
// 
            // f.removeSessionView(targetSession);
            // manager.closeSession(targetSession);
            // targetSession = null;
// 
         // }
      // }
      // else {
         // closingDown(f);
      // }
   // }

   protected void parseArgs(String theStringList, String[] s) {
      int x = 0;
      StringTokenizer tokenizer = new StringTokenizer(theStringList, " ");
      while (tokenizer.hasMoreTokens()) {
         s[x++] = tokenizer.nextToken();
      }
   }

   protected static Locale parseLocal(String localString) {
      int x = 0;
      String[] s = {"","",""};
      StringTokenizer tokenizer = new StringTokenizer(localString, "_");
      while (tokenizer.hasMoreTokens()) {
         s[x++] = tokenizer.nextToken();
      }
      return new Locale(s[0],s[1],s[2]);
   }

   protected static void loadSessions() {

      FileInputStream in = null;
      try {
         in = new FileInputStream("sessions");
         sessions.load(in);

      }
      catch (FileNotFoundException fnfe) {System.out.println(fnfe.getMessage());}
      catch (IOException ioe) {System.out.println(ioe.getMessage());}

   }

   public void onSessionChanged(SessionChangeEvent changeEvent) {

      Session ses = (Session)changeEvent.getSource();
      System.out.println("changeEvent: " + ses);
      System.out.println("evento: " + changeEvent.getState());

      switch (changeEvent.getState()) {
         case STATE_REMOVE:
            closeSession(ses);
            break;
      }
   }

   protected void closeSession(Session targetSession) {

      Sessions sessions = manager.getSessions();
      Session session = null;

         if ((sessions.item(targetSession)) != null) {

      System.out.println("Sto chiudendo la sessione");
            manager.closeSession(targetSession);
            targetSession = null;

         }
   }



   /**
    * This routine will extract image resources from jar file and create
    * an ImageIcon
    */
   // protected ImageIcon createImageIcon (String image) {
      // URL file=null;
// 
      // try {
         // ClassLoader cl = this.getClass().getClassLoader();
         // file = cl.getResource(image);
// 
      // }
      // catch (Exception e) {
         // System.err.println(e);
      // }
      // return new ImageIcon( file);
   // }


}