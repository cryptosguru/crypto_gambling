package com.madgrid.web.util.quartz;

import com.madgrid.web.util.dwr.DwrTools;
 
public class RestartWarningTask 
{
	public void restartWarning() {
		DwrTools dwrTools = DwrTools.getInstance();
		try{
			synchronized (dwrTools) {
				dwrTools.restartWarningJob( "Uh76sagHdjhD673KfN");
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}