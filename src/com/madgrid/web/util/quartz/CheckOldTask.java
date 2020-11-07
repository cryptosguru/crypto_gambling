package com.madgrid.web.util.quartz;

import com.madgrid.web.util.dwr.DwrTools;
 
public class CheckOldTask 
{
	public void checkOld() {
		DwrTools dwrTools = DwrTools.getInstance();
		try{
			synchronized (dwrTools) {
				dwrTools.checkOldJob();
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}