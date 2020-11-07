package com.madgrid.web.util.quartz;

import com.madgrid.web.util.dwr.DwrTools;
 
public class CalibrateClockTask 
{
	public void calibrateClock() {
		DwrTools dwrTools = DwrTools.getInstance();
		try{
			synchronized (dwrTools) {
				dwrTools.calibrateClockJob( "Uh76sagHdjhD673KfN");
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}