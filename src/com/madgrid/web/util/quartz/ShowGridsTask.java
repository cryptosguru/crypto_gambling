package com.madgrid.web.util.quartz;

import com.madgrid.web.util.dwr.DwrTools;
 
public class ShowGridsTask 
{
	public void showGrids() {
		DwrTools dwrTools = DwrTools.getInstance();
		try{
			synchronized (dwrTools) {
				dwrTools.showGridsJob();
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}