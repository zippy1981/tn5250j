package org.tn5250jlpr;
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

import java.io.*;
import com.lowagie.text.pdf.*;
import com.lowagie.text.*;

public final class SCS2PDF implements LPRInterface {


   public int ascii[] = new int[256];
   private int ebcdic[] = new int[256];
   private boolean dumpBytes = true;
   private FileOutputStream fw;
   private BufferedOutputStream dw;
   private Stream bk;

   private int page = 1;
   private int line = 1;
   private int column =1;
   private float pgWidth;
   private float pgHeight;
   private int lineSpacing = 2;

   // set the margins at a half inch - measurements of point is 72 points = 1 inch
   static final int point = 72;

   private float leftMargin = 0.5f;
   private float rightMargin = 0.5f;
   private float topMargin = 0.5f;
   private float bottomMargin = 0.5f;

   private int textOrientation;
   private Font font;

   private PdfWriter bos;
   private Document document;

   SCS2PDF () {

      setCodePage("280");

   }

   public void openOutputFile(String path) {
      try {
         System.out.println("Opening file");
         if (document == null) {
            document = new Document();
            bos = PdfWriter.getInstance(document,new FileOutputStream(path + ".pdf"));
//            document.setPageSize(new Rectangle(0.0f,
//                                                0.0f,
//                                                getPointFromInches(13),
//                                                getPointFromInches(11)));

            BaseFont bf = BaseFont.createFont("Courier", "Cp1252", false);
            font = new Font(bf, 11, Font.NORMAL);
         }
      }
      catch(IOException _ex) {
         System.out.println("Cannot open");

      }
      catch(Exception _ex) {
         System.out.println("Cannot open");
      }

   }

   public void closeOutputFile() {

         document.close();
         document = null;



   }

   public final void process_print_record(Stream s)
                                                            throws Exception {

      bk = s;
      while (bk.hasNext()) {
         byte nb = bk.getNextByte();
         switch (nb) {
            case 0x0:      // 0x00
               break;
            case SCS_PP:   // 0x34
               scs_pp();
               break;
            case SCS_2B:   // 0x2B
               scs_2B();
               break;
            case SCS_CR:   // 0x0D
               column = 1;
//               System.out.println("CR");
               break;
            case SCS_NL:   // 0x15
               scs_NL();
//               System.out.println();
               break;
            default:
               String st = "";
               st += getASCIIChar(nb);
               writeChar(st);
//               System.out.print(getASCIIChar(nb));
               break;
         }
      }

//      System.out.println();
   }

   private void scs_NL() {
      line += 1;
      column = 1;
      writeChar("\n");
//      bos.println();
   }

   private void scs_pp() throws Exception {

      int move = 0;
      switch (bk.getNextByte()) {
         case SCS_PP_ABS_HORZ_MOVE: // 0xC0
            move = bk.getNextByte();
            if (move != column) {
               if (move < column) {
                  writeChar("\n");
               }


               for (int x = 0; x < move; x++)
                  writeChar(" ");
               column = move;
            }
            System.out.println(" Presentation Position ABS_HORZ_MOVE " + column);
            break;
         case SCS_PP_ABS_VERT_MOVE: // 0xC4
            move = bk.getNextByte();
            if (move <= line) {
               document.newPage();
               System.out.println("new page at " + line);
               line = 1;
            }
            for (int x = 0; x < move; x++)
               writeChar("\n");
            line = move;
//
//            if (line > 66) {
//               document.newPage();
//               line = 1;
//               System.out.println("new page");
//            }
            System.out.println(" Presentation Position ABS_VERT_MOVE " + line);
            break;
         case SCS_PP_REL_MOVE_DOWN: //0x4C
            move = bk.getNextByte();
            if (move != line) {
//               if (move < line)
//                  writeChar("\n");
               for (int x = 0; x < move; x++)
                  writeChar("\n");
               line += move;
            }
            if (line > 66) {
               document.newPage();
               line = 1;
               System.out.println("new page");
            }
            System.out.println(" Presentation Position REL_MOVE_DOWN " + line);
            break;
         case SCS_PP_REL_MOVE_RIGHT:   // 0xC8
            move = bk.getNextByte();
            if (move != column) {
               if (move < column) {
                  writeChar("\n");
               }
               for (int x = 0; x < move; x++)
                  writeChar(" ");
               column += move;
            }
            System.out.println(" Presentation Position REL_MOVE_RIGHT " + column);
            break;

         default:
            System.out.println(" Invalid Presentation Position ");
            return;
      }
   }

