/**Copyright (C) 2004 Seagull Software
*
* This library is free software; you can redistribute it and/or
* modify it under the terms of the GNU Lesser General Public
* License as published by the Free Software Foundation; either
* version 2.1 of the License, or (at your option) any later version.
*
* This library is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
* Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public
* License along with this library; if not, write to the Free Software
* Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*
*@author bvansomeren (bvansomeren@seagull.nl)
*/
package org.tn5250j.framework.test;

import java.io.File;
import java.util.Properties;

import org.tn5250j.ScreenChar;
import org.tn5250j.ScreenField;
import org.tn5250j.ScreenFields;
import org.tn5250j.framework.Tn5250jController;
import org.tn5250j.framework.Tn5250jEvent;
import org.tn5250j.framework.Tn5250jKeyEvents;
import org.tn5250j.framework.Tn5250jListener;
import org.tn5250j.framework.Tn5250jSession;
import org.tn5250j.tools.logging.TN5250jLogFactory;
import org.tn5250j.tools.logging.TN5250jLogger;
/**
 * 
 * This very simple class demonstrates the framework by sending all output to the logging console.
 * Enjoy :-)
 */
public class TnExamplePlug extends Tn5250jListener {
	TN5250jLogger log = TN5250jLogFactory.getLogger(this.getClass());
	
	public void actionPerformed(Tn5250jEvent event) {
		log.info("Incomming event of type: "+event.getClass().toString()+"\n");
		if(event instanceof Tn5250jKeyEvents) {
			Tn5250jKeyEvents keys = (Tn5250jKeyEvents) event;
			log.info("Key strokes were: "+keys.getKeystrokes());
		}
		else {
			log.info("Character data is: \n");
			ScreenChar data[] = event.getData();
			StringBuffer text = new StringBuffer();
			for(int x=0;x<data.length;x++) {
				text.append(data[x].getChar());
			}
			log.info(text.toString());
			
			log.info("And screenfields: \n");
			ScreenFields fields = event.getFields();
			ScreenField array[] = fields.getFields();
			for(int x=0;x<array.length;x++) {
				log.info("ScreenField: "+array[x].toString());
			}
		}
	}

	
	public void init(File fileDir, Properties config) {
		
	}

	
	public void run() {
		
	}

	
	public void destroy() {
		

	}

	
	public String getName() {
		return this.getClass().getName();
	}

	
	public void setController(Tn5250jController control) {
		

	}

	
	public void sessionCreated(Tn5250jSession session) {
		log.info("Session created: "+session.getSession().getName());

	}

}
