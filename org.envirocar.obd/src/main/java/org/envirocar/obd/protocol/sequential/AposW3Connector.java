/* 
 * enviroCar 2013
 * Copyright (C) 2013  
 * Martin Dueren, Jakob Moellers, Gerald Pape, Christopher Stephan
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
 * 
 */
package org.envirocar.obd.protocol.sequential;

import org.envirocar.core.logging.Logger;
import org.envirocar.obd.commands.CommonCommand;
import org.envirocar.obd.commands.elm.Timeout;
import org.envirocar.obd.adapter.OBDConnector;

import java.util.ArrayList;
import java.util.List;

public class AposW3Connector extends ELM327Connector {
	
	private static final Logger logger = Logger.getLogger(AposW3Connector.class);

	@Override
	public List<CommonCommand> getInitializationCommands() {
		List<CommonCommand> result = new ArrayList<CommonCommand>();
		result.add(new ObdReset());
		result.add(new AposEchoOff());
		result.add(new AposEchoOff());
		result.add(new LineFeedOff());
		result.add(new Timeout(62));
		result.add(new SelectAutoProtocol());
		return result;
	}

	@Override
	public boolean supportsDevice(String deviceName) {
		return deviceName.contains("APOS") && deviceName.contains("OBD_W3");
	}

	@Override
	public void processInitializationCommand(CommonCommand cmd) {

		if (cmd instanceof AposEchoOff) {
			if (cmd instanceof StringResultCommand) {
				String content = ((StringResultCommand) cmd).getStringResult();
				if (content.contains("OK")) {
					succesfulCount++;
				}
			}
		} else {
			super.processInitializationCommand(cmd);
		}

	}

	@Override
	public OBDConnector.ConnectionState connectionState() {
		if (succesfulCount >= 4) {
			return OBDConnector.ConnectionState.CONNECTED;
		}
		return OBDConnector.ConnectionState.DISCONNECTED;
	}

	private static class AposEchoOff extends EchoOff {
		
		@Override
		public byte[] getOutgoingBytes() {
			try {
				/*
				 * hack for too fast init requests,
				 * issue observed with Galaxy Nexus (4.3) and VW Tiguan 2013
				 */
				Thread.sleep(250);
			} catch (InterruptedException e) {
				logger.warn(e.getMessage(), e);
			}
			return super.getOutgoingBytes();
		}

		@Override
		public boolean responseAlwaysRequired() {
			return false;
		}
		
	}
}