   private void moveCursor(int move) {



   }

   private void writeChar(String s) {

      if (!document.isOpen())
         document.open();

      try {
         document.add(new Phrase(new Chunk(s,font)));
      }
      catch (com.lowagie.text.DocumentException de) {
         System.out.println(de);
      }
   }
   private void scs_2B() throws Exception {

      byte b2B = bk.getNextByte();
      switch (b2B) {
         case SCS_D1:   // 0xD1
            scs_D1();
            break;
         case SCS_D2:   // 0xD2
            scs_D2();
            break;
         case SCS_D3:   // 0xD3
            scs_D3();
            break;
         case SCS_C8: // 0xC8 Set graphical error action
            scs_C8();
            break;

         default:
            System.out.println(" Invalid 2B control " + b2B);
            return;
      }
//      line = bk.getNextByte();
//      System.out.println(line);
   }

   private void scs_D2() throws Exception {

      byte byte0 = bk.getNextByte();
      byte function = bk.getNextByte();

      switch (function) {
         case SCS_D2_SIC:
            int ic = bk.getNextByte();
            System.out.println(" Set initial conditions " + ic);
            break;
         case SCS_D2_SPSU:
            int pf = 0;
            byte0 -= 2;  // length include length + function + xxxxx so we offset
                        // length by 2 to skip over them
            for (int spsulen = 0; spsulen < byte0; spsulen++) {
               if (spsulen == 1)
                  pf = bk.getNextByte();
               else
                  bk.getNextByte();
            }
            System.out.println(" Set print setup " + pf);
            break;
         case SCS_D2_SJM:
            int st = 0;
            int pr = 0;

            st = bk.getNextByte(); // State
                                   //    0 - state off
                                   //    1 - Activate. begin justification

            if (byte0 == 4)
               pr = bk.getNextByte(); // Percent Rule
                                       //  0,50 or 100
            System.out.println(" Set justification mode state: " + st +
                                 " percent: " + pr);
            break;
         case SCS_D2_SEA:
            byte0 -= 2;  // length include length + function + xxxxx so we offset
                        // length by 2 to skip over them
            System.out.print(" Set exception actions - ignored for now - : ");
            for (int seaulen = 0; seaulen < byte0; seaulen++) {
                  System.out.print(bk.getNextByte() + " " );
            }
            System.out.println();
            break;

         case SCS_D2_SLS:
            lineSpacing = bk.getNextByte();
            System.out.println(" Set line spacing: " + lineSpacing);
            break;

         case SCS_D2_SHM:

            int lm = (bk.getNextByte() & 0xff) << 8 | bk.getNextByte() & 0xff;
            int rm = 0;
            if (byte0 == 06)
               rm =  (bk.getNextByte() & 0xff) << 8 | bk.getNextByte() & 0xff;

            leftMargin =  (float)lm / 1440;
            if (rm != 0)
               rightMargin =  (float)rm / 1440;

//            document.setMargins(getPointFromInches(leftMargin),
//                                 getPointFromInches(rightMargin),
//                                    document.topMargin(),
//                                    document.bottomMargin());

            System.out.println(" Set horizontal margins: left " + leftMargin + " " +
                                 getPointFromInches(leftMargin) +
                                 " right: " + rightMargin);
            break;
         case SCS_D2_SVM:
            int topM = (bk.getNextByte() & 0xff) << 8 | bk.getNextByte() & 0xff;
            int botM = 0;
            if (byte0 == 06)
               botM =  (bk.getNextByte() & 0xff) << 8 | bk.getNextByte() & 0xff;

            topMargin = (float)topM / 1440;
            if (botM != 0)
               bottomMargin = (float)botM / 1440;

            document.setMargins(document.leftMargin(),document.rightMargin(),
                                    getPointFromInches(topMargin),
                                    getPointFromInches(bottomMargin));
            System.out.println(" Set vertical margins: top " + topMargin + " " + topM +
                                 " bottom: " + bottomMargin + " " + botM);
            break;

         case SCS_D2_SCG:
            int gcgid =  (bk.getNextByte() & 0xff) << 8 | bk.getNextByte() & 0xff;
            int gcid =  (bk.getNextByte() & 0xff) << 8 | bk.getNextByte() & 0xff;

            // note this may be the font controls that set the code page to
            //    be used for translation of the text.  for now we will ignore it
            //    See SCGL - note for me....
            System.out.println(" Set GCGID through GCID: GCGID ignored " + gcgid +
                                 " gcid: " + gcid);
            break;

         case SCS_D2_PPM:
            byte0 -= 2;  // length include length + function + xxxxx so we offset
                        // length by 2 to skip over them
            System.out.print(" Page presentation media -ignored for now- : ");
            for (int pplen = 0; pplen < byte0; pplen++) {
                  System.out.print(bk.getNextByte() + " " );
            }
            System.out.println();
            break;
         case SCS_D2_SPPS:
            int width =  (bk.getNextByte() & 0xff) << 8 | bk.getNextByte() & 0xff;
            int height =  (bk.getNextByte() & 0xff) << 8 | bk.getNextByte() & 0xff;

            pgWidth = (float)width / 1440;
            pgHeight = (float)height / 1440;


//            bos.pause();
            document.setPageSize(new Rectangle(0.0f,
                                                0.0f,
                                                getPointFromInches(pgWidth),
                                                getPointFromInches(pgHeight)));
//            document.newPage();
//            document.setPageSize(new Rectangle(0.0f,
//                                                0.0f,
//                                                getPointFromInches(pgHeight),
//                                                getPointFromInches(pgWidth)));
//            bos.resume();
            System.out.println(" Set Presentation Page Size: Width " + pgWidth +
                                 " Height: " + pgHeight);

            break;
         case SCS_D2_SSLD:
            float distance =  (bk.getNextByte() & 0xff) << 8 | bk.getNextByte() & 0xff;

            // distance divided by 1440ths of an inch
            System.out.println(" Set Single Line Distance: " + distance +
                                    "/1440 = " + (distance / 1440)      );

            break;

         default:
            System.out.println(" Invalid D2 function " + function);
            byte0 -= 2;  // length include length + function + xxxxx so we offset
                        // length by 2 to skip over them
            for (int len = 0; len < byte0; len++) {
               bk.getNextByte();
            }
            break;
      }
   }

