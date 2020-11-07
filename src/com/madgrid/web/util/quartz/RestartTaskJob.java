package com.madgrid.web.util.quartz;
 
import java.util.Map;
 
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
 
public class RestartTaskJob implements Job
{
  public void execute(JobExecutionContext context) throws JobExecutionException 
  {
	Map dataMap = context.getJobDetail().getJobDataMap();
	RestartTask restartTask = (RestartTask)dataMap.get("restartTask");
	restartTask.restart();
  }
}