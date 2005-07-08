/*
 * @(#)TN5250jTabbedPane.java Copyright: Copyright (c) 2001
 * @author Kenneth J. Pouncey
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this software; see the file COPYING. If not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 */package org.tn5250j.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

import org.tn5250j.event.TabClosedListener;

/**
 * This class provides an instance of the a JTabbedPane that provides the
 * following functionality:
 * 
 *    Paints a closable (X) on each tab when the cursor enters a tab.
 *    Ability to cycle through the tabs using the mouse wheel.
 *    Reordering of tabs via drag-n-drop with a valid drip marker drawn.
 *    
 */
public class TN5250jTabbedPane extends JTabbedPane implements MouseListener,
      MouseWheelListener, MouseMotionListener {

   // the currectly drawn tab that an X is drawn on
   int tabNumber;
   // the region that the X is drawn in the tab
   Rectangle closeRect;
   // closable tab listener.
   private Vector closeListeners;

   // variable to identify the drop position to reorder the tabs
   private int dropIndex = -1;
   
   // variable to identify if we are in dragging mode or not
   private boolean dragging = false;

   public TN5250jTabbedPane() {
      super();
      // this.setUI(new MyBasicTabbedPaneUI());
      addMouseListener(this);
      addMouseMotionListener(this);
      addMouseWheelListener(this);
   }

   public void addTab(String title, Component component, Icon extraIcon) {
      super.addTab(title, new CloseTabIcon(extraIcon), component);
   }

   public void setIconAt(Icon extraIcon, int index) {
      super.setIconAt(index, new CloseTabIcon(extraIcon));
   }

   public void mouseClicked(MouseEvent e) {
      int tabIndex = getTabIndex(e.getX(), e.getY());

      if (tabIndex < 0)
         return;

      if (closeRect.contains(e.getX(), e.getY())) {
         // the tab is being closed
         fireTabClosed(tabNumber);

      }
   }

   public void mouseExited(MouseEvent e) {
      tabNumber = -1;
      repaint();

   }

   public void mousePressed(MouseEvent e) {

      if (!e.isPopupTrigger() && e.getButton() == MouseEvent.BUTTON1) {
         int tabIndex = getTabIndex(e.getX(), e.getY());
         if (tabIndex != -1) {
            dropIndex = tabIndex;
         }
      }
   }

   public void mouseReleased(MouseEvent e) {
      if (!e.isPopupTrigger() && e.getButton() == MouseEvent.BUTTON1) {
         if (dragging) {
            int tabIndex = getTabIndex(e.getX(), e.getY());
            if (tabIndex != -1 && tabIndex != dropIndex) {
               reorderTab(dropIndex, tabIndex);
               setSelectedIndex(tabIndex);
            }
            dropIndex = -1;
            dragging = false;
            repaint();
         }
      }
   }

   /**
    * Well just what it says.  As the mouse is dragged the reordering position
    * marker is drawn if a valid drop position.
    */
   public void mouseDragged(MouseEvent e) {
      
      if (dragging) {
         if (getUI().tabForCoordinate(this, e.getX(), e.getY()) != -1) {
            int stabNumber = getUI().tabForCoordinate(this, e.getX(), e.getY());

            if (stabNumber >= 0) {

               if (stabNumber != tabNumber) {

                  repaint();
               }

               tabNumber = stabNumber;

               Rectangle tabRect = getUI().getTabBounds(this, tabNumber);

               // System.out.println(tabNumber + " " + tabRect.x
               // + " " + tabRect.y
               // + " " + tabRect.width
               // + " " + tabRect.height);

               Graphics g = this.getGraphics();

               paintDropPosition(g, tabRect.x, tabRect.y, Color.red);
            }
         }
      }
      else {
         //  Well let's start dragging then
         dragging = true;
      }
   }

   public void mouseMoved(MouseEvent e) {

      int stabNumber = getUI().tabForCoordinate(this, e.getX(), e.getY());

      if (stabNumber >= 0) {

         if (stabNumber != tabNumber) {

            repaint();
         }

         tabNumber = stabNumber;

         //Rectangle tabRect = getUI().getTabBounds(this, tabNumber);

         // System.out.println(tabNumber + " " + tabRect.x
         // + " " + tabRect.y
         // + " " + tabRect.width
         // + " " + tabRect.height);

         Graphics g = this.getGraphics();

         closeRect = ((CloseTabIcon) getIconAt(tabNumber)).getBounds();

         if (closeRect.contains(e.getX(), e.getY()))
            paintClosableX(g, closeRect.x, closeRect.y, Color.red);
         else
            paintClosableX(g, closeRect.x, closeRect.y, Color.black);

      }

      else {
         repaint();
      }
   }

   /**
    * Here we will cycle through the tabs with the mouse
    */
   public void mouseWheelMoved(MouseWheelEvent e) {

      int notches = e.getWheelRotation();
      if (notches < 0) {

         // This is UP
         // We will just cycle from the last tab to the first tab
         if (this.getSelectedIndex() == this.getTabCount() - 1) {
            this.setSelectedIndex(0);

         }
         else {
            this.setSelectedIndex(this.getSelectedIndex() + 1);
         }

      }
      else {

         // This is DOWN
         // We will just cycle from the first tab to the last tab
         if (this.getSelectedIndex() == 0) {
            this.setSelectedIndex(this.getTabCount() - 1);

         }
         else {
            this.setSelectedIndex(this.getSelectedIndex() - 1);
         }
      }
   }

   /**
    * This method paints the closable X on the tab in the correct position
    * 
    * @param g
    * @param x
    * @param y
    * @param x_color
    */
   private void paintClosableX(Graphics g, int x, int y, Color x_color) {

      Color col = g.getColor();

      g.setColor(x_color);

      int y_p = y + 2;
      g.drawLine(x + 1, y_p, x + 12, y_p);
      g.drawLine(x + 1, y_p + 13, x + 12, y_p + 13);
      g.drawLine(x, y_p + 1, x, y_p + 12);
      g.drawLine(x + 13, y_p + 1, x + 13, y_p + 12);
      g.drawLine(x + 3, y_p + 3, x + 10, y_p + 10);
      g.drawLine(x + 3, y_p + 4, x + 9, y_p + 10);
      g.drawLine(x + 4, y_p + 3, x + 10, y_p + 9);
      g.drawLine(x + 10, y_p + 3, x + 3, y_p + 10);
      g.drawLine(x + 10, y_p + 4, x + 4, y_p + 10);
      g.drawLine(x + 9, y_p + 3, x + 3, y_p + 9);
      g.setColor(col);

   }

   /**
    * This method paints the drop position on the tab bar to mark the position
    * for the tab reordering.
    * 
    * @param g
    * @param x
    * @param y
    * @param x_color
    */
   private void paintDropPosition(Graphics g, int x, int y, Color x_color) {

      Color col = g.getColor();

      g.setColor(x_color);

      int w = 10;
      int h = 10;
      int m = w / 2;
      x -= w / 2;
      y -= h / 2;
      Polygon poly = new Polygon();
      poly.addPoint(x, y);
      poly.addPoint(x + w, y);
      poly.addPoint(x + m, y + h);

      g.fillPolygon(poly);

      g.setColor(col);
   }

   /**
    * Method to save off the current tab information remove it and then insert
    * into the desired position
    * 
    * @param newIndex
    * @param currentTabIndex
    */
   private void reorderTab(int newIndex, int currentTabIndex) {
      String title = getTitleAt(newIndex);
      Icon icon = getIconAt(newIndex);
      Component component = getComponentAt(newIndex);
      String toolTipText = getToolTipTextAt(newIndex);

      Color background = getBackgroundAt(newIndex);
      Color foreground = getForegroundAt(newIndex);
      Icon disabledIcon = getDisabledIconAt(newIndex);
      int mnemonic = getMnemonicAt(newIndex);
      int displayedMnemonicIndex = getDisplayedMnemonicIndexAt(newIndex);
      boolean enabled = isEnabledAt(newIndex);

      remove(newIndex);
      insertTab(title, icon, component, toolTipText, currentTabIndex);

      setBackgroundAt(currentTabIndex, background);
      setForegroundAt(currentTabIndex, foreground);
      setDisabledIconAt(currentTabIndex, disabledIcon);
      setMnemonicAt(currentTabIndex, mnemonic);
      setDisplayedMnemonicIndexAt(currentTabIndex, displayedMnemonicIndex);
      setEnabledAt(currentTabIndex, enabled);
   }

   /**
    * Convenience method to obtain the tab index from the coordinates.
    * 
    * @param x
    * @param y
    * @return
    */
   private int getTabIndex(int x, int y) {
      return getUI().tabForCoordinate(this, x, y);
   }

   public void mouseEntered(MouseEvent e) {

   }

   /**
    * Add a TabClosedListener to the listener list.
    * 
    * @param listener The TabClosedListener to be added
    */
   public synchronized void addtabCloseListener(TabClosedListener listener) {

      if (closeListeners == null) {
         closeListeners = new java.util.Vector(3);
      }
      closeListeners.addElement(listener);

   }

   /**
    * Remove a TabClosedListener from the listener list.
    * 
    * @param listener The TabClosedListener to be removed
    */
   public synchronized void removeTabCloseListener(TabClosedListener listener) {
      if (closeListeners == null) {
         return;
      }
      closeListeners.removeElement(listener);

   }

   /**
    * Notify all the tab listeners that this specific tab was selected to close.
    * 
    * @param tabToClose
    */
   public void fireTabClosed(int tabToClose) {
      if (closeListeners != null) {
         int size = closeListeners.size();
         for (int i = 0; i < size; i++) {
            TabClosedListener target = (TabClosedListener) closeListeners
                  .elementAt(i);
            target.tabClosed(tabToClose);
         }
      }

   }

   /**
    * The class which generates the 'X' icon for the tabs. The constructor
    * accepts an icon which is extra to the 'X' icon, so you can have tabs like
    * in JBuilder. This value is null if no extra icon is required.
    */
   class CloseTabIcon implements Icon {
      private int x_pos;
      private int y_pos;
      private int width;
      private int height;
      private Icon fileIcon;

      public CloseTabIcon(Icon fileIcon) {
         this.fileIcon = fileIcon;
         width = 16;
         height = 16;
      }

      public void paintIcon(Component c, Graphics g, int x, int y) {
         this.x_pos = x;
         this.y_pos = y;
         Color col = g.getColor();
         g.setColor(Color.black);
         int y_p = y + 2;
         g.drawLine(x + 1, y_p, x + 12, y_p);
         g.drawLine(x + 1, y_p + 13, x + 12, y_p + 13);
         g.drawLine(x, y_p + 1, x, y_p + 12);
         g.drawLine(x + 13, y_p + 1, x + 13, y_p + 12);
         g.drawLine(x + 3, y_p + 3, x + 10, y_p + 10);
         g.drawLine(x + 3, y_p + 4, x + 9, y_p + 10);
         g.drawLine(x + 4, y_p + 3, x + 10, y_p + 9);
         g.drawLine(x + 10, y_p + 3, x + 3, y_p + 10);
         g.drawLine(x + 10, y_p + 4, x + 4, y_p + 10);
         g.drawLine(x + 9, y_p + 3, x + 3, y_p + 9);
         g.setColor(col);
         if (fileIcon != null) {
            fileIcon.paintIcon(c, g, x + width, y_p);
         }
      }

      public int getIconWidth() {
         return width + (fileIcon != null ? fileIcon.getIconWidth() : 0);
      }

      public int getIconHeight() {
         return height;
      }

      public Rectangle getBounds() {
         return new Rectangle(x_pos, y_pos, width, height);
      }
   }

}