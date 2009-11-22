/**
 * $Id$
 * 
 * Title: tn5250J
 * Copyright:   Copyright (c) 2001,2009
 * Company:
 * @author: master_jaf
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
package org.tn5250j.cp;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.tn5250j.encoding.CharMappings;
import org.tn5250j.encoding.CodePage;

/**
 * Microbenchmark to test encoding+decoding speed
 * of the 'old' CCSID1148<->Unicode converte versus the new leaner one.
 * 
 * Result: The new implementation is much faster!
 * 
 * @author master_jaf
 */
public class MicroBenchmark_CCSID1148 {

	private static final int RUNS = 50000;
	
	private final char[] TESTSTRING = "abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ0987654321abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ0987654321abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ0987654321abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ0987654321abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ0987654321abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ0987654321abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ0987654321abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ0987654321abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ0987654321abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ0987654321".toCharArray();
	private CodePage cp;
	private CCSID1148 cpex;	

	@Before
	public void setup() {
		cp = CharMappings.getCodePage("1148");
		cpex = new CCSID1148();
		cpex.init();
		assertNotNull("At least an ASCII Codepage should be available.", cp);
		assertNotNull("At least an ASCII Codepage should be available.", cpex);
	}
	
	/**
	 * Speed test for new implementation ...
	 */
	@Test(timeout=10000)
	public void testNewConverter1148() {
		for (int i=0; i<RUNS; i++) {
			realRunNew();
		}
	}
	
	/**
	 * Speed test for old implementation ....
	 */
	@Test(timeout=10000)
	public void testOldConverter1148() {
		for (int i=0; i<RUNS; i++) {
			realRunOld();
		}
	}

	/*
	 * =======================================================================
	 */
	
	private void realRunOld() {
		for (int i=0; i<TESTSTRING.length; i++) {
			final char beginvalue = TESTSTRING[i];
			final byte converted = cp.uni2ebcdic(beginvalue);
			cp.ebcdic2uni(converted);
		}
	}

	/*
	 * ======================================================================
	 */


	private void realRunNew() {
		for (int i=0; i<TESTSTRING.length; i++) {
			final char beginvalue = TESTSTRING[i];
			final byte converted = cpex.uni2ebcdic(beginvalue);
			cpex.ebcdic2uni(converted & 0xFF);
		}
	}
}
