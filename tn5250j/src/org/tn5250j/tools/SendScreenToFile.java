package org.tn5250j.tools;

/**
 * Title: tn5250J
 * Copyright:   Copyright (c) 2001
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

import java.awt.*;
import java.io.*;
import javax.swing.*;
import org.tn5250j.Screen5250;

public class SendScreenToFile {

   Screen5250 screen;

   public SendScreenToFile(Screen5250 scrn) {

      screen = scrn;
      try {
         jbInit();
      }
      catch(Exception ex) {
         ex.printStackTrace();
      }
   }

   void jbInit() throws Exception {
      getPCFile();

   }

   /**
    * Get the local file from a file chooser
    */
   private void getPCFile() {

      String workingDir = System.getProperty("user.dir");
      MyFileChooser pcFileChooser = new MyFileChooser(workingDir);

      int ret = pcFileChooser.showSaveDialog(new JFrame());

      // check to see if something was actually chosen
      if (ret == JFileChooser.APPROVE_OPTION) {
         File file = pcFileChooser.getSelectedFile();

         StringBuffer sb = new StringBuffer();
         char[] s = screen.getScreenAsChars();
         int c = screen.getCols();
         int l = screen.getRows() * c;
         int col = 0;
         for (int x = 0; x < l; x++,col++) {
            sb.append(s[x]);
            if (col == c) {
               sb.append('\n');
               col = 0;
            }
         }

         writeToFile(sb.toString(),file);

      }

   }


   private void writeToFile(String sc,File file) {

      FileOutputStream out = null;
      try {
         out = new FileOutputStream(file);
         out.write(sc.getBytes());
         out.flush();
         out.close();

      }
      catch (FileNotFoundException fnfe) {System.out.println("fnfe: " + fnfe.getMessage());}
      catch (IOException ioe) {System.out.println("ioe: " + ioe.getMessage());}
      finally {
         if (out != null)
            try {
               out.close();
            }
            catch (IOException exc) {}

      }


   }

    /**
     * This is to fix
     * Bug Id - 4416982
     * Synopsis JFileChooser does not use its resources to size itself initially
     */
   class MyFileChooser extends JFileChooser {
      MyFileChooser(String dir) {
         super(dir);
      }

      public Dimension getPreferredSize() {
         return getLayout().preferredLayoutSize(this);
      }
   }

}
