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

import java.util.*;
import java.text.*;
import java.net.Socket;
import java.net.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.lang.reflect.*;

public final class LPRStream implements Runnable {

   // IPDS and SCS Technical Reference manual
   // http://publib.boulder.ibm.com/pubs/pdfs/prsys/54453126.pdf

   Socket sock;
   BufferedInputStream bin;
   BufferedOutputStream bout;
   Vector bf;
   Stream bk;
   public int ascii[] = new int[256];
   private int ebcdic[] = new int[256];
   private boolean waitingForInput;
   boolean invited;
   private boolean dumpBytes = true;
   private boolean negotiated = false;
   private Thread me;
   private boolean cursorOn = false;
   private String session = "";
   private int port = 23;
   private boolean connected = false;
   ByteArrayOutputStream baosp = null;
   ByteArrayOutputStream baosrsp = null;
   ByteArrayOutputStream baosin = null;
   byte[] saveStream;
   private boolean proxySet = false;
   private String proxyHost = null;
   private String proxyPort = "1080";
   private int devSeq = -1;
   private String devName;
   private String devNameUsed;
   private FileOutputStream fw;
   private BufferedOutputStream dw;
   private boolean fileOpen;

   private LPRInterface scs2;

   LPRStream () {

      bf = new Vector();
      setCodePage("37");
      baosp = new ByteArrayOutputStream();
      baosrsp = new ByteArrayOutputStream();
      baosin = new ByteArrayOutputStream();

   }

   public String getHostName () {

      return session;
   }

   public void setDeviceName(String name) {

      devName = name;

   }

   public String getDeviceName() {
      return devName;
   }

   public String getAllocatedDeviceName() {
      return devNameUsed;
   }

   public boolean isConnected() {


      return connected;
   }

   public final boolean connect() {
      return connect(session,port);

   }

   public final void setProxy(String proxyHost, String proxyPort) {

      this.proxyHost=proxyHost;
      this.proxyPort = proxyPort;
      proxySet = true;

      Properties systemProperties = System.getProperties();
      systemProperties.put("socksProxySet","true");
      systemProperties.put("socksProxyHost",proxyHost);
      systemProperties.put("socksProxyPort",proxyPort);

      System.setProperties(systemProperties);

   }

   public final boolean connect(String s, int port) {

      try {
         session = s;
         this.port = port;


//         try {
//            screen52.setStatus(screen52.STATUS_SYSTEM,screen52.STATUS_VALUE_ON,"X - Connecting");
//         }
//         catch (Exception exc) {
//            System.out.println("setStatus(ON) " + exc.getMessage());
//
//         }

         sock = new Socket(s, port);

         if (sock == null)
            System.out.println("I did not get a socket");
         connected = true;
         // used for JDK1.3
//         sock.setKeepAlive(true);
         sock.setTcpNoDelay(true);
         sock.setSoLinger(false,0);
         InputStream in = sock.getInputStream();
         OutputStream out = sock.getOutputStream();

//         System.out.println("got input stream");

         bin = new BufferedInputStream(in, 8192);
         bout = new BufferedOutputStream(out);
//         System.out.println("got output stream");

         byte abyte0[];
         while(negotiate(abyte0 = readNegotiations())) ;
         negotiated = true;
//         controller.fireSessionChanged(TN5250jConstants.STATE_CONNECTED);
         try {
            loadStream(abyte0, 0);

            processStream();
         }
         catch (IOException ioe) {

         }
         catch (Exception ex1) {


         }

         System.out.println("starting up ");
         me = new Thread(this);
         me.start();


      }
      catch(Exception exception) {
         System.out.println("connect() " + exception.getMessage());
         if (sock == null)
            System.out.println("I did not get a socket");

         disconnect();
         return false;
      }
      return true;

   }

   public final boolean disconnect() {

      if (me != null && me.isAlive())
         me.interrupt();

      try {
         if (bin != null)
            bin.close();
         if (bout != null)
            bout.close();
         if (sock != null) {
            System.out.println("Closing socket");
            sock.close();
         }
         connected = false;
//         controller.fireSessionChanged(TN5250jConstants.STATE_DISCONNECTED);

      }
      catch(Exception exception) {
         System.out.println(exception.getMessage());
         connected = false;
         devSeq = -1;
         return false;

      }
      devSeq = -1;
      return true;
   }

   public static void main(String[] args) {
      
      final String argSession;
      final int argPort;
      final LPRStream lpr = new LPRStream();
      
      if (args.length > 0) {
	      argSession = args[0];
	      if (argSession.startsWith("-")) {
		      usage();
		      return;
	      }
	      if (isSpecified("-dn", args)) 
		      lpr.setDeviceName(getParm("-dn",args));
	      else {
		      usage();
		      return;
	      }
	      
	      if (isSpecified("-p", args)) 
		      argPort = Integer.parseInt(getParm("-p",args));
	      else
		      argPort = 23;
	      
	      if (isSpecified("-cp", args)) 
		      lpr.setCodePage(getParm("-cp",args));
	      else
		      lpr.setCodePage("37");
	      
      }
      else {
	   usage();
	   return;
      }

      // lets set this puppy up to connect within its own thread
      Runnable connectIt = new Runnable() {
            public void run() {
               lpr.connect(argSession,argPort);
//               lpr.connect("luxdev",23);
            }

        };

      // now lets set it to connect within its own daemon thread
      //    this seems to work better and is more responsive than using
      //    swingutilities's invokelater
      Thread ct = new Thread(connectIt);
      ct.setDaemon(true);
      ct.start();

      while (true) {



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

   static void usage()
	{
		System.out.println( "tn5250jlpr usage:" );
		System.out.println( "\tjava -jar tn5250jlpr host -dnDeviceName -pPort -cpCodePage" );
		System.out.println( "\thost and device name are MANDATORY" );
	}

   private final ByteArrayOutputStream appendByteStream(byte abyte0[]) {
      ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
      for(int i = 0; i < abyte0.length; i++)
      {
         bytearrayoutputstream.write(abyte0[i]);
         if(abyte0[i] == -1)
             bytearrayoutputstream.write(-1);
      }

      return bytearrayoutputstream;
   }

   public final void run() {

      boolean done = false;

      if(Thread.currentThread() == me) {

         while (!done) {
            try {

               waitingForInput = false;

               byte[] abyte0 = readIncoming();

               loadStream(abyte0, 0);

               if (!bf.isEmpty()) {
                  invited = false;
                  // lets play nicely with the others on the playground
                  me.yield();

                  processStream();
               }

            }
            catch (SocketException se) {
               System.out.println(se.getMessage());
               done = true;
               disconnect();
               System.exit(-1);
            }

            catch (IOException ioe) {

              System.out.println(ioe.getMessage());
              invited = true;
              if (me.isInterrupted())
                 done = true;

            }
            catch (Exception ex) {

              System.out.println(ex.getMessage());
              invited = true;
              if (me.isInterrupted())
                 done = true;

            }
         }
      }
    }

    private final byte[] readNegotiations()
        throws IOException {
        int i = bin.read();
        if(i < 0) {
            throw new IOException("Connection closed.");
        }
        else {
            int j = bin.available();
            byte abyte0[] = new byte[j + 1];
            abyte0[0] = (byte)i;
            bin.read(abyte0, 1, j);
            return abyte0;
        }
    }

   private final void loadStream(byte abyte0[], int i)
     throws IOException {

      int j = 0;
      int size = 0;
      if (saveStream == null) {
         j = (abyte0[i] & 0xff) << 8 | abyte0[i + 1] & 0xff;
         size = abyte0.length;
      }
      else {
         size = saveStream.length + abyte0.length;
         byte[] inter = new byte[size];
         System.arraycopy(saveStream, 0, inter, 0, saveStream.length);
         System.arraycopy(abyte0, 0, inter, saveStream.length, abyte0.length);
         abyte0 = new byte[size];
         System.arraycopy(inter, 0, abyte0, 0, size);
         saveStream = null;
         inter = null;
         j = (abyte0[i] & 0xff) << 8 | abyte0[i + 1] & 0xff;
//         System.out.println("partial stream found");
      }

      if (j > size) {
         saveStream = new byte[abyte0.length];
         System.arraycopy(abyte0, 0, saveStream, 0, abyte0.length);
//         System.out.println("partial stream saved");
      }
      else {
         byte abyte1[];
         try {
            abyte1 = new byte[j + 2];

            System.arraycopy(abyte0, i, abyte1, 0, j + 2);
            bf.addElement(new Stream(abyte1));
            if(abyte0.length > abyte1.length + i)
                loadStream(abyte0, i + j + 2);
         }
         catch (Exception ex) {

           System.out.println("load stream error " + ex.getMessage());
   //        ex.printStackTrace();
   //        dump(abyte0);

         }
      }
   }

    private final void writeByte(byte abyte0[])
        throws IOException {

        bout.write(abyte0);
        bout.flush();
    }

    private final void writeByte(byte byte0)
        throws IOException {

        bout.write(byte0);
        bout.flush();
    }

   // write gerneral data stream
   public final void writeGDS(int flags, int opcode, byte abyte0[])
      throws IOException {
      int length;
      if(abyte0 != null)
         length = abyte0.length + 7;
      else
         length = 7;

      // refer to rfc1205 - 5250 Telnet interface
      // Section 3.  Data Stream Format

      // Logical Record Length   -  16 bits
      baosrsp.write(length >> 8);       // Length LL
      baosrsp.write(length & 0xff);     //        LL

      // Record Type -  16 bits
      // It should always be set to '12A0'X to indicate the
      // General Data Stream (GDS) record type.
      baosrsp.write(18);          // 0x12
      baosrsp.write(160);         // 0xA0

//      // the next 16 bits are not used
//      baosrsp.write(0);           // 0x00
//      baosrsp.write(0);           // 0x00
//
//      //  The second part is meant to be variable in length
//      //  currently this portion is 4 octets long (1 byte or 8 bits for us ;-O)
//      baosrsp.write(4);           // 0x04
//
//      baosrsp.write(flags);       // flags
//                                                // bit 0 - ERR
//                                                // bit 1 - ATN Attention
//                                                // bits 2-4   - reserved
//                                                // bit 5 -  SRQ system request
//                                                // bit 6 - TRQ Test request key
//                                                // bit 7 - HLP

      baosrsp.write(0x01);
      baosrsp.write(0x02);

      baosrsp.write(abyte0.length+1);

      if(abyte0 != null)
         baosrsp.write(abyte0, 0, abyte0.length);
      baosrsp = appendByteStream(baosrsp.toByteArray());

      // make sure we indicate no more to be sent
      baosrsp.write(IAC);
      baosrsp.write(EOR);

      baosrsp.writeTo(bout);

//        byte[] b = new byte[baosrsp.size()];
//        b = baosrsp.toByteArray();
//      dump(b);
      bout.flush();
//      baos = null;
      baosrsp.reset();
   }

   public final int getDataFlowType() {

      return bk.getDataFlowType();
   }


   private final void sendNotify() throws IOException {

      writeGDS(0, 0, null);
   }

   private final void processStream()
         throws Exception {


      while (!bf.isEmpty()) {
         // lets play nicely with the others on the playground
         me.yield();

         cursorOn = false;

         bk = (Stream)bf.elementAt(0);
         bf.removeElementAt(0);
         System.out.println("data flow type: " + Integer.toBinaryString(getDataFlowType()) +
                              " op code: " + Integer.toHexString(bk.getOpCode()) +
                              " record flag: " + Integer.toBinaryString(bk.getFlag()));
            switch (bk.getOpCode()) {
               case 00:
//                  System.out.println("No operation");
                  while (bk.hasNext())
                     System.out.print(getASCIIChar(bk.getNextByte()));
                  System.out.println();
                  sendPrintCompleteRecord();

                  break;
               case 1:
                  if (scs2 == null)
                     getWriter();
                  if (!fileOpen) {
                     scs2.openOutputFile();
                     fileOpen = true;
                  }
                  if (bk.getStreamSize() == 17) {
                     fileOpen =false;
                     scs2.closeOutputFile();
                     scs2 = null;
                  }
                  else
                     scs2.process_print_record(bk);
                  sendPrintCompleteRecord();
                  break;
               case 2:
                  while (bk.hasNext())
                     System.out.print(getASCIIChar(bk.getNextByte()));
                     System.out.println();
                  break;
               default:
                  break;
            }

      }


   }

   private final void getWriter() {

//      scs2 = new SCS2ASCII();
      scs2 = new SCS2PDF();
   }

   private final void sendPrintCompleteRecord() throws IOException {
      byte[] byte0 = new byte[3];
      byte0[0] = 0x00;
      byte0[1] = 0x00;
      byte0[2] = 0x01;
      writeGDS(0, 0, byte0);
   }

   public final byte[] readIncoming()
        throws IOException {

      boolean done = false;
      boolean negotiate = false;

      baosin.reset();
      int j = -1;
      int i = 0;

      while(!done) {
         i = bin.read();

         // We use the values instead of the static values IAC and EOR
         //    because they are defined as bytes.
         //
         // The > if(i != 255 || j != 255)  < is a hack for the double FF FF's
         // that are being returned.  I do not know why this is like this and
         // can not find any documentation for it.  It is also being returned
         // on my Client Access tcp dump as well so they are handling it.
         //
         // my5250
         // 0000:  00 50 DA 44 C8 45 42 00 00 00 00 24 08 00 45 00 .P.D.EB....$..E.
         // 0010:  04 2A BC F9 00 00 40 06 D0 27 C1 A8 33 04 C1 A8 .*....@..'..3...
         // 0020:  33 58 00 17 04 18 6F A2 83 CB 00 1E D1 BA 50 18 3X....o.......P.
         // 0030:  20 00 8A 9A 00 00 03 FF FF 12 A0 00 00 04 00 00  ...............
         // --------------------------- || || -------------------------------------
         // 0040:  03 04 40 04 11 00 20 01 07 00 00 00 18 00 00 10 ..@... .........

         if(j == 255 && i == 255) {
            j = -1;
            continue;
         }
         else {
            baosin.write(i);
            // check for end of record EOR and IAC  - FFEF
            if(j == 255 && i == 239)
               done = true;

            // This is to check for the TELNET TIMING MARK OPTION
            // rfc860 explains this in more detail.  When we receive it
            // we will negotiate with the server by sending a WONT'T TIMING-MARK
            // This will let the server know that we processed the information
            // and are just waiting for the user to enter some data so keep the
            // socket alive.   This is more or less a AYT (ARE YOU THERE) or not.
            if(i == 253 && j == 255) {
               done = true;
               negotiate = true;
            }
            j = i;
         }
     }

     // after the initial negotiation we might get other options such as
     //    timing marks ??????????????  do we ???????????? look at telnet spec
     // yes we do. rfc860 explains about timing marks.
         if (negotiate) {
            // get the negotiation option
            baosin.write(bin.read());
            negotiate(baosin.toByteArray());
         }

         if (dumpBytes) {
            System.out.println("dumping" );
            dump(baosin.toByteArray());
         }

        return baosin.toByteArray();
    }

   private final boolean negotiate(byte abyte0[]) throws IOException {
      int i = 0;


      // from server negotiations
      if(abyte0[i] == IAC) { // -1

         while(i < abyte0.length && abyte0[i++] == -1)
            switch(abyte0[i++]) {

               // we will not worry about what it WONT do
               case WONT:            // -4
               default:
                 break;

               case DO: //-3

                  switch(abyte0[i]) {
                     case TERMINAL_TYPE: // 24
                        baosp.write(IAC);
                        baosp.write(WILL);
                        baosp.write(TERMINAL_TYPE);
                        writeByte(baosp.toByteArray());
                        baosp.reset();

                        break;

                    case OPT_END_OF_RECORD: // 25

                        baosp.write(IAC);
                        baosp.write(WILL);
                        baosp.write(OPT_END_OF_RECORD);
                        writeByte(baosp.toByteArray());
                        baosp.reset();
                        break;

                    case TRANSMIT_BINARY: // 0

                        baosp.write(IAC);
                        baosp.write(WILL);
                        baosp.write(TRANSMIT_BINARY);
                        writeByte(baosp.toByteArray());
                        baosp.reset();
                        break;

                    case TIMING_MARK: // 6   rfc860
//                        System.out.println("Timing Mark Received and notifying " +
//                        "the server that we will not do it");
                        baosp.write(IAC);
                        baosp.write(WONT);
                        baosp.write(TIMING_MARK);
                        writeByte(baosp.toByteArray());
                        baosp.reset();

                        break;

                    case NEW_ENVIRONMENT: // 39 rfc1572
                        if (devName == null) {
                           baosp.write(IAC);
                           baosp.write(WONT);
                           baosp.write(NEW_ENVIRONMENT);
                           writeByte(baosp.toByteArray());
                           baosp.reset();

                        }
                        else {
                           System.out.println(devName);
                           baosp.write(IAC);
                           baosp.write(WILL);
                           baosp.write(NEW_ENVIRONMENT);
                           writeByte(baosp.toByteArray());
                           baosp.reset();

                        }
                        break;

                    default:  // every thing else we will not do at this time
                        baosp.write(IAC);
                        baosp.write(WONT);
                        baosp.write(abyte0[i]); // either
                        writeByte(baosp.toByteArray());
                        baosp.reset();

                        break;
                 }

                 i++;
                 break;

               case WILL:

                 switch(abyte0[i]) {
                    case OPT_END_OF_RECORD: // 25
                        baosp.write(IAC);
                        baosp.write(DO);
                        baosp.write(OPT_END_OF_RECORD);
                        writeByte(baosp.toByteArray());
                        baosp.reset();

                        break;

                    case TRANSMIT_BINARY: // '\0'
                        baosp.write(IAC);
                        baosp.write(DO);
                        baosp.write(TRANSMIT_BINARY);
                        writeByte(baosp.toByteArray());
                        baosp.reset();

                        break;
                 }
                 i++;
                 break;

               case SB: // -6

                  if(abyte0[i] == NEW_ENVIRONMENT && abyte0[i + 1] == 1) {
                     byte[] seed = new byte[8];
                     int offset = i + 11; // skip over IBMRSEED
                     for (int x = 0; x < 8; x++) {
                        seed[x] = abyte0[offset + x];
                     }
                     negNewEnvironment(seed);

                     i++;
                  }

                  if(abyte0[i] == TERMINAL_TYPE && abyte0[i + 1] == 1) {
                     baosp.write(IAC);
                     baosp.write(SB);
                     baosp.write(TERMINAL_TYPE);
                     baosp.write(QUAL_IS);
                     baosp.write((new String("IBM-3812-1")).getBytes());
                     baosp.write(IAC);
                     baosp.write(SE);
                     writeByte(baosp.toByteArray());
                     baosp.reset();

                     i++;
                  }
                  i++;
                  break;
            }
            return true;
      }
      else {
         return false;
      }
   }

   private void negNewEnvironment(byte[] seed)  throws IOException {

//   0000:  00 04 AC 9E B9 35 00 50 DA 44 C8 45 08 00 45 00 .....5.P.D.E..E.
//   0010:  00 D8 ED 13 40 00 80 06 23 62 C1 A8 33 58 C1 A8 ....@...#b..3X..
//   0020:  33 01 05 78 00 17 03 DA 69 C5 00 01 F0 EF 50 18 3..x....i.....P.
//   0030:  21 E9 D4 29 00 00 FF FA 27 00 03 49 42 4D 52 53 !..)....'..IBMRS
//   0040:  45 45 44 00 AE 18 44 44 D2 7C 26 B5 03 44 45 56 EED...DD.|&..DEV
//   0050:  4E 41 4D 45 01 41 46 49 52 53 54 03 49 42 4D 4D NAME.AFIRST.IBMM
//   0060:  53 47 51 4E 41 4D 45 01 51 53 59 53 4F 50 52 03 SGQNAME.QSYSOPR.
//   0070:  49 42 4D 4D 53 47 51 4C 49 42 01 2A 4C 49 42 4C IBMMSGQLIB.*LIBL
//   0080:  03 49 42 4D 46 4F 4E 54 01 31 31 03 49 42 4D 54 .IBMFONT.11.IBMT
//   0090:  52 41 4E 53 46 4F 52 4D 01 31 03 49 42 4D 4D 46 RANSFORM.1.IBMMF
//   00A0:  52 54 59 50 4D 44 4C 01 2A 48 50 49 49 03 49 42 RTYPMDL.*HPII.IB
//   00B0:  4D 50 50 52 53 52 43 31 01 04 01 03 49 42 4D 50 MPPRSRC1....IBMP
//   00C0:  50 52 53 52 43 32 01 04 03 49 42 4D 45 4E 56 45 PRSRC2...IBMENVE
//   00D0:  4C 4F 50 45 01 FF 03 49 42 4D 41 53 43 49 49 38 LOPE...IBMASCII8
//   00E0:  39 39 01 00 FF F0                               99....

//   0000:  00 04 AC 9E B9 35 00 50 DA 44 C8 45 08 00 45 00 .....5.P.D.E..E.
//   0010:  00 C0 23 14 40 00 80 06 ED 79 C1 A8 33 58 C1 A8 ..#.@....y..3X..
//   0020:  33 01 05 7C 00 17 03 E4 DF 5F 00 01 F0 FB 50 18 3..|....._....P.
//   0030:  21 E3 10 05 00 00 FF FA 27 00 03 49 42 4D 52 53 !.......'..IBMRS
//   0040:  45 45 44 2A CB 40 0E C2 CC 51 77 00 03 44 45 56 EED*.@...Qw..DEV
//   0050:  4E 41 4D 45 01 50 52 54 4A 49 4D 4D 59 03 49 42 NAME.PRTJIMMY.IB
//   0060:  4D 4D 53 47 51 4E 41 4D 45 01 51 53 59 53 4F 50 MMSGQNAME.QSYSOP
//   0070:  52 03 49 42 4D 4D 53 47 51 4C 49 42 01 2A 4C 49 R.IBMMSGQLIB.*LI
//   0080:  42 4C 03 49 42 4D 46 4F 4E 54 01 30 31 31 03 49 BL.IBMFONT.011.I
//   0090:  42 4D 46 4F 52 4D 46 45 45 44 01 03 49 42 4D 42 BMFORMFEED..IBMB
//   00A0:  55 46 46 45 52 53 49 5A 45 01 37 36 38 03 49 42 UFFERSIZE.768.IB
//   00B0:  4D 54 52 41 4E 53 46 4F 52 4D 01 30 FF F0 FF FA MTRANSFORM.0....
//   00C0:  18 00 49 42 4D 2D 33 38 31 32 2D 31 FF F0       ..IBM-3812-1..

      baosp.write(IAC);
      baosp.write(SB);
      baosp.write(NEW_ENVIRONMENT);
      baosp.write(IS);

      baosp.write(USERVAR);
      baosp.write((new String("IBMRSEED")).getBytes());
      baosp.write(seed);
      baosp.write(VAR);

      baosp.write(USERVAR);
      baosp.write((new String("DEVNAME")).getBytes());
      baosp.write(VALUE);
      baosp.write(negDeviceName().getBytes());

      baosp.write(USERVAR);
      baosp.write((new String("IBMMSGQNAME")).getBytes());
      baosp.write(VALUE);
      baosp.write((new String("QSYSOPR")).getBytes());

      baosp.write(USERVAR);
      baosp.write((new String("IBMMSGQLIB")).getBytes());
      baosp.write(VALUE);
      baosp.write((new String("*LIBL")).getBytes());

      baosp.write(USERVAR);
      baosp.write((new String("IBMFONT")).getBytes());
      baosp.write(VALUE);
      baosp.write((new String("10")).getBytes());

      baosp.write(USERVAR);
      baosp.write((new String("IBMTRANSFORM")).getBytes());
      baosp.write(VALUE);
      baosp.write((new String("0")).getBytes());

//      baosp.write(USERVAR);
//      baosp.write((new String("IBMMFRTYPMDL")).getBytes());
//      baosp.write(VALUE);
//      baosp.write((new String("*HPII")).getBytes());
//
//      baosp.write(USERVAR);
//      baosp.write((new String("IBMPPRSRC1")).getBytes());
//      baosp.write(VALUE);
//      baosp.write(ESC);
//      baosp.write(0x01);
//
//      baosp.write(USERVAR);
//      baosp.write((new String("IBMPPRSRC2")).getBytes());
//      baosp.write(VALUE);
//      baosp.write(0x04);
//
//      baosp.write(USERVAR);
//      baosp.write((new String("IBMENVELOPE")).getBytes());
//      baosp.write(VALUE);
//      baosp.write(IAC);
//
//      baosp.write(USERVAR);
//      baosp.write((new String("IBMASCII899")).getBytes());
//      baosp.write(VALUE);
//      baosp.write(0);

      baosp.write(IAC);
      baosp.write(SE);
      writeByte(baosp.toByteArray());
      baosp.reset();

   }

   /**
    * This will negotiate a device name with controller.
    *    if the sequence is less than zero then it will send the device name
    *    as specified.  On each unsuccessful attempt a sequential number is
    *    appended until we find one or the controller says no way.
    */
   private String negDeviceName() {

      if (devSeq++ == -1) {
         devNameUsed = devName;
         return devName;
      }
      else {
         StringBuffer sb = new StringBuffer(devName + devSeq);
         int ei = 1;
         while (sb.length() > 10) {

            sb.setLength(0);
            sb.append(devName.substring(0,devName.length() - ei++));
            sb.append(devSeq);

         }
         devNameUsed = sb.toString();
         return devNameUsed;
      }
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

   // negotiating commands
   private static final byte IAC = (byte)-1; // 255  FF
   private static final byte DONT = (byte)-2; //254  FE
   private static final byte DO = (byte)-3; //253    FD
   private static final byte WONT = (byte)-4; //252  FC
   private static final byte WILL = (byte)-5; //251  FB
   private static final byte SB = (byte)-6; //250 Sub Begin  FA
   private static final byte SE = (byte)-16; //240 Sub End   F0
   private static final byte EOR = (byte)-17; //239 End of Record  EF
   private static final byte TERMINAL_TYPE = (byte)24;     // 18
   private static final byte OPT_END_OF_RECORD = (byte)25;  // 19
   private static final byte TRANSMIT_BINARY = (byte)0;     // 0
   private static final byte QUAL_IS = (byte)0;             // 0
   private static final byte TIMING_MARK = (byte)6;         // 6
   private static final byte NEW_ENVIRONMENT = (byte)39;         // 27
   private static final byte IS = (byte)0;         // 0
   private static final byte SEND = (byte)1;         // 1
   private static final byte INFO = (byte)2;         // 2
   private static final byte VAR = (byte)0;         // 0
   private static final byte VALUE = (byte)1;         // 1
   private static final byte NEGOTIATE_ESC = (byte)2;         // 2
   private static final byte USERVAR = (byte)3;         // 3

   // miscellaneous
   private static final byte ESC = 0x04; // 04
   private static final char char0 = 0;


}