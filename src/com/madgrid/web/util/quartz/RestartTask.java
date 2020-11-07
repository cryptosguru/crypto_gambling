package com.madgrid.web.util.quartz;

import com.madgrid.web.util.dwr.DwrTools;
 
public class RestartTask 
{
	public void restart() {
		DwrTools dwrTools = DwrTools.getInstance();
		try{
			synchronized (dwrTools) {
				dwrTools.restartServerJob("Uh76sagHdjhD673KfN");
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}