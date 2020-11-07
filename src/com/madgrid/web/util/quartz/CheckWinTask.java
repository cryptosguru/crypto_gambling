package com.madgrid.web.util.quartz;

import com.madgrid.web.util.dwr.DwrTools;
 
public class CheckWinTask 
{
	public void CheckWin() {
		DwrTools dwrTools = DwrTools.getInstance();
		try{
			synchronized (dwrTools) {
				dwrTools.checkWinJob();
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}