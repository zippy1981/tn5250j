package org.tn5250j.tools.filters;

/*
 * @(#)DelimitedOutputFilter.java
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

import java.io.*;
import java.util.ArrayList;
import org.tn5250j.tools.*;
import javax.swing.*;

public class DelimitedOutputFilter implements OutputFilterInterface {

   PrintStream fout = null;
   String delimiter = ",";
   String stringQualifier = "\"";
   StringBuffer sb = new StringBuffer();

   // create instance of file for output
   public void createFileInstance(String fileName) throws
                              FileNotFoundException {
      fout = new PrintStream(new FileOutputStream(fileName));
   }

   /**
    * Write the html header of the output file
    */
   public void parseFields(byte[] cByte, ArrayList ffd, StringBuffer rb) {

      FileFieldDef f;

      // write out the html record information for each field that is selected

      for (int x = 0; x < ffd.size(); x++) {
         f = (FileFieldDef)ffd.get(x);
         if (f.isWriteField()) {


            switch (f.getFieldType()) {

               case 'P':
               case 'S':
                  rb.append(f.parseData(cByte).trim() + delimiter);
                  break;
               default:
                  rb.append(stringQualifier + f.parseData(cByte).trim() + stringQualifier + delimiter);
                  break;

            }
         }
      }

      rb.append ('\n');

      fout.println(rb);

   }

   /**
    * Write the html header of the output file
    */
   public void writeHeader(String fileName, String host,
                                 ArrayList ffd, char decChar) {

      try {

         FileFieldDef f;
         StringBuffer sb = new StringBuffer();
         //  loop through each of the fields and write out the field name for
         //    each selected field
         for (int x = 0; x < ffd.size(); x++) {
            f = (FileFieldDef)ffd.get(x);
            if (f.isWriteField()) {
               sb.append(f.getFieldName() + delimiter);
            }
         }

         fout.write (sb.toString().getBytes());
         fout.write ('\n');

      }
      catch (IOException ioe) {

//         printFTPInfo(" error writing header " + ioe.getMessage());
      }
   }


   /**
    * write the footer of the html output
    */
   public void writeFooter(ArrayList ffd) {

        fout.flush();
        fout.close();

   }

   public boolean isCustomizable() {
      return true;
   }

   public void setCustomProperties() {

      new DelimitedDialog(new JFrame());
   }

   class DelimitedDialog {

      public DelimitedDialog(JFrame parent) {

         JPanel opts = new JPanel();
         opts.setBorder(BorderFactory.createTitledBorder(
                                    LangTool.getString("delm.labelOptions")));

         opts.setLayout(new AlignLayout(2,5,5));
         JLabel fdl = new JLabel(LangTool.getString("delm.labelField"));
         JComboBox fd = new JComboBox();
         fd.addItem(",");
         fd.addItem(";");
         fd.addItem(":");
         fd.addItem("|");
         fd.addItem(LangTool.getString("delm.labelSpace"));
         fd.addItem(LangTool.getString("delm.labelTab"));

         fd.setEditable(true);
         fd.setSelectedIndex(0);

         JLabel tdl = new JLabel(LangTool.getString("delm.labelText"));
         JComboBox td = new JComboBox();
         td.addItem("\"");
         td.addItem("'");
         td.setEditable(true);
         td.setSelectedIndex(0);

         opts.add(fdl);
         opts.add(fd);
         opts.add(tdl);
         opts.add(td);

         Object[]      message = new Object[1];
         message[0] = opts;

         String[] options = {UIManager.getString("OptionPane.okButtonText"),
                              UIManager.getString("OptionPane.cancelButtonText")};

         int result = JOptionPane.showOptionDialog(
             parent,                            // the parent that the dialog blocks
             message,                           // the dialog message array
             LangTool.getString("delm.title"),    // the title of the dialog window
             JOptionPane.DEFAULT_OPTION,        // option type
             JOptionPane.PLAIN_MESSAGE,      // message type
             null,                              // optional icon, use null to use the default icon
             options,                           // options string array, will be made into buttons//
             options[0]                         // option that should be made into a default button
         );

         switch(result) {
            case 0: // change options
               delimiter = (String)fd.getSelectedItem();
               if (delimiter.equals(LangTool.getString("delm.labelSpace")))
                  delimiter = " ";
               if (delimiter.equals(LangTool.getString("delm.labelTab")))
                  delimiter = "\t";
               stringQualifier = (String)td.getSelectedItem();
               break;
            case 1: // Cancel
   //		      System.out.println("Cancel");
               break;
            default:
               break;
         }
      }
   }
}