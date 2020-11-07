package com.madgrid.web.util.quartz;
 
import java.util.Map;
 
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
 
public class ShowGridsTaskJob implements Job
{
  public void execute(JobExecutionContext context) throws JobExecutionException 
  {
	Map dataMap = context.getJobDetail().getJobDataMap();
	ShowGridsTask showGridsTask = (ShowGridsTask)dataMap.get("showGridsTask");
	showGridsTask.showGrids();
  }
}