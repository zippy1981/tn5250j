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


public class CharMappings {

// note to myself - execute the following on linux to obtain others
// EXAMPLE *** recode -v -h ebcdic-cp-es > ebcdic284.txt

   private static final String[] acp = {"37","37PT","273","280","284","285",
                                       "277-dk","277-no","297","424","500-ch",
                                       "870-pl","870-sk","1025-r"};

   /* Conversion table generated mechanically by Free `recode' 3.5
   for sequence IBM037..ISO-8859-1 (reversible).  */

   private static final int[] codePage37 = {
      0,   1,   2,   3, 156,   9, 134, 127,	/*   0 -   7  */
    151, 141, 142,  11,  12,  13,  14,  15,	/*   8 -  15  */
     16,  17,  18,  19, 157, 133,   8, 135,	/*  16 -  23  */
     24,  25, 146, 143,  28,  29,  30,  31,	/*  24 -  31  */
    128, 129, 130, 131, 132,  10,  23,  27,	/*  32 -  39  */
    136, 137, 138, 139, 140,   5,   6,   7,	/*  40 -  47  */
    144, 145,  22, 147, 148, 149, 150,   4,	/*  48 -  55  */
    152, 153, 154, 155,  20,  21, 158,  26,	/*  56 -  63  */
     32, 160, 226, 228, 224, 225, 227, 229,	/*  64 -  71  */
    231, 241, 162,  46,  60,  40,  43, 124,	/*  72 -  79  */
     38, 233, 234, 235, 232, 237, 238, 239,	/*  80 -  87  */
    236, 223,  33,  36,  42,  41,  59, 172,	/*  88 -  95  */
     45,  47, 194, 196, 192, 193, 195, 197,	/*  96 - 103  */
    199, 209, 166,  44,  37,  95,  62,  63,	/* 104 - 111  */
    248, 201, 202, 203, 200, 205, 206, 207,	/* 112 - 119  */
    204,  96,  58,  35,  64,  39,  61,  34,	/* 120 - 127  */
    216,  97,  98,  99, 100, 101, 102, 103,	/* 128 - 135  */
    104, 105, 171, 187, 240, 253, 254, 177,	/* 136 - 143  */
    176, 106, 107, 108, 109, 110, 111, 112,	/* 144 - 151  */
    113, 114, 170, 186, 230, 184, 198, 164,	/* 152 - 159  */
    181, 126, 115, 116, 117, 118, 119, 120,	/* 160 - 167  */
    121, 122, 161, 191, 208, 221, 222, 174,	/* 168 - 175  */
     94, 163, 165, 183, 169, 167, 182, 188,	/* 176 - 183  */
    189, 190,  91,  93, 175, 168, 180, 215,	/* 184 - 191  */
    123,  65,  66,  67,  68,  69,  70,  71,	/* 192 - 199  */
     72,  73, 173, 244, 246, 242, 243, 245,	/* 200 - 207  */
    125,  74,  75,  76,  77,  78,  79,  80,	/* 208 - 215  */
     81,  82, 185, 251, 252, 249, 250, 255,	/* 216 - 223  */
     92, 247,  83,  84,  85,  86,  87,  88,	/* 224 - 231  */
     89,  90, 178, 212, 214, 210, 211, 213,	/* 232 - 239  */
     48,  49,  50,  51,  52,  53,  54,  55,	/* 240 - 247  */
     56,  57, 179, 219, 220, 217, 218, 159,	/* 248 - 255  */
   };

   /* Conversion table generated mechanically by Free `recode' 3.5
   for sequence IBM273..ISO-8859-1 (reversible).  */

   private static final int[] codePage273 = {
      0,   1,   2,   3, 156,   9, 134, 127,	/*   0 -   7  */
    151, 141, 142,  11,  12,  13,  14,  15,	/*   8 -  15  */
     16,  17,  18,  19, 157, 133,   8, 135,	/*  16 -  23  */
     24,  25, 146, 143,  28,  29,  30,  31,	/*  24 -  31  */
    128, 129, 130, 131, 132,  10,  23,  27,	/*  32 -  39  */
    136, 137, 138, 139, 140,   5,   6,   7,	/*  40 -  47  */
    144, 145,  22, 147, 148, 149, 150,   4,	/*  48 -  55  */
    152, 153, 154, 155,  20,  21, 158,  26,	/*  56 -  63  */
     32, 160, 226, 123, 224, 225, 227, 229,	/*  64 -  71  */
    231, 241, 196,  46,  60,  40,  43,  33,	/*  72 -  79  */
     38, 233, 234, 235, 232, 237, 238, 239,	/*  80 -  87  */
    236, 126, 220,  36,  42,  41,  59,  94,	/*  88 -  95  */
     45,  47, 194,  91, 192, 193, 195, 197,	/*  96 - 103  */
    199, 209, 246,  44,  37,  95,  62,  63,	/* 104 - 111  */
    248, 201, 202, 203, 200, 205, 206, 207,	/* 112 - 119  */
    204,  96,  58,  35, 167,  39,  61,  34,	/* 120 - 127  */
    216,  97,  98,  99, 100, 101, 102, 103,	/* 128 - 135  */
    104, 105, 171, 187, 240, 253, 254, 177,	/* 136 - 143  */
    176, 106, 107, 108, 109, 110, 111, 112,	/* 144 - 151  */
    113, 114, 170, 186, 230, 184, 198, 164,	/* 152 - 159  */
    181, 223, 115, 116, 117, 118, 119, 120,	/* 160 - 167  */
    121, 122, 161, 191, 208, 221, 222, 174,	/* 168 - 175  */
    162, 163, 165, 183, 169,  64, 182, 188,	/* 176 - 183  */
    189, 190, 172, 124, 175, 168, 180, 215,	/* 184 - 191  */
    228,  65,  66,  67,  68,  69,  70,  71,	/* 192 - 199  */
     72,  73, 173, 244, 166, 242, 243, 245,	/* 200 - 207  */
    252,  74,  75,  76,  77,  78,  79,  80,	/* 208 - 215  */
     81,  82, 185, 251, 125, 249, 250, 255,	/* 216 - 223  */
    214, 247,  83,  84,  85,  86,  87,  88,	/* 224 - 231  */
     89,  90, 178, 212,  92, 210, 211, 213,	/* 232 - 239  */
     48,  49,  50,  51,  52,  53,  54,  55,	/* 240 - 247  */
     56,  57, 179, 219,  93, 217, 218, 159,	/* 248 - 255  */
   };

   /* Conversion table generated mechanically by Free `recode' 3.5
   for sequence IBM277..ISO-8859-1 (reversible).  */

   private static final int[] codePage277_DK = {

      0,   1,   2,   3, 156,   9, 134, 127,	/*   0 -   7  */
    151, 141, 142,  11,  12,  13,  14,  15,	/*   8 -  15  */
     16,  17,  18,  19, 157, 133,   8, 135,	/*  16 -  23  */
     24,  25, 146, 143,  28,  29,  30,  31,	/*  24 -  31  */
    128, 129, 130, 131, 132,  10,  23,  27,	/*  32 -  39  */
    136, 137, 138, 139, 140,   5,   6,   7,	/*  40 -  47  */
    144, 145,  22, 147, 148, 149, 150,   4,	/*  48 -  55  */
    152, 153, 154, 155,  20,  21, 158,  26,	/*  56 -  63  */
     32, 160, 226, 228, 224, 225, 227, 125,	/*  64 -  71  */
    231, 241,  35,  46,  60,  40,  43,  33,	/*  72 -  79  */
     38, 233, 234, 235, 232, 237, 238, 239,	/*  80 -  87  */
    236, 223, 164, 197,  42,  41,  59,  94,	/*  88 -  95  */
     45,  47, 194, 196, 192, 193, 195,  36,	/*  96 - 103  */
    199, 209, 248,  44,  37,  95,  62,  63,	/* 104 - 111  */
    166, 201, 202, 203, 200, 205, 206, 207,	/* 112 - 119  */
    204,  96,  58, 198, 216,  39,  61,  34,	/* 120 - 127  */
     64,  97,  98,  99, 100, 101, 102, 103,	/* 128 - 135  */
    104, 105, 171, 187, 240, 253, 254, 177,	/* 136 - 143  */
    176, 106, 107, 108, 109, 110, 111, 112,	/* 144 - 151  */
    113, 114, 170, 186, 123, 184,  91,  93,	/* 152 - 159  */
    181, 252, 115, 116, 117, 118, 119, 120,	/* 160 - 167  */
    121, 122, 161, 191, 208, 221, 222, 174,	/* 168 - 175  */
    162, 163, 165, 183, 169, 167, 182, 188,	/* 176 - 183  */
    189, 190, 172, 124, 175, 168, 180, 215,	/* 184 - 191  */
    230,  65,  66,  67,  68,  69,  70,  71,	/* 192 - 199  */
     72,  73, 173, 244, 246, 242, 243, 245,	/* 200 - 207  */
    229,  74,  75,  76,  77,  78,  79,  80,	/* 208 - 215  */
     81,  82, 185, 251, 126, 249, 250, 255,	/* 216 - 223  */
     92, 247,  83,  84,  85,  86,  87,  88,	/* 224 - 231  */
     89,  90, 178, 212, 214, 210, 211, 213,	/* 232 - 239  */
     48,  49,  50,  51,  52,  53,  54,  55,	/* 240 - 247  */
     56,  57, 179, 219, 220, 217, 218, 159,	/* 248 - 255  */
  };

/* Conversion table generated mechanically by Free `recode' 3.5
   for sequence IBM277..ISO-8859-1 (reversible).  */

   private static final int[] codePage277_NO = {
      0,   1,   2,   3, 156,   9, 134, 127,	/*   0 -   7  */
    151, 141, 142,  11,  12,  13,  14,  15,	/*   8 -  15  */
     16,  17,  18,  19, 157, 133,   8, 135,	/*  16 -  23  */
     24,  25, 146, 143,  28,  29,  30,  31,	/*  24 -  31  */
    128, 129, 130, 131, 132,  10,  23,  27,	/*  32 -  39  */
    136, 137, 138, 139, 140,   5,   6,   7,	/*  40 -  47  */
    144, 145,  22, 147, 148, 149, 150,   4,	/*  48 -  55  */
    152, 153, 154, 155,  20,  21, 158,  26,	/*  56 -  63  */
     32, 160, 226, 228, 224, 225, 227, 125,	/*  64 -  71  */
    231, 241,  35,  46,  60,  40,  43,  33,	/*  72 -  79  */
     38, 233, 234, 235, 232, 237, 238, 239,	/*  80 -  87  */
    236, 223, 164, 197,  42,  41,  59,  94,	/*  88 -  95  */
     45,  47, 194, 196, 192, 193, 195,  36,	/*  96 - 103  */
    199, 209, 248,  44,  37,  95,  62,  63,	/* 104 - 111  */
    166, 201, 202, 203, 200, 205, 206, 207,	/* 112 - 119  */
    204,  96,  58, 198, 216,  39,  61,  34,	/* 120 - 127  */
     64,  97,  98,  99, 100, 101, 102, 103,	/* 128 - 135  */
    104, 105, 171, 187, 240, 253, 254, 177,	/* 136 - 143  */
    176, 106, 107, 108, 109, 110, 111, 112,	/* 144 - 151  */
    113, 114, 170, 186, 123, 184,  91,  93,	/* 152 - 159  */
    181, 252, 115, 116, 117, 118, 119, 120,	/* 160 - 167  */
    121, 122, 161, 191, 208, 221, 222, 174,	/* 168 - 175  */
    162, 163, 165, 183, 169, 167, 182, 188,	/* 176 - 183  */
    189, 190, 172, 124, 175, 168, 180, 215,	/* 184 - 191  */
    230,  65,  66,  67,  68,  69,  70,  71,	/* 192 - 199  */
     72,  73, 173, 244, 246, 242, 243, 245,	/* 200 - 207  */
    229,  74,  75,  76,  77,  78,  79,  80,	/* 208 - 215  */
     81,  82, 185, 251, 126, 249, 250, 255,	/* 216 - 223  */
     92, 247,  83,  84,  85,  86,  87,  88,	/* 224 - 231  */
     89,  90, 178, 212, 214, 210, 211, 213,	/* 232 - 239  */
     48,  49,  50,  51,  52,  53,  54,  55,	/* 240 - 247  */
     56,  57, 179, 219, 220, 217, 218, 159,	/* 248 - 255  */
  };

/* Conversion table generated mechanically by Free `recode' 3.5
   for sequence IBM280..ISO-8859-1 (reversible).  */

   private static final int[] codePage280 = {
      0,   1,   2,   3, 156,   9, 134, 127,	/*   0 -   7  */
    151, 141, 142,  11,  12,  13,  14,  15,	/*   8 -  15  */
     16,  17,  18,  19, 157, 133,   8, 135,	/*  16 -  23  */
     24,  25, 146, 143,  28,  29,  30,  31,	/*  24 -  31  */
    128, 129, 130, 131, 132,  10,  23,  27,	/*  32 -  39  */
    136, 137, 138, 139, 140,   5,   6,   7,	/*  40 -  47  */
    144, 145,  22, 147, 148, 149, 150,   4,	/*  48 -  55  */
    152, 153, 154, 155,  20,  21, 158,  26,	/*  56 -  63  */
     32, 160, 226, 228, 123, 225, 227, 229,	/*  64 -  71  */
     92, 241, 176,  46,  60,  40,  43,  33,	/*  72 -  79  */
     38,  93, 234, 235, 125, 237, 238, 239,	/*  80 -  87  */
    126, 223, 233,  36,  42,  41,  59,  94,	/*  88 -  95  */
     45,  47, 194, 196, 192, 193, 195, 197,	/*  96 - 103  */
    199, 209, 242,  44,  37,  95,  62,  63,	/* 104 - 111  */
    248, 201, 202, 203, 200, 205, 206, 207,	/* 112 - 119  */
    204, 249,  58, 163, 167,  39,  61,  34,	/* 120 - 127  */
    216,  97,  98,  99, 100, 101, 102, 103,	/* 128 - 135  */
    104, 105, 171, 187, 240, 253, 254, 177,	/* 136 - 143  */
     91, 106, 107, 108, 109, 110, 111, 112,	/* 144 - 151  */
    113, 114, 170, 186, 230, 184, 198, 164,	/* 152 - 159  */
    181, 236, 115, 116, 117, 118, 119, 120,	/* 160 - 167  */
    121, 122, 161, 191, 208, 221, 222, 174,	/* 168 - 175  */
    162,  35, 165, 183, 169,  64, 182, 188,	/* 176 - 183  */
    189, 190, 172, 124, 175, 168, 180, 215,	/* 184 - 191  */
    224,  65,  66,  67,  68,  69,  70,  71,	/* 192 - 199  */
     72,  73, 173, 244, 246, 166, 243, 245,	/* 200 - 207  */
    232,  74,  75,  76,  77,  78,  79,  80,	/* 208 - 215  */
     81,  82, 185, 251, 252,  96, 250, 255,	/* 216 - 223  */
    231, 247,  83,  84,  85,  86,  87,  88,	/* 224 - 231  */
     89,  90, 178, 212, 214, 210, 211, 213,	/* 232 - 239  */
     48,  49,  50,  51,  52,  53,  54,  55,	/* 240 - 247  */
     56,  57, 179, 219, 220, 217, 218, 159,	/* 248 - 255  */
   };

   /* Conversion table generated mechanically by Free `recode' 3.5
   for sequence IBM284..ISO-8859-1 (reversible).  */
   private static final int[] codePage284 =  {
      0,   1,   2,   3, 156,   9, 134, 127,	/*   0 -   7  */
    151, 141, 142,  11,  12,  13,  14,  15,	/*   8 -  15  */
     16,  17,  18,  19, 157, 133,   8, 135,	/*  16 -  23  */
     24,  25, 146, 143,  28,  29,  30,  31,	/*  24 -  31  */
    128, 129, 130, 131, 132,  10,  23,  27,	/*  32 -  39  */
    136, 137, 138, 139, 140,   5,   6,   7,	/*  40 -  47  */
    144, 145,  22, 147, 148, 149, 150,   4,	/*  48 -  55  */
    152, 153, 154, 155,  20,  21, 158,  26,	/*  56 -  63  */
     32, 160, 226, 228, 224, 225, 227, 229,	/*  64 -  71  */
    231, 166,  91,  46,  60,  40,  43, 124,	/*  72 -  79  */
     38, 233, 234, 235, 232, 237, 238, 239,	/*  80 -  87  */
    236, 223,  93,  36,  42,  41,  59, 172,	/*  88 -  95  */
     45,  47, 194, 196, 192, 193, 195, 197,	/*  96 - 103  */
    199,  35, 241,  44,  37,  95,  62,  63,	/* 104 - 111  */
    248, 201, 202, 203, 200, 205, 206, 207,	/* 112 - 119  */
    204,  96,  58, 209,  64,  39,  61,  34,	/* 120 - 127  */
    216,  97,  98,  99, 100, 101, 102, 103,	/* 128 - 135  */
    104, 105, 171, 187, 240, 253, 254, 177,	/* 136 - 143  */
    176, 106, 107, 108, 109, 110, 111, 112,	/* 144 - 151  */
    113, 114, 170, 186, 230, 184, 198, 164,	/* 152 - 159  */
    181, 168, 115, 116, 117, 118, 119, 120,	/* 160 - 167  */
    121, 122, 161, 191, 208, 221, 222, 174,	/* 168 - 175  */
    162, 163, 165, 183, 169, 167, 182, 188,	/* 176 - 183  */
    189, 190,  94,  33, 175, 126, 180, 215,	/* 184 - 191  */
    123,  65,  66,  67,  68,  69,  70,  71,	/* 192 - 199  */
     72,  73, 173, 244, 246, 242, 243, 245,	/* 200 - 207  */
    125,  74,  75,  76,  77,  78,  79,  80,	/* 208 - 215  */
     81,  82, 185, 251, 252, 249, 250, 255,	/* 216 - 223  */
     92, 247,  83,  84,  85,  86,  87,  88,	/* 224 - 231  */
     89,  90, 178, 212, 214, 210, 211, 213,	/* 232 - 239  */
     48,  49,  50,  51,  52,  53,  54,  55,	/* 240 - 247  */
     56,  57, 179, 219, 220, 217, 218, 159,	/* 248 - 255  */
   };

/* Conversion table generated mechanically by Free `recode' 3.5
   for sequence IBM297..ISO-8859-1 (reversible).  */
   private static final int[] codePage297 =  {
      0,   1,   2,   3, 156,   9, 134, 127,	/*   0 -   7  */
    151, 141, 142,  11,  12,  13,  14,  15,	/*   8 -  15  */
     16,  17,  18,  19, 157, 133,   8, 135,	/*  16 -  23  */
     24,  25, 146, 143,  28,  29,  30,  31,	/*  24 -  31  */
    128, 129, 130, 131, 132,  10,  23,  27,	/*  32 -  39  */
    136, 137, 138, 139, 140,   5,   6,   7,	/*  40 -  47  */
    144, 145,  22, 147, 148, 149, 150,   4,	/*  48 -  55  */
    152, 153, 154, 155,  20,  21, 158,  26,	/*  56 -  63  */
     32, 160, 226, 228,  64, 225, 227, 229,	/*  64 -  71  */
     92, 241, 176,  46,  60,  40,  43,  33,	/*  72 -  79  */
     38, 123, 234, 235, 125, 237, 238, 239,	/*  80 -  87  */
    236, 223, 167,  36,  42,  41,  59,  94,	/*  88 -  95  */
     45,  47, 194, 196, 192, 193, 195, 197,	/*  96 - 103  */
    199, 209, 249,  44,  37,  95,  62,  63,	/* 104 - 111  */
    248, 201, 202, 203, 200, 205, 206, 207,	/* 112 - 119  */
    204, 181,  58, 163, 224,  39,  61,  34,	/* 120 - 127  */
    216,  97,  98,  99, 100, 101, 102, 103,	/* 128 - 135  */
    104, 105, 171, 187, 240, 253, 254, 177,	/* 136 - 143  */
     91, 106, 107, 108, 109, 110, 111, 112,	/* 144 - 151  */
    113, 114, 170, 186, 230, 184, 198, 164,	/* 152 - 159  */
     96, 168, 115, 116, 117, 118, 119, 120,	/* 160 - 167  */
    121, 122, 161, 191, 208, 221, 222, 174,	/* 168 - 175  */
    162,  35, 165, 183, 169,  93, 182, 188,	/* 176 - 183  */
    189, 190, 172, 124, 175, 126, 180, 215,	/* 184 - 191  */
    233,  65,  66,  67,  68,  69,  70,  71,	/* 192 - 199  */
     72,  73, 173, 244, 246, 242, 243, 245,	/* 200 - 207  */
    232,  74,  75,  76,  77,  78,  79,  80,	/* 208 - 215  */
     81,  82, 185, 251, 252, 166, 250, 255,	/* 216 - 223  */
    231, 247,  83,  84,  85,  86,  87,  88,	/* 224 - 231  */
     89,  90, 178, 212, 214, 210, 211, 213,	/* 232 - 239  */
     48,  49,  50,  51,  52,  53,  54,  55,	/* 240 - 247  */
     56,  57, 179, 219, 220, 217, 218, 159,	/* 248 - 255  */
   };

/* Conversion table generated mechanically by Free `recode' 3.5
   for sequence EBCDIC-PT..ISO-8859-1 (reversible).  */

   private static final int[] codePage37PT = {
      0,   1,   2,   3, 156,   9, 134, 127,	/*   0 -   7  */
    151, 141, 142,  11,  12,  13,  14,  15,	/*   8 -  15  */
     16,  17,  18,  19, 157, 133,   8, 135,	/*  16 -  23  */
     24,  25, 146, 143,  28,  29,  30,  31,	/*  24 -  31  */
    128, 129, 130, 131, 132,  10,  23,  27,	/*  32 -  39  */
    136, 137, 138, 139, 140,   5,   6,   7,	/*  40 -  47  */
    144, 145,  22, 147, 148, 149, 150,   4,	/*  48 -  55  */
    152, 153, 154, 155,  20,  21, 158,  26,	/*  56 -  63  */
     32, 193, 194, 123, 196, 197, 198, 224,	/*  64 -  71  */
    200, 201,  91,  46,  60,  40,  43,  33,	/*  72 -  79  */
     38, 216, 217, 226, 192, 228, 229, 230,	/*  80 -  87  */
    161, 232,  93,  36,  42,  41,  59,  94,	/*  88 -  95  */
     45,  47, 214,  35, 209, 126, 210, 215,	/*  96 - 103  */
    212, 233, 245,  44,  37,  95,  62,  63,	/* 104 - 111  */
    242, 248, 249, 162, 163, 164, 165, 166,	/* 112 - 119  */
    167,  96,  58, 195, 213,  39,  61,  34,	/* 120 - 127  */
     64,  97,  98,  99, 100, 101, 102, 103,	/* 128 - 135  */
    104, 105,  92, 124, 246, 168, 243, 125,	/* 136 - 143  */
    240, 106, 107, 108, 109, 110, 111, 112,	/* 144 - 151  */
    113, 114, 169, 244, 247, 211, 241, 255,	/* 152 - 159  */
    160, 231, 115, 116, 117, 118, 119, 120,	/* 160 - 167  */
    121, 122, 170, 171, 172, 173, 174, 175,	/* 168 - 175  */
    176, 177, 178, 179, 208, 181, 182, 183,	/* 176 - 183  */
    184, 185, 186, 187, 188, 189, 190, 191,	/* 184 - 191  */
    227,  65,  66,  67,  68,  69,  70,  71,	/* 192 - 199  */
     72,  73, 202, 203, 204, 205, 206, 207,	/* 200 - 207  */
    180,  74,  75,  76,  77,  78,  79,  80,	/* 208 - 215  */
     81,  82, 218, 219, 220, 221, 222, 223,	/* 216 - 223  */
    199, 225,  83,  84,  85,  86,  87,  88,	/* 224 - 231  */
     89,  90, 234, 235, 236, 237, 238, 239,	/* 232 - 239  */
     48,  49,  50,  51,  52,  53,  54,  55,	/* 240 - 247  */
     56,  57, 250, 251, 252, 253, 254, 159,	/* 248 - 255  */
  };

/* Conversion table generated mechanically by Free `recode' 3.5
   for sequence IBM500..ISO-8859-1 (reversible).  */

   private static final int[] codePage500ch = {

      0,   1,   2,   3, 156,   9, 134, 127,	/*   0 -   7  */
    151, 141, 142,  11,  12,  13,  14,  15,	/*   8 -  15  */
     16,  17,  18,  19, 157, 133,   8, 135,	/*  16 -  23  */
     24,  25, 146, 143,  28,  29,  30,  31,	/*  24 -  31  */
    128, 129, 130, 131, 132,  10,  23,  27,	/*  32 -  39  */
    136, 137, 138, 139, 140,   5,   6,   7,	/*  40 -  47  */
    144, 145,  22, 147, 148, 149, 150,   4,	/*  48 -  55  */
    152, 153, 154, 155,  20,  21, 158,  26,	/*  56 -  63  */
     32, 160, 226, 228, 224, 225, 227, 229,	/*  64 -  71  */
    231, 241,  91,  46,  60,  40,  43,  33,	/*  72 -  79  */
     38, 233, 234, 235, 232, 237, 238, 239,	/*  80 -  87  */
    236, 223,  93,  36,  42,  41,  59,  94,	/*  88 -  95  */
     45,  47, 194, 196, 192, 193, 195, 197,	/*  96 - 103  */
    199, 209, 166,  44,  37,  95,  62,  63,	/* 104 - 111  */
    248, 201, 202, 203, 200, 205, 206, 207,	/* 112 - 119  */
    204,  96,  58,  35,  64,  39,  61,  34,	/* 120 - 127  */
    216,  97,  98,  99, 100, 101, 102, 103,	/* 128 - 135  */
    104, 105, 171, 187, 240, 253, 254, 177,	/* 136 - 143  */
    176, 106, 107, 108, 109, 110, 111, 112,	/* 144 - 151  */
    113, 114, 170, 186, 230, 184, 198, 164,	/* 152 - 159  */
    181, 126, 115, 116, 117, 118, 119, 120,	/* 160 - 167  */
    121, 122, 161, 191, 208, 221, 222, 174,	/* 168 - 175  */
    162, 163, 165, 183, 169, 167, 182, 188,	/* 176 - 183  */
    189, 190, 172, 124, 175, 168, 180, 215,	/* 184 - 191  */
    123,  65,  66,  67,  68,  69,  70,  71,	/* 192 - 199  */
     72,  73, 173, 244, 246, 242, 243, 245,	/* 200 - 207  */
    125,  74,  75,  76,  77,  78,  79,  80,	/* 208 - 215  */
     81,  82, 185, 251, 252, 249, 250, 255,	/* 216 - 223  */
     92, 247,  83,  84,  85,  86,  87,  88,	/* 224 - 231  */
     89,  90, 178, 212, 214, 210, 211, 213,	/* 232 - 239  */
     48,  49,  50,  51,  52,  53,  54,  55,	/* 240 - 247  */
     56,  57, 179, 219, 220, 217, 218, 159,	/* 248 - 255  */
  };


/* Conversion table generated mechanically by Free `recode' 3.5
   for sequence IBM285..ISO-8859-1 (reversible).  */

