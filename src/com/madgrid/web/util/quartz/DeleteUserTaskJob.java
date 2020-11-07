package com.madgrid.web.util.quartz;
 
import java.util.Map;
 
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
 
public class DeleteUserTaskJob implements Job
{
  public void execute(JobExecutionContext context) throws JobExecutionException 
  {
	Map dataMap = context.getJobDetail().getJobDataMap();
	DeleteUserTask deleteUserTask = (DeleteUserTask)dataMap.get("deleteUserTask");
	deleteUserTask.deleteUser();
  }
}