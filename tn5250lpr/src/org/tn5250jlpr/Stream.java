package org.tn5250jlpr;
/**
 * Title: tn5250Jlpr
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

public class Stream {

    private int streamSize;
    private int opCode;
    private int dataFlowType;
    private int dataStart;
    private int pos;
    private int flag;
    private byte buffer[];

    public Stream(byte abyte0[]) {
//   0000:  00 50 DA 44 C8 45 00 04 AC 9E B9 35 08 00 45 00 .P.D.E.....5..E.
//   0010:  00 73 77 42 40 00 40 06 D9 98 C1 A8 33 01 C1 A8 .swB@.@.....3...
//   0020:  33 58 00 17 05 7D 00 01 F1 09 03 E8 35 8F 50 18 3X...}......5.P.
//   0030:  20 00 C7 46 00 00 00 49 12 A0 90 00 05 60 06 00  ..F...I.....`..
//   0040:  20 C0 00 3D 00 00 C9 F9 F0 F2 C2 C3 D7 D3 E4 C4  ..=............
//   0050:  C5 E5 C1 C6 C9 D9 E2 E3 40 40 40 40 00 00 00 00 ........@@@@....
//   0060:  00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 ................
//   0070:  00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 FF ................
//   0080:  EF                                              .

        buffer = abyte0;
        // size without end of record 0xFF 0xEF
        streamSize = (abyte0[0] & 0xff) << 8 | abyte0[1] & 0xff;
        opCode = abyte0[9];
        dataFlowType = (abyte0[4] & 0xff) << 8 | abyte0[5] & 0xff;
        dataStart = 6 + abyte0[6];
        flag = abyte0[7];
        pos = dataStart;


    }

    public final char[] getResponseCode() {
      char[] rc = new char[5];
      return rc;

    }
    public final int getOpCode() {
        return opCode;
    }

    public final int getDataFlowType() {
        return dataFlowType;
    }

    public final int getFlag() {

      return flag;
    }

    public final byte getNextByte()
        throws Exception  {
        if(pos > buffer.length)
            throw new Exception("Buffer length exceeded: " + pos);
        else
            return buffer[pos++];
    }

    public final void setPrevByte()
        throws Exception {
        if(pos == 0) {
            throw new Exception("Index equals zero.");
        }
        else {
            pos--;
            return;
      }
   }

   public final int getCurrentPos() {
      return pos;
   }

   public final byte getByteOffset(int off)
        throws Exception  {

        if((pos + off ) > buffer.length)
            throw new Exception("Buffer length exceeded: " + pos);
        else
            return buffer[pos + off];

   }

   public final int getStreamSize() {
      return streamSize;
   }

   public final boolean size() {
      return pos >= streamSize;
   }


   public final boolean hasNext() {

//      return pos >= buffer.length;
      return pos < streamSize;
   }
}