   private static final int[] codePage285 = {

      0,   1,   2,   3, 156,   9, 134, 127,	/*   0 -   7  */
    151, 141, 142,  11,  12,  13,  14,  15,	/*   8 -  15  */
     16,  17,  18,  19, 157, 133,   8, 135,	/*  16 -  23  */
     24,  25, 146, 143,  28,  29,  30,  31,	/*  24 -  31  */
    128, 129, 130, 131, 132,  10,  23,  27,	/*  32 -  39  */
    136, 137, 138, 139, 140,   5,   6,   7,	/*  40 -  47  */
    144, 145,  22, 147, 148, 149, 150,   4,	/*  48 -  55  */
    152, 153, 154, 155,  20,  21, 158,  26,	/*  56 -  63  */
     32, 160, 226, 228, 224, 225, 227, 229,	/*  64 -  71  */
    231, 241,  36,  46,  60,  40,  43, 124,	/*  72 -  79  */
     38, 233, 234, 235, 232, 237, 238, 239,	/*  80 -  87  */
    236, 223,  33, 163,  42,  41,  59, 172,	/*  88 -  95  */
     45,  47, 194, 196, 192, 193, 195, 197,	/*  96 - 103  */
    199, 209, 166,  44,  37,  95,  62,  63,	/* 104 - 111  */
    248, 201, 202, 203, 200, 205, 206, 207,	/* 112 - 119  */
    204,  96,  58,  35,  64,  39,  61,  34,	/* 120 - 127  */
    216,  97,  98,  99, 100, 101, 102, 103,	/* 128 - 135  */
    104, 105, 171, 187, 240, 253, 254, 177,	/* 136 - 143  */
    176, 106, 107, 108, 109, 110, 111, 112,	/* 144 - 151  */
    113, 114, 170, 186, 230, 184, 198, 164,	/* 152 - 159  */
    181, 175, 115, 116, 117, 118, 119, 120,	/* 160 - 167  */
    121, 122, 161, 191, 208, 221, 222, 174,	/* 168 - 175  */
    162,  91, 165, 183, 169, 167, 182, 188,	/* 176 - 183  */
    189, 190,  94,  93, 126, 168, 180, 215,	/* 184 - 191  */
    123,  65,  66,  67,  68,  69,  70,  71,	/* 192 - 199  */
     72,  73, 173, 244, 246, 242, 243, 245,	/* 200 - 207  */
    125,  74,  75,  76,  77,  78,  79,  80,	/* 208 - 215  */
     81,  82, 185, 251, 252, 249, 250, 255,	/* 216 - 223  */
     92, 247,  83,  84,  85,  86,  87,  88,	/* 224 - 231  */
     89,  90, 178, 212, 214, 210, 211, 213,	/* 232 - 239  */
     48,  49,  50,  51,  52,  53,  54,  55,	/* 240 - 247  */
     56,  57, 179, 219, 220, 217, 218, 159,	/* 248 - 255  */
  };

  /* Conversion table generated mechanically by Free `recode' 3.5
   for sequence IBM424..ISO-8859-1 (reversible).  */

   private static final int[] codePage424 = {
      0,   1,   2,   3, 156,   9, 134, 127,	/*   0 -   7  */
    151, 141, 142,  11,  12,  13,  14,  15,	/*   8 -  15  */
     16,  17,  18,  19, 157, 133,   8, 135,	/*  16 -  23  */
     24,  25, 146, 143,  28,  29,  30,  31,	/*  24 -  31  */
    128, 129, 130, 131, 132,  10,  23,  27,	/*  32 -  39  */
    136, 137, 138, 139, 140,   5,   6,   7,	/*  40 -  47  */
    144, 145,  22, 147, 148, 149, 150,   4,	/*  48 -  55  */
    152, 153, 154, 155,  20,  21, 158,  26,	/*  56 -  63  */
     32, 193, 194, 195, 196, 197, 198, 199,	/*  64 -  71  */
    200, 201, 162,  46,  60,  40,  43, 124,	/*  72 -  79  */
     38, 216, 217, 226, 227, 228, 229, 230,	/*  80 -  87  */
    231, 232,  33,  36,  42,  41,  59, 172,	/*  88 -  95  */
     45,  47, 233, 192, 186, 161, 210, 191,	/*  96 - 103  */
    212, 213, 166,  44,  37,  95,  62,  63,	/* 104 - 111  */
    242, 248, 249, 209, 160, 255, 234, 241,	/* 112 - 119  */
    177,  96,  58,  35,  64,  39,  61,  34,	/* 120 - 127  */
    214,  97,  98,  99, 100, 101, 102, 103,	/* 128 - 135  */
    104, 105, 171, 187, 246, 211, 243, 208,	/* 136 - 143  */
    176, 106, 107, 108, 109, 110, 111, 112,	/* 144 - 151  */
    113, 114, 218, 240, 225, 184, 245, 164,	/* 152 - 159  */
    181, 126, 115, 116, 117, 118, 119, 120,	/* 160 - 167  */
    121, 122, 170, 224, 244, 202, 175, 174,	/* 168 - 175  */
     94, 163, 165, 183, 169, 167, 182, 188,	/* 176 - 183  */
    189, 190,  91,  93, 250, 168, 180, 215,	/* 184 - 191  */
    123,  65,  66,  67,  68,  69,  70,  71,	/* 192 - 199  */
     72,  73, 173, 203, 204, 205, 206, 207,	/* 200 - 207  */
    125,  74,  75,  76,  77,  78,  79,  80,	/* 208 - 215  */
     81,  82, 185, 219, 220, 221, 222, 223,	/* 216 - 223  */
     92, 247,  83,  84,  85,  86,  87,  88,	/* 224 - 231  */
     89,  90, 178, 235, 236, 237, 238, 239,	/* 232 - 239  */
     48,  49,  50,  51,  52,  53,  54,  55,	/* 240 - 247  */
     56,  57, 179, 251, 252, 253, 254, 159,	/* 248 - 255  */
  };

/* Conversion table generated mechanically by my own hands.  */
   private static final int[] codePage870 = {
      0x00, 0x01, 0x02, 0x03, 0xA6, 0x09, 0x86, 0x7F,
      0x97, 0xA9, 0xAB, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F,
      0x10, 0x11, 0x12, 0x13, 0xAC, 0x85, 0x08, 0x87,
      0x18, 0x19, 0x92, 0xAE, 0x1C, 0x1D, 0x1E, 0x1F,
      0x80, 0x81, 0x82, 0x83, 0x84, 0x0A, 0x17, 0x1B,
      0x88, 0x89, 0xB1, 0x8B, 0xB5, 0x05, 0x06, 0x07,
      0x90, 0x91, 0x16, 0x93, 0x94, 0x95, 0x96, 0x04,
      0x98, 0x99, 0xB6, 0x9B, 0x14, 0x15, 0xB7, 0x1A,
      0x20, 0xA0, 0xE2, 0xE4, 0xFE, 0xE1, 0xE3, 0xE8,
      0xE7, 0xE6, 0x5B, 0x2E, 0x3C, 0x28, 0x2B, 0x21,
      0x26, 0xE9, 0xEA, 0xEB, 0xF9, 0xED, 0xEE, 0xBE,
      0xE5, 0xDF, 0x5D, 0x24, 0x2A, 0x29, 0x3B, 0x5E,
      0x2D, 0x2F, 0xC2, 0xC4, 0xBD, 0xC1, 0xC3, 0xC8,
      0xC7, 0xC6, 0x7C, 0x2C, 0x25, 0x5F, 0x3E, 0x3F,
      0xA1, 0xC9, 0xCA, 0xCB, 0xD9, 0xCD, 0xCE, 0xBC,
      0xC5, 0x60, 0x3A, 0x23, 0x40, 0x27, 0x3D, 0x22,
      0xA2, 0x61, 0x62, 0x63, 0x64, 0x65, 0x66, 0x67,
      0x68, 0x69, 0x9C, 0xF2, 0xF0, 0xFD, 0xF8, 0xBA,
      0xB0, 0x6A, 0x6B, 0x6C, 0x6D, 0x6E, 0x6F, 0x70,
      0x71, 0x72, 0xB3, 0xF1, 0x9A, 0xB8, 0xB2, 0xA4,
      0xB9, 0x7E, 0x73, 0x74, 0x75, 0x76, 0x77, 0x78,
      0x79, 0x7A, 0x8C, 0xD2, 0xD0, 0xDD, 0xD8, 0xAA,
      0xFF, 0xA5, 0xBF, 0xDE, 0xAF, 0xA7, 0x9E, 0x9F,
      0x8E, 0x8F, 0xA3, 0xD1, 0x8A, 0xA8, 0xB4, 0xD7,
      0x7B, 0x41, 0x42, 0x43, 0x44, 0x45, 0x46, 0x47,
      0x48, 0x49, 0xAD, 0xF4, 0xF6, 0xE0, 0xF3, 0xF5,
      0x7D, 0x4A, 0x4B, 0x4C, 0x4D, 0x4E, 0x4F, 0x50,
      0x51, 0x52, 0xCC, 0xFB, 0xFC, 0x9D, 0xFA, 0xEC,
      0x5C, 0xF7, 0x53, 0x54, 0x55, 0x56, 0x57, 0x58,
      0x59, 0x5A, 0xEF, 0xD4, 0xD6, 0xC0, 0xD3, 0xD5,
      0x30, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37,
      0x38, 0x39, 0xCF, 0xDB, 0xDC, 0x8D, 0xDA, 0xBB,
   };

/* Conversion table generated mechanically by Free `recode' 3.5
   for sequence IBM870..ISO-8859-1 (reversible).  */

   private static final int[] codePage870sk = {

      0,   1,   2,   3, 156,   9, 134, 127,	/*   0 -   7  */
    151, 141, 142,  11,  12,  13,  14,  15,	/*   8 -  15  */
     16,  17,  18,  19, 157, 133,   8, 135,	/*  16 -  23  */
     24,  25, 146, 143,  28,  29,  30,  31,	/*  24 -  31  */
    128, 129, 130, 131, 132,  10,  23,  27,	/*  32 -  39  */
    136, 137, 138, 139, 140,   5,   6,   7,	/*  40 -  47  */
    144, 145,  22, 147, 148, 149, 150,   4,	/*  48 -  55  */
    152, 153, 154, 155,  20,  21, 158,  26,	/*  56 -  63  */
     32, 160, 194, 228, 192, 225, 198, 226,	/*  64 -  71  */
    231, 248,  91,  46,  60,  40,  43,  33,	/*  72 -  79  */
     38, 233, 217, 235, 227, 237, 229, 230,	/*  80 -  87  */
    200, 223,  93,  36,  42,  41,  59,  94,	/*  88 -  95  */
     45,  47, 236, 196, 209, 193, 210, 191,	/*  96 - 103  */
    199, 216, 124,  44,  37,  95,  62,  63,	/* 104 - 111  */
    242, 201, 249, 203, 163, 205, 165, 166,	/* 112 - 119  */
    181,  96,  58,  35,  64,  39,  61,  34,	/* 120 - 127  */
    241,  97,  98,  99, 100, 101, 102, 103,	/* 128 - 135  */
    104, 105, 224, 213, 204, 253, 206, 208,	/* 136 - 143  */
    176, 106, 107, 108, 109, 110, 111, 112,	/* 144 - 151  */
    113, 114, 169, 162, 197, 184, 245, 164,	/* 152 - 159  */
    161, 126, 115, 116, 117, 118, 119, 120,	/* 160 - 167  */
    121, 122, 170, 171, 172, 221, 174, 175,	/* 168 - 175  */
    183, 177, 178, 179, 190, 167, 182, 240,	/* 176 - 183  */
    238, 185, 186, 187, 188, 168, 180, 215,	/* 184 - 191  */
    123,  65,  66,  67,  68,  69,  70,  71,	/* 192 - 199  */
     72,  73, 173, 244, 246, 255, 243, 207,	/* 200 - 207  */
    125,  74,  75,  76,  77,  78,  79,  80,	/* 208 - 215  */
     81,  82, 254, 219, 252, 202, 250, 232,	/* 216 - 223  */
     92, 247,  83,  84,  85,  86,  87,  88,	/* 224 - 231  */
     89,  90, 234, 212, 214, 195, 211, 239,	/* 232 - 239  */
     48,  49,  50,  51,  52,  53,  54,  55,	/* 240 - 247  */
     56,  57, 222, 251, 220, 189, 218, 159,	/* 248 - 255  */
  };

   /* Added 1025 code pages by Fomin, Leonid <leonid.fomin@mosnar.com */
   /* Conversion table generated mechanically by my own hands. */
   private static final int[] codePage1025 = {
   0,  1,  2,  3,  164,  9,  134,  127,
   151,  165,  166,  11,  12,  13,  14,  15,
   16,  17,  18,  19,  169,  133,  8,  135,
   24,  25,  146,  171,  28,  29,  30,  31,
   172,  174,  130,  176,  132,  10,  23,  27,
   136,  137,  177,  139,  180,  5,  6,  7,
   181,  145,  22,  147,  148,  149,  150,  4,
   152,  153,  182,  155,  20,  21,  183,  26,
   32,  160,  144,  131,  184,  186,  190,  179,
   191,  188,  91,  46,  60,  40,  43,  33,
   38,  154,  156,  158,  157,  162,  159,  218,
   185,  128,  93,  36,  42,  41,  59,  94,
   45,  47,  129,  168,  170,  189,  178,  175,
   163,  138,  124,  44,  37,  95,  62,  63,
   140,  142,  141,  173,  161,  143,  254,  224,
   225,  96,  58,  35,  64,  39,  61,  34,
   246,  97,  98,  99,  100,  101,  102,  103,
   104,  105,  228,  229,  244,  227,  245,  232,
   233,  106,  107,  108,  109,  110,  111,  112,
   113,  114,  234,  235,  236,  237,  238,  239,
   255,  126,  115,  116,  117,  118,  119,  120,
   121,  122,  240,  241,  242,  243,  230,  226,
   252,  251,  231,  248,  253,  249,  247,  250,
   222,  192,  193,  214,  196,  197,  212,  195,
   123,  65,  66,  67,  68,  69,  70,  71,
   72,  73,  213,  200,  201,  202,  203,  204,
   125,  74,  75,  76,  77,  78,  79,  80,
   81,  82,  205,  206,  207,  223,  208,  209,
   92,  167,  83,  84,  85,  86,  87,  88,
   89,  90,  210,  211,  198,  194,  220,  219,
   48,  49,  50,  51,  52,  53,  54,  55,
   56,  57,  199,  216,  221,  217,  215,  187,
   };

   public static final int[] getCodePage(String cp) {

      if (cp.equals("37"))
         return codePage37;
      if (cp.equals("37PT"))
         return codePage37PT;
      if (cp.equals("273"))
         return codePage273;
      if (cp.equals("277-dk"))
         return codePage277_DK;
      if (cp.equals("277-no"))
         return codePage277_NO;
      if (cp.equals("280"))
         return codePage280;
      if (cp.equals("284"))
         return codePage284;
      if (cp.equals("285"))
         return codePage285;
      if (cp.equals("297"))
         return codePage297;
      if (cp.equals("424"))
         return codePage500ch;
      if (cp.equals("500-ch"))
         return codePage500ch;
      if (cp.equals("870-pl"))
         return codePage870;
      if (cp.equals("870-sk"))
         return codePage870sk;
      if (cp.equals("1025-r"))
         return codePage1025;

      return codePage37;

   }

   public static final String[] getAvailableCodePages() {

      return  acp;

   }


}
