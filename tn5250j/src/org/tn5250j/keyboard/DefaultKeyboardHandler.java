/**
 * Title: DefaultKeyboardHandler
 * Copyright:   Copyright (c) 2001, 2002
 * Company:
 * @author  Kenneth J. Pouncey
 * @version 0.5
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

package org.tn5250j.keyboard;

import javax.swing.KeyStroke;
import java.awt.event.*;
import java.awt.Toolkit;
import java.util.Hashtable;
import java.util.StringTokenizer;
import javax.swing.Action;
import javax.swing.AbstractAction;

import org.tn5250j.Session;
import org.tn5250j.keyboard.actions.*;

/**
 * The default keyboard input handler.
 */
public class DefaultKeyboardHandler extends KeyboardHandler {

   /**
    * Creates a new keyboard handler
    * @param Session The session to which the keys should be sent
    */
   public DefaultKeyboardHandler(Session session) {
      super(session);
   }

   public boolean isKeyStrokeDefined(String accelKey) {

      return keyMap.isKeyStrokeDefined(accelKey);
   }

   public KeyStroke getKeyStroke(String accelKey) {
      return keyMap.getKeyStroke(accelKey);
   }

    /*
     * We have to jump through some hoops to avoid
     * trying to print non-printing characters
     * such as Shift.  (Not only do they not print,
     * but if you put them in a String, the characters
     * afterward won't show up in the text area.)
     */
    protected void displayInfo(KeyEvent e, String s){
        String charString, keyCodeString, modString, tmpString,isString;

        char c = e.getKeyChar();
        int keyCode = e.getKeyCode();
        int modifiers = e.getModifiers();

        if (Character.isISOControl(c)) {
            charString = "key character = "
                       + "(an unprintable control character)";
        } else {
            charString = "key character = '"
                       + c + "'";
        }

        keyCodeString = "key code = " + keyCode
                        + " ("
                        + KeyEvent.getKeyText(keyCode)
                        + ")";
         if(keyCode == KeyEvent.VK_PREVIOUS_CANDIDATE) {

            keyCodeString += " previous candidate ";

         }

         if(keyCode == KeyEvent.VK_DEAD_ABOVEDOT ||
               keyCode == KeyEvent.VK_DEAD_ABOVERING ||
               keyCode == KeyEvent.VK_DEAD_ACUTE ||
               keyCode == KeyEvent.VK_DEAD_BREVE ||
               keyCode == KeyEvent.VK_DEAD_CIRCUMFLEX

            ) {

            keyCodeString += " dead key ";

         }

        modString = "modifiers = " + modifiers;
        tmpString = KeyEvent.getKeyModifiersText(modifiers);
        if (tmpString.length() > 0) {
            modString += " (" + tmpString + ")";
        } else {
            modString += " (no modifiers)";
        }

        isString = "isKeys = isActionKey (" + e.isActionKey() + ")" +
                         " isAltDown (" + e.isAltDown() + ")" +
                         " isAltGraphDown (" + e.isAltGraphDown() + ")" +
                         " isAltGraphDownLinux (" + isAltGr + ")" +
                         " isControlDown (" + e.isControlDown() + ")" +
                         " isMetaDown (" + e.isMetaDown() + ")" +
                         " isShiftDown (" + e.isShiftDown() + ")";


         String newline = "\n";
        System.out.println(s + newline
                           + "    " + charString + newline
                           + "    " + keyCodeString + newline
                           + "    " + modString + newline
                           + "    " + isString + newline);

    }

   /**
    * This is here for keybindings using the swing input map - the preferred
    *    way to use the keyboard.
    *
    */
   void initKeyBindings() {

      KeyStroke ks;

      new NewSessionAction(session,keyMap);
      new ToggleConnectionAction(session,keyMap);
      new JumpNextAction(session,keyMap);
      new JumpPrevAction(session,keyMap);
      new HotspotsAction(session,keyMap);
      new GuiAction(session,keyMap);
      new DispMsgsAction(session,keyMap);
      new AttributesAction(session,keyMap);
      new PrintAction(session,keyMap);
      new RulerAction(session,keyMap);
      new CloseAction(session,keyMap);
      new TransferAction(session,keyMap);
      new EmailAction(session,keyMap);
      new RunScriptAction(session,keyMap);
      new DebugAction(session,keyMap);

   }

   /**
    * Forwards key events directly to the input handler.
    * This is slightly faster than using a KeyListener
    * because some Swing overhead is avoided.
    */
   public void processKeyEvent(KeyEvent evt) {

      if(evt.isConsumed())
         return;

      switch(evt.getID()) {
      case KeyEvent.KEY_TYPED:
         processVTKeyTyped(evt);
         break;
      case KeyEvent.KEY_PRESSED:
         processVTKeyPressed(evt);
         break;
      case KeyEvent.KEY_RELEASED:
         processVTKeyReleased(evt);
         break;
      }

   }

   private void processVTKeyPressed(KeyEvent e){


      keyProcessed = true;
//      displayInfo(e,"Pressed " + keyProcessed);
      int keyCode = e.getKeyCode();

      if (isLinux && keyCode == e.VK_ALT_GRAPH) {

         isAltGr = true;
      }

//      if (linux)
//      if (keyCode == e.VK_UNDEFINED ||
//      if (keyCode == e.VK_ALT) {
//         System.out.println(" cursor active " + screen.cursorActive);
//         e.consume();
//         return;
//      }

      if (      keyCode == e.VK_CAPS_LOCK ||
            keyCode == e.VK_SHIFT ||
            keyCode == e.VK_ALT ||
            keyCode == e.VK_ALT_GRAPH
         ) {
//         displayInfo(e,"Pressed ");

         return;
      }

//      displayInfo(e,"Pressed " + keyProcessed);

      KeyStroke ks = KeyStroke.getKeyStroke(e.getKeyCode(),e.getModifiers(),false);

      if (emulatorAction(ks,e)) {

         return;
      }

      if (isLinux)
         lastKeyStroke = keyMap.getKeyStrokeText(e,isAltGr);
      else
         lastKeyStroke = keyMap.getKeyStrokeText(e);

      //System.out.println("lastKeyStroke " + lastKeyStroke);

      if (lastKeyStroke != null && !lastKeyStroke.equals("null")) {

         if (lastKeyStroke.startsWith("[") || lastKeyStroke.length() == 1) {

            screen.sendKeys(lastKeyStroke);
            if (recording)
               recordBuffer.append(lastKeyStroke);
         }
         else {
            session.executeMeMacro(lastKeyStroke);
         }
         if (lastKeyStroke.startsWith("[mark")) {
            if (lastKeyStroke.equals("[markleft]") ||
                  lastKeyStroke.equals("[markright]") ||
                  lastKeyStroke.equals("[markup]") ||
                  lastKeyStroke.equals("[markdown]")) {
               session.doKeyBoundArea(e,lastKeyStroke);
            }
         }
      }
      else
         keyProcessed = false;

      if (keyProcessed)
         e.consume();

   }

   private void processVTKeyTyped(KeyEvent e){

      char kc = e.getKeyChar();
//      displayInfo(e,"Typed processed " + keyProcessed);
      // Hack to make german umlauts work under Linux
      // The problem is that these umlauts don't generate a keyPressed event
      // and so keyProcessed is true (even if is hasn't been processed)
      // so we check if it's a letter (with or without shift) and skip return
      if (isLinux) {

         //if (!((Character.isLetter(kc) || kc == '�')  && (e.getModifiers() == 0
         if (!((Character.isLetter(kc) || kc == '\u20AC')  && (e.getModifiers() == 0
            || e.getModifiers() == KeyEvent.SHIFT_MASK))) {

            if (Character.isISOControl(kc) || keyProcessed) {
               return;
            }
         }
      }
      else {
         if (Character.isISOControl(kc) || keyProcessed) {
            return;
         }
      }
//      displayInfo(e,"Typed processed " + keyProcessed);
      String s = "";
//      if (isLinux) {
//         lastKeyStroke = keyMap.getKeyStrokeText(e,isAltGr);
//         System.out.println("last " + lastKeyStroke);
//         if (lastKeyStroke != null) {
//            s = lastKeyStroke;
//            System.out.println("last " + s);
//         }
//         else
//            s +=kc;
//      }
//      else
         s += kc;
      if (!session.getVT().isConnected()   )
         return;
      screen.sendKeys(s);
      if (recording)
         recordBuffer.append(s);
      keyProcessed = true;
      e.consume();
   }

   private void processVTKeyReleased(KeyEvent e){


      if (isLinux && e.getKeyCode() == e.VK_ALT_GRAPH) {

         isAltGr = false;
      }

      if (Character.isISOControl(e.getKeyChar()) || keyProcessed || e.isConsumed() )
         return;

//      displayInfo(e,"Released " + keyProcessed);

      String s = keyMap.getKeyStrokeText(e);

      if (s != null) {

         if (s.startsWith("[")) {
            screen.sendKeys(s);
            if (recording)
               recordBuffer.append(s);
         }
         else
            session.executeMeMacro(s);

      }
      else
         keyProcessed = false;

      if (keyProcessed)
         e.consume();
   }

}

