package org.tn5250j;
/**
 * Title: tn5250J
 * Copyright:   Copyright (c) 2001
 * Company:
 * @author  Kenneth J. Pouncey
 * @version 0.4
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

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import org.tn5250j.event.SessionJumpListener;
import org.tn5250j.event.SessionJumpEvent;
import org.tn5250j.event.SessionListener;
import org.tn5250j.event.SessionChangeEvent;
import org.tn5250j.interfaces.GUIViewInterface;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameListener;
import javax.swing.event.InternalFrameEvent;

public class Gui5250MDIFrame extends Gui5250Frame implements GUIViewInterface,
                                                    ChangeListener,
                                                    TN5250jConstants,
                                                    SessionListener,
                                                    SessionJumpListener {

   BorderLayout borderLayout1 = new BorderLayout();
   My5250 me;
   JTabbedPane sessionPane = new JTabbedPane();
   private SessionManager manager;
   private ImageIcon focused = null;
   private ImageIcon unfocused = null;
   private int selectedIndex = 0;
   private boolean packFrame = false;
   private int sequence;
   private JDesktopPane desktop;
   static int openFrameCount = 0;

   //Construct the frame
   public Gui5250MDIFrame(My5250 m) {
      super(m);
      me = m;
      enableEvents(AWTEvent.WINDOW_EVENT_MASK);
      try  {
         jbInit();
      }
      catch(Exception e) {
         e.printStackTrace();
      }
   }

   //Component initialization
  private void jbInit() throws Exception  {

      desktop = new JDesktopPane();
      setContentPane(desktop);

      // Install our custom desktop manager
      desktop.setDesktopManager(new MyDesktopMgr());

      if (sequence > 0)
         setTitle("tn5250j <" + sequence + ">- " + tn5250jRelease + tn5250jVersion + tn5250jSubVer);
      else
         setTitle("tn5250j - " + tn5250jRelease + tn5250jVersion + tn5250jSubVer);

      if (packFrame)
         pack();
      else
         validate();

   }

   public void centerFrame() {

      //Center the window
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      Dimension frameSize = getSize();
      if (frameSize.height > screenSize.height)
         frameSize.height = screenSize.height;
      if (frameSize.width > screenSize.width)
         frameSize.width = screenSize.width;

      setLocation((screenSize.width - frameSize.width) / 2,
                     (screenSize.height - frameSize.height) / 2);


   }

   //Overridden so we can exit on System Close
   protected void processWindowEvent(WindowEvent e) {
      super.processWindowEvent(e);
      if(e.getID() == WindowEvent.WINDOW_CLOSING) {
         me.closingDown(this);
      }
   }


   public void update(Graphics g) {
      paint(g);
   }

   public void onSessionJump(SessionJumpEvent jumpEvent) {

      switch (jumpEvent.getJumpDirection()) {

         case JUMP_PREVIOUS:
            prevSession();
            break;
         case JUMP_NEXT:
            nextSession();
            break;
      }
   }

   private void nextSession() {

      JInternalFrame[] frames = desktop.getAllFrames();
      JInternalFrame miv = desktop.getSelectedFrame();
      int index = desktop.getIndexOf(miv);
      if (index < desktop.getComponentCount() - 1) {
         try {
            frames[index + 1].setSelected(true);
         }
         catch (java.beans.PropertyVetoException e) {
            System.out.println(e.getMessage());
         }
      }
      else {
         try {
            frames[0].setSelected(true);
         }
         catch (java.beans.PropertyVetoException e) {
            System.out.println(e.getMessage());
         }

      }


   }

   private void prevSession() {

      JInternalFrame[] frames = desktop.getAllFrames();
      JInternalFrame miv = desktop.getSelectedFrame();
      int index = desktop.getIndexOf(miv);
      if (index == 0) {
//         desktop.setSelectedFrame(frames[frames.length - 1]);
         try {
            frames[frames.length - 1].setSelected(true);
            frames[frames.length - 1].repaint();
         }
         catch (java.beans.PropertyVetoException e) {
            System.out.println(e.getMessage());
         }
      }
      else {
         try {
            frames[index - 1].setSelected(true);
            frames[index - 1].repaint();
         }
         catch (java.beans.PropertyVetoException e) {
            System.out.println(e.getMessage());
         }

      }

   }

   public void setIcons(ImageIcon focused, ImageIcon unfocused) {

   }

   public void stateChanged(ChangeEvent e) {


   }

   public void addSessionView(String tabText,Session session) {

      MyInternalFrame frame = new MyInternalFrame();
      frame.setVisible(true);
      desktop.add(frame);
      frame.setContentPane(session);
      try {
         frame.setSelected(true);
      } catch (java.beans.PropertyVetoException e) {}
      session.addSessionListener(this);
      session.addSessionJumpListener(this);

   }

   public void removeSessionView(Session targetSession) {

      JInternalFrame[] frames = desktop.getAllFrames();


   }

   public int getSessionViewCount() {

      return desktop.getAllFrames().length;
   }

   public Session getSessionAt( int index) {

      JInternalFrame[] frames = desktop.getAllFrames();

      return (Session)frames[index].getContentPane();
   }

   public void onSessionChanged(SessionChangeEvent changeEvent) {

   }

   public boolean containsSession(Session session) {

      return desktop.getIndexOf(session) >= 0;

   }

   public class MyInternalFrame extends JInternalFrame {
   //    static int openFrameCount = 0;
      static final int xOffset = 30, yOffset = 30;

      public MyInternalFrame() {
         super("#" + (++openFrameCount),
              true, //resizable
              true, //closable
              true, //maximizable
              true);//iconifiable

         //...Create the GUI and put it in the window...

         //...Then set the window size or call pack...
         setSize(300,300);

         //Set the window's location.
         setLocation(xOffset*openFrameCount, yOffset*openFrameCount);

         addInternalFrameListener(new InternalFrameAdapter() {
//               public void internalFrameActivated(InternalFrameEvent e) {
//                  paintme();
//   //                  displayMessage("Internal frame activated", e);
//              }

      public void internalFrameClosing(InternalFrameEvent e) {
                  displayMessage("Internal frame closing", e);
               }

               public void internalFrameClosed(InternalFrameEvent e) {
                  displayMessage("Internal frame closed", e);
               }

               public void internalFrameOpened(InternalFrameEvent e) {
                  displayMessage("Internal frame opened", e);
               }

               public void internalFrameIconified(InternalFrameEvent e) {
                  displayMessage("Internal frame iconified", e);
               }

               public void internalFrameDeiconified(InternalFrameEvent e) {
                  displayMessage("Internal frame deiconified", e);
               }

               public void internalFrameActivated(InternalFrameEvent e) {
                  displayMessage("Internal frame activated", e);
                  repaint();
               }

               public void internalFrameDeactivated(InternalFrameEvent e) {
                  displayMessage("Internal frame deactivated", e);
               }


            });

          }
         void displayMessage(String prefix, InternalFrameEvent e) {
            String s = prefix + ": " + e.getSource();
            System.out.println(s + '\n');
         }


         public void paintme() {
            repaint();
         }
         public void update(Graphics g) {
            paint(g);
            System.out.println("update");
         }

   }

   // A DesktopManager that keeps its frames inside the desktop.
     public class MyDesktopMgr extends DefaultDesktopManager {

       // We'll tag internal frames that are being resized using a client
       // property with the name RESIZING.  Used in setBoundsForFrame().
       protected static final String RESIZING = "RESIZING";

       public void beginResizingFrame(JComponent f, int dir) {
         f.putClientProperty(RESIZING, Boolean.TRUE);
       }

       public void endResizingFrame(JComponent f) {
         f.putClientProperty(RESIZING, Boolean.FALSE);
       }

       // This is called any time a frame is moved or resized.  This
       // implementation keeps the frame from leaving the desktop.
       public void setBoundsForFrame(JComponent f, int x, int y, int w, int h) {
         if (f instanceof JInternalFrame == false) {
           super.setBoundsForFrame(f, x, y, w, h); // only deal w/internal frames
         }
         else {
           JInternalFrame frame = (JInternalFrame)f;

           // Figure out if we are being resized (otherwise it's just a move)
           boolean resizing = false;
           Object r = frame.getClientProperty(RESIZING);
           if (r != null && r instanceof Boolean) {
             resizing = ((Boolean)r).booleanValue();
           }

           JDesktopPane desk = frame.getDesktopPane();
           Dimension d = desk.getSize();

           // Nothing all that fancy below, just figuring out how to adjust
           // to keep the frame on the desktop.
           if (x < 0) {              // too far left?
             if (resizing)
               w += x;               // don't get wider!
             x=0;                    // flush against the left side
           }
           else {
             if (x+w>d.width) {      // too far right?
              if (resizing)
                w = d.width-x;       // don't get wider!
              else
                x = d.width-w;       // flush against the right side
             }
           }
           if (y < 0) {              // too high?
             if (resizing)
               h += y;               // don't get taller!
             y=0;                    // flush against the top
           }
           else {
             if (y+h > d.height) {   // too low?
               if (resizing)
                 h = d.height - y;   // don't get taller!
               else
                 y = d.height-h;     // flush against the bottom
             }
           }

           // Set 'em the way we like 'em
           super.setBoundsForFrame(f, x, y, w, h);
         }
       }
     }

}