   private void scs_D3() throws Exception {

      byte byte0 = bk.getNextByte();
      byte function = bk.getNextByte();

      switch (function) {
         case SCS_D3_STO:
            int gcgid =  (bk.getNextByte() & 0xff) << 8 | bk.getNextByte() & 0xff;
            textOrientation =  (bk.getNextByte() & 0xff) << 8 | bk.getNextByte() & 0xff;
            System.out.println(" Set text orientation:" + getTextOrientationText());
            break;
         default:
            System.out.println(" Invalid D3 function " + function);
            byte0 -= 2;  // length include length + function + xxxxx so we offset
                        // length by 2 to skip over them
            for (int len = 0; len < byte0; len++) {
               bk.getNextByte();
            }
            break;

      }
   }

   private void scs_D1() throws Exception {

      byte byte0 = bk.getNextByte();
      byte function = bk.getNextByte();

      switch (function) {
         case SCS_D1_SFG: // 0x05
            int gfid =  (bk.getNextByte() & 0xff) << 8 | bk.getNextByte() & 0xff;
            int fwd =  (bk.getNextByte() & 0xff) << 8 | bk.getNextByte() & 0xff;
            int fa =  bk.getNextByte() & 0xff;
            System.out.println(" Set FID through GFID: Global font id " + gfid +
                                    " Font width " + fwd +
                                    " Font attribute " + fa);
            break;
         case SCS_D1_SCGL: // 0x81
            int lcid =  bk.getNextByte() & 0xff;
            System.out.println(" Set CGCS through local id: " + lcid);
            break;
         default:
            System.out.println(" Invalid D1 function " + function);
            byte0 -= 2;  // length include length + function + xxxxx so we offset
                        // length by 2 to skip over them
            for (int len = 0; len < byte0; len++) {
               bk.getNextByte();
            }
            break;

      }
   }

   // Set graphical Error Action
   private void scs_C8() throws Exception {

      byte byte0 = bk.getNextByte();
      int dg = bk.getNextByte(); // default graphic
      int uco = bk.getNextByte(); // unpritable character
      System.out.println(" Set graphical error action - ignored for now - : " +
                        " Default graphic character " + Integer.toHexString(dg) +
                        " Unprintable character " + Integer.toHexString(dg));
   }

   private String getTextOrientationText() {
      switch (textOrientation) {

         case 0x00:
            return " normal portrait (upright) orientation";
         case 0x2D00:
            return " Landscape left (270 degrees clockwise rotation of text)";
         case 0x5A00:
            return " Portrain upside down (180 degrees clockwise rotation of text)";
         case 0x8700:
            return " Landscape right (90 degrees clockwise rotation of text)";
         case 0xFFFE:
            return " Select CORR Mode";
         case 0xFFFF:
            return " Default: taken from SPPS option";
         default:
            return " invalid text orientation";

      }

   }
   private final float getPointFromInches(float inch) {

      return inch * point;
   }
   public final void setCodePage(String codePage) {
      int i = 0;

      int[] cp = CharMappings.getCodePage(codePage);
      do {

         ebcdic[i] = cp[i];
         ascii[cp[i]] = i;
      } while(++i < 256);

   }

//   public final Dimension getPreferredSize() {
//      return screen52.getPreferredSize();
//   }

   public byte getEBCDIC(int index) {
      return (byte)ascii[index & 0xff];

   }

   public char getEBCDICChar(int index) {
      return (char)ascii[index & 0xff];

   }

   public byte getASCII(int index) {

      return (byte)ascii[index];

   }

   public char getASCIIChar(int index) {
         return (char)ebcdic[index & 0xff];
   }

//   public void dumpScreen () {
//
//      for (int y = 0;y < screen52.getRows(); y++) {
//         System.out.print("row :" + (y + 1) + " ");
//
//         for (int x = 0; x < screen52.getCols(); x++) {
//            System.out.println("row " + (y + 1) + " col " + (x + 1) + " " + screen52.screen[y * x].toString());
//
//         }
//      }
//   }

   public void dump (byte[] abyte0) {
      try {
         if (fw == null) {
            fw = new FileOutputStream("log.txt");
            dw = new BufferedOutputStream(fw);
         }

         System.out.print("\n Buffer Dump of data from AS400: ");
         dw.write("\r\n Buffer Dump of data from AS400: ".getBytes());

         StringBuffer h = new StringBuffer();
         for (int x = 0; x < abyte0.length; x++) {
            if (x % 16 == 0) {
               System.out.println("  " + h.toString());
               dw.write(("  " + h.toString() + "\r\n").getBytes());

               h.setLength(0);
               h.append("+0000");
               h.setLength(5 - Integer.toHexString(x).length());
               h.append(Integer.toHexString(x).toUpperCase());

               System.out.print(h.toString());
               dw.write(h.toString().getBytes());

               h.setLength(0);
            }
            char ac = getASCIIChar(abyte0[x]);
            if (ac < ' ')
               h.append('.');
            else
               h.append(ac);
            if (x % 4 == 0) {
               System.out.print(" ");
               dw.write((" ").getBytes());

            }

            if (Integer.toHexString(abyte0[x] & 0xff).length() == 1){
               System.out.print("0" + Integer.toHexString(abyte0[x] & 0xff).toUpperCase());
               dw.write(("0" + Integer.toHexString(abyte0[x] & 0xff).toUpperCase()).getBytes());

            }
            else {
               System.out.print(Integer.toHexString(abyte0[x] & 0xff).toUpperCase());
               dw.write((Integer.toHexString(abyte0[x] & 0xff).toUpperCase()).getBytes());
            }

         }
         System.out.println();
         dw.write("\r\n".getBytes());

         dw.flush();
//         dw.close();
      }
      catch(EOFException _ex) { }
      catch(Exception _ex) {
         System.out.println("Cannot dump from host\n\r");
      }

   }

//   public void dumpBytes() {
//      byte shit[] = bk.buffer;
//      for (int i = 0;i < shit.length;i++)
//         System.out.println(i + ">" + shit[i] + "< - ascii - >" + getASCIIChar(shit[i]) + "<");
//   }

   public void dumpHexBytes(byte[] abyte) {
      byte shit[] = abyte;
      for (int i = 0;i < shit.length;i++)
         System.out.println(i + ">" + shit[i] + "< hex >" + Integer.toHexString((shit[i] & 0xff)));
   }


   private static final byte SCS_CR = 0x0D; // Carrage Return
   private static final byte SCS_LF = 0x25; // Line Feed
   private static final byte SCS_NL = 0x15; // New line



   // Cursor Controls
   private static final byte SCS_PP = 0x34; // presentation position
   private static final byte SCS_PP_REL_MOVE_DOWN = 0x4C;
   private static final byte SCS_PP_ABS_HORZ_MOVE = -64;  // 0xC0
   private static final byte SCS_PP_ABS_VERT_MOVE = -60;    // 0xC4
   private static final byte SCS_PP_REL_MOVE_RIGHT = -56;   // 0xC8

   // Page Controls
   private static final byte SCS_2B = 0x2B; // Set page stuff
   private static final byte SCS_D1 = -47;  // 0xD1
   private static final byte SCS_D2 = -46;  // 0xD2
   private static final byte SCS_D3 = -45;  // 0xD3
   private static final byte SCS_C8 = -56;  // 0xC8

   // D1 functions
   private static final byte SCS_D1_SFG = 0x05; // Set FID through GFID
   private static final byte SCS_D1_SCGL = -127; // 0x81 Set CGCS through Local Id

   // D2 functions
   private static final byte SCS_D2_SIC = 0x45; // Set initial conditions
   private static final byte SCS_D2_SPSU = 0x4C; // Set Print Setup
   private static final byte SCS_D2_SJM = 0x0D; // Set Justify Mode
   private static final byte SCS_D2_SEA = -123; // 0x85 Set Exception Action
   private static final byte SCS_D2_SLS = 0x09; // Set Line Spacing
   private static final byte SCS_D2_SHM = 0x11; // Set Horizontal Margin
   private static final byte SCS_D2_SCG = 0x01; // Set GCGID through GCID
   private static final byte SCS_D2_PPM = 0x48; // Page presentation media
   private static final byte SCS_D2_SVM = 0x49; // Set Vertical Margin
   private static final byte SCS_D2_SPPS = 0x40; // Set Presentation Page Size
   private static final byte SCS_D2_SSLD = 0x15; // Set Signle Line Distance

   // D3 functions
   private static final byte SCS_D3_STO = -10; // 0xF6 Set text orientation



}