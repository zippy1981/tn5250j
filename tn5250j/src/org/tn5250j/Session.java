/*
 * @(#)Session.java
 * Copyright:    Copyright (c) 2001
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
package org.tn5250j;

import java.util.*;
import javax.swing.*;
import java.awt.Rectangle;

import org.tn5250j.*;
import org.tn5250j.interfaces.SessionInterface;
import org.tn5250j.event.SessionListener;
import org.tn5250j.event.SessionChangeEvent;
import org.tn5250j.scripting.InterpreterDriverManager;

/**
 * A host session
 */
public class Session extends Gui5250 implements SessionInterface,TN5250jConstants {

   private String configurationResource;
   private String sessionName;
   private boolean connected;
   private int sessionType;
   private Properties sesProps;
   private Vector listeners;
   private SessionChangeEvent sce;
   private String sslType;
   private boolean firstScreen;
   private char[] signonSave;

   public Session (Properties props, String configurationResource,
                     String sessionName,
                     SessionConfig config) {

      super(config);
      this.configurationResource = configurationResource;
      this.sessionName = sessionName;
      sesProps = props;
      sce = new SessionChangeEvent(this);

   }

   public String getConfigurationResource() {

      return configurationResource;

   }

   public SessionConfig getConfiguration() {

      return sesConfig;
   }

   public SessionManager getSessionManager() {
      return SessionManager.instance();
   }

   public boolean isConnected() {

      return vt.isConnected();

   }

   public boolean isOnSignOnScreen() {

      // check to see if we should check.
      if (firstScreen) {

         char[] so = screen.getScreenAsChars();
         int size = signonSave.length;

         Rectangle region = super.sesConfig.getRectangleProperty("signOnRegion");

         int fromRow = region.x;
         int fromCol = region.y;
         int toRow = region.width;
         int toCol = region.height;

         // make sure we are within range.
         if (fromRow == 0)
            fromRow = 1;
         if (fromCol == 0)
            fromCol = 1;
         if (toRow == 0)
            toRow = 24;
         if (toCol == 0)
            toCol = 80;

         int pos = 0;

         for (int r = fromRow; r <= toRow; r++)
            for (int c =fromCol;c <= toCol; c++) {
               pos = screen.getPos(r - 1, c - 1);
//               System.out.println(signonSave[pos]);
               if (signonSave[pos] != so[pos])
                  return false;
            }
      }

      return true;
   }

   public String getSessionName() {
      return sessionName;
   }

   public String getAllocDeviceName() {
      if (vt != null)
         return vt.getAllocatedDeviceName();
      else
         return null;
   }

   public int getSessionType() {

      return sessionType;

   }

   public String getHostName() {
      return vt.getHostName();
   }

   public Screen5250 getScreen() {

      return screen;

   }

//      public SessionManager getSessionManager() {
//
//   //      return this.me.manager;
//
//      }

   public void connect() {

      String proxyPort = "1080"; // default socks proxy port
      boolean enhanced = false;
      boolean support132 = false;
      int port = 23; // default telnet port

      enhanced = sesProps.containsKey(SESSION_TN_ENHANCED);

      if (sesProps.containsKey(SESSION_SCREEN_SIZE))
         if (((String)sesProps.getProperty(SESSION_SCREEN_SIZE)).equals(SCREEN_SIZE_27X132_STR))
            support132 = true;

      final tnvt vt = new tnvt(screen,enhanced,support132);
      setVT(vt);

      vt.setController(this);

      if (sesProps.containsKey(SESSION_PROXY_PORT))
         proxyPort = (String)sesProps.getProperty(SESSION_PROXY_PORT);

      if (sesProps.containsKey(SESSION_PROXY_HOST))
         vt.setProxy((String)sesProps.getProperty(SESSION_PROXY_HOST),
                     proxyPort);

      if (sesProps.containsKey(org.tn5250j.transport.SSLConstants.SSL_TYPE)) {
         sslType = (String)sesProps.getProperty(org.tn5250j.transport.SSLConstants.SSL_TYPE);
      }
      else {
         // set default to none
         sslType = org.tn5250j.transport.SSLConstants.SSL_TYPE_NONE;
      }

      vt.setSSLType(sslType);

      if (sesProps.containsKey(SESSION_CODE_PAGE))
         vt.setCodePage((String)sesProps.getProperty(SESSION_CODE_PAGE));

      if (sesProps.containsKey(SESSION_DEVICE_NAME))
         vt.setDeviceName((String)sesProps.getProperty(SESSION_DEVICE_NAME));

      if (sesProps.containsKey(SESSION_HOST_PORT)) {
         port = Integer.parseInt((String)sesProps.getProperty(SESSION_HOST_PORT));
      }
      else {
         // set to default 23 of telnet
         port = 23;
      }

      final String ses = (String)sesProps.getProperty(SESSION_HOST);
      final int portp = port;

      // lets set this puppy up to connect within its own thread
      Runnable connectIt = new Runnable() {
            public void run() {
               vt.connect(ses,portp);
            }

        };

      // now lets set it to connect within its own daemon thread
      //    this seems to work better and is more responsive than using
      //    swingutilities's invokelater
      Thread ct = new Thread(connectIt);
      ct.setDaemon(true);
      ct.start();
      addSessionListener(this);

   }

   public void disconnect() {

      connected = false;
      vt.disconnect();

   }

   /**
    * Notify all registered listeners of the onSessionChanged event.
    *
    * @param state  The state change property object.
    */
   protected void fireSessionChanged(int state) {

      switch (state) {

         case TN5250jConstants.STATE_CONNECTED:
            if (!firstScreen) {
               firstScreen = true;
               signonSave = screen.getScreenAsChars();
               System.out.println("Signon saved");
            }
            break;
         default:
            firstScreen = false;
            signonSave = null;

      }

      if (listeners != null) {
         int size = listeners.size();
         for (int i = 0; i < size; i++) {
            SessionListener target =
                    (SessionListener)listeners.elementAt(i);
            sce.setState(state);
            target.onSessionChanged(sce);
         }
      }
   }

   /**
    * Add a SessionListener to the listener list.
    *
    * @param listener  The SessionListener to be added
    */
   public synchronized void addSessionListener(SessionListener listener) {

      if (listeners == null) {
          listeners = new java.util.Vector(3);
      }
      listeners.addElement(listener);

   }

   /**
    * Remove a SessionListener from the listener list.
    *
    * @param listener  The SessionListener to be removed
    */
   public synchronized void removeSessionListener(SessionListener listener) {
      if (listeners == null) {
          return;
      }
      listeners.removeElement(listener);

   }

}