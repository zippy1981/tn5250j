package org.tn5250jlpr;

/*
 * @(#)TN5250jConstants.java
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



public interface TN5250jConstants {

   // Version information
   public static final String tn5250jRelease = "0";
   public static final String tn5250jVersion = ".0";
   public static final String tn5250jSubVer= ".1";

   // STATE
   static final int STATE_DISCONNECTED   =  0;
   static final int STATE_CONNECTED   =  1;
   static final int STATE_REMOVE   =  2;

   // OUTPUT
   static final String OUTPUT_PRINT   =  "print";
   static final String OUTPUT_TEXT   =  "text";
   static final String OUTPUT_PDF   =  "pdf";

   // SESSION Level key value pairs
   public static final String SESSION_HOST        = "OS_OHIO_SESSION_HOST";
   public static final String SESSION_HOST_PORT   =
                                             "SESSION_HOST_PORT";
   public static final String SESSION_NAME   =
                                             "SESSION_HOST_NAME";
   public static final String SESSION_CONFIG_RESOURCE =
                                              "SESSION_CONFIG_RESOURCE";
   public static final String SESSION_TYPE     = "SESSION_HOST_TYPE";
   public static final String SESSION_TN_ENHANCED = "SESSION_TN_ENHANCED";
   public static final String SESSION_SCREEN_SIZE = "SESSION_SCREEN_SIZE";
   public static final String SESSION_CODE_PAGE = "SESSION_CODE_PAGE";
   public static final String SESSION_PROXY_HOST = "SESSION_PROXY_HOST";
   public static final String SESSION_PROXY_PORT = "SESSION_PROXY_PORT";
   public static final String SESSION_OUTPUT = "SESSION_OUTPUT";
   public static final String SESSION_DEVICE_NAME = "SESSION_DEVICE_NAME";

//   // OS_OHIO_SESSION_TYPE type of sessions
//   public static final String OS_OHIO_SESSION_TYPE_5250_STR   = "2";

   public static final int NUM_PARMS = 15;

   // negative response categories
   public static final int NR_REQUEST_REJECT = 0x08;
   public static final int NR_REQUEST_ERROR = 0x10;
   public static final int NR_STATE_ERROR = 0x20;
   public static final int NR_USAGE_ERROR = 0x40;
   public static final int NR_PATH_ERROR = 0x80;

   // commands
   public static final byte CMD_WRITE_TO_DISPLAY = 0x11; // 17
   public static final byte CMD_CLEAR_UNIT = 0x40; // 64
   public static final byte CMD_CLEAR_UNIT_ALTERNATE = 0x20; // 32
   public static final byte CMD_CLEAR_FORMAT_TABLE = 0x50; // 80
   public static final byte CMD_READ_INPUT_FIELDS = 0x42; // 66
   public static final byte CMD_READ_MDT_FIELDS = 0x52; // 82
   public static final byte CMD_READ_MDT_IMMEDIATE_ALT = (byte)0x83; // 131
//   public static final byte CMD_READ_MDT_FIELDS_ALT = (byte)0x82; // 130
//   public static final byte CMD_READ_IMMEDIATE = 0x72; // 114
   public static final byte CMD_READ_SCREEN_IMMEDIATE = 0x62; // 98
   public static final byte CMD_WRITE_STRUCTURED_FIELD = (byte)243;  // (byte)0xF3 -13
   public static final byte CMD_SAVE_SCREEN = 0x02; // 02
   public static final byte CMD_RESTORE_SCREEN = 0x12; // 18
   public static final byte CMD_WRITE_ERROR_CODE = 0x21; // 33
   public static final byte CMD_WRITE_ERROR_CODE_TO_WINDOW = 0x22; // 34
   public static final byte CMD_ROLL = 0x23; // 35
   public static final byte CMD_READ_SCREEN_TO_PRINT = (byte)0x66; // 102

}