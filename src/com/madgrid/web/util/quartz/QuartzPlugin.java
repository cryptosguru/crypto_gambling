package com.madgrid.web.util.quartz;
 
import java.text.ParseException;
import java.util.Date;
import java.util.Map;
 
import javax.servlet.ServletException;
 
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import com.madgrid.web.util.Utils;
 
public class QuartzPlugin implements PlugIn {
 
	public void destroy() {
	}

	public void init(ActionServlet servlet, ModuleConfig config) throws ServletException {
	   ShowGridsTask showGridsTask = new ShowGridsTask();
	   JobDetail showGridsJob = new JobDetail();
	   showGridsJob.setName("showGridsJobName");
	   showGridsJob.setJobClass(ShowGridsTaskJob.class);
	   showGridsJob.setGroup( "grupo");
 
	   Map showGridsDataMap = showGridsJob.getJobDataMap();
	   showGridsDataMap.put("showGridsTask", showGridsTask);
 	   try{
		   CronTrigger trigger = new CronTrigger();
		   trigger.setName("showGridsTrigger");
		   trigger.setCronExpression("0/7 * * * * ?");
		   trigger.setGroup( "grupo");
		   trigger.setJobGroup( "grupo");
		   trigger.setJobName( "showGridsJobName");
		   trigger.setStartTime(Utils.today());

		   Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		   scheduler.start();
		   scheduler.scheduleJob(showGridsJob, trigger);
	   }catch(ParseException e){
		   e.printStackTrace();
	   }catch(SchedulerException e){
		   e.printStackTrace();
	   }catch(Exception e){
		   e.printStackTrace();
	   }
	   
	   
	   CheckWinTask checkWinTask = new CheckWinTask();
	   JobDetail checkWinJob = new JobDetail();
	   checkWinJob.setName("checkWinJobName");
	   checkWinJob.setJobClass(CheckWinTaskJob.class);
	   checkWinJob.setGroup( "grupo");
 
	   Map checkWinDataMap = checkWinJob.getJobDataMap();
	   checkWinDataMap.put("checkWinTask", checkWinTask);
 	   try{
		   CronTrigger trigger = new CronTrigger();
		   trigger.setName("checkWinTrigger");
		   trigger.setCronExpression("0/5 * * * * ?");
		   trigger.setGroup( "grupo");
		   trigger.setJobGroup( "grupo");
		   trigger.setJobName( "checkWinJobName");
		   trigger.setStartTime(Utils.today());

		   Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		   scheduler.start();
		   scheduler.scheduleJob(checkWinJob, trigger);
	   }catch(ParseException e){
		   e.printStackTrace();
	   }catch(SchedulerException e){
		   e.printStackTrace();
	   }catch(Exception e){
		   e.printStackTrace();
	   }
	   
	   
	   CheckOldTask checkOldTask = new CheckOldTask();
	   JobDetail checkOldJob = new JobDetail();
	   checkOldJob.setName("checkOldJobName");
	   checkOldJob.setJobClass(CheckOldTaskJob.class);
	   checkOldJob.setGroup( "grupo");
 
	   Map checkOldDataMap = checkOldJob.getJobDataMap();
	   checkOldDataMap.put("checkOldTask", checkOldTask);
 	   try{
		   CronTrigger trigger = new CronTrigger();
		   trigger.setName("checkOldTrigger");
		   trigger.setCronExpression("0 0 0/1 * * ?");
		   trigger.setGroup( "grupo");
		   trigger.setJobGroup( "grupo");
		   trigger.setJobName( "checkOldJobName");
		   trigger.setStartTime( Utils.today());

		   Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		   scheduler.start();
		   scheduler.scheduleJob(checkOldJob, trigger);
	   }catch(ParseException e){
		   e.printStackTrace();
	   }catch(SchedulerException e){
		   e.printStackTrace();
	   }catch(Exception e){
		   e.printStackTrace();
	   }
	   
	   
	   DeleteUserTask deleteUserTask = new DeleteUserTask();
	   JobDetail deleteUserJob = new JobDetail();
	   deleteUserJob.setName("deleteUserJobName");
	   deleteUserJob.setJobClass(DeleteUserTaskJob.class);
	   deleteUserJob.setGroup( "grupo");
 
	   Map deleteUserDataMap = deleteUserJob.getJobDataMap();
	   deleteUserDataMap.put("deleteUserTask", deleteUserTask);
 	   try{
		   CronTrigger trigger = new CronTrigger();
		   trigger.setName("deleteUserTrigger");
		   trigger.setCronExpression("0 0 4 * * ?");
		   trigger.setGroup( "grupo");
		   trigger.setJobGroup( "grupo");
		   trigger.setJobName( "deleteUserJobName");
		   trigger.setStartTime( Utils.today());

		   Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		   scheduler.start();
		   scheduler.scheduleJob(deleteUserJob, trigger);
	   }catch(ParseException e){
		   e.printStackTrace();
	   }catch(SchedulerException e){
		   e.printStackTrace();
	   }catch(Exception e){
		   e.printStackTrace();
	   }
	   
	   RestartTask restartTask = new RestartTask();
	   JobDetail restartJob = new JobDetail();
	   restartJob.setName("restartJobName");
	   restartJob.setJobClass(RestartTaskJob.class);
	   restartJob.setGroup( "grupo");
 
	   Map restartDataMap = restartJob.getJobDataMap();
	   restartDataMap.put("restartTask", restartTask);
 	   try{
		   CronTrigger trigger = new CronTrigger();
		   trigger.setName("restartTrigger");
		   trigger.setCronExpression("0 10,20,30,40,50,0 * * * ?");
		   trigger.setGroup( "grupo");
		   trigger.setJobGroup( "grupo");
		   trigger.setJobName( "restartJobName");
		   trigger.setStartTime( Utils.today());

		   Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		   scheduler.start();
		   scheduler.scheduleJob(restartJob, trigger);
	   }catch(ParseException e){
		   e.printStackTrace();
	   }catch(SchedulerException e){
		   e.printStackTrace();
	   }catch(Exception e){
		   e.printStackTrace();
	   }
	   
	   
	   RestartWarningTask restartWarningTask = new RestartWarningTask();
	   JobDetail restartWarningJob = new JobDetail();
	   restartWarningJob.setName("restartWarningJobName");
	   restartWarningJob.setJobClass(RestartWarningTaskJob.class);
	   restartWarningJob.setGroup( "grupo");
 
	   Map restartWarningDataMap = restartWarningJob.getJobDataMap();
	   restartWarningDataMap.put("restartWarningTask", restartWarningTask);
 	   try{
		   CronTrigger trigger = new CronTrigger();
		   trigger.setName("restartWarningTrigger");
		   trigger.setCronExpression("0 9,19,29,39,49,59 * * * ?");
		   trigger.setGroup( "grupo");
		   trigger.setJobGroup( "grupo");
		   trigger.setJobName( "restartWarningJobName");
		   trigger.setStartTime( Utils.today());

		   Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		   scheduler.start();
		   scheduler.scheduleJob(restartWarningJob, trigger);
	   }catch(ParseException e){
		   e.printStackTrace();
	   }catch(SchedulerException e){
		   e.printStackTrace();
	   }catch(Exception e){
		   e.printStackTrace();
	   }
	   
	   
	   CalibrateClockTask calibrateClockTask = new CalibrateClockTask();
	   JobDetail calibrateClockJob = new JobDetail();
	   calibrateClockJob.setName("calibrateClockJobName");
	   calibrateClockJob.setJobClass(CalibrateClockTaskJob.class);
	   calibrateClockJob.setGroup( "grupo");
 
	   Map calibrateClockDataMap = calibrateClockJob.getJobDataMap();
	   calibrateClockDataMap.put("calibrateClockTask", calibrateClockTask);
 	   try{
		   CronTrigger trigger = new CronTrigger();
		   trigger.setName("calibrateClockTrigger");
		   trigger.setCronExpression("0 0/1 * * * ?");
		   trigger.setGroup( "grupo");
		   trigger.setJobGroup( "grupo");
		   trigger.setJobName( "calibrateClockJobName");
		   trigger.setStartTime( Utils.today());

		   Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		   scheduler.start();
		   scheduler.scheduleJob(calibrateClockJob, trigger);
	   }catch(ParseException e){
		   e.printStackTrace();
	   }catch(SchedulerException e){
		   e.printStackTrace();
	   }catch(Exception e){
		   e.printStackTrace();
	   }
 	   
 	   
 	   DeletePaymentForwardTask deletePaymentForwardTask = new DeletePaymentForwardTask();
	   JobDetail deletePaymentForwardJob = new JobDetail();
	   deletePaymentForwardJob.setName("deletePaymentForwardJobName");
	   deletePaymentForwardJob.setJobClass(DeletePaymentForwardTaskJob.class);
	   deletePaymentForwardJob.setGroup( "grupo");

	   Map deletePaymentForwardDataMap = deletePaymentForwardJob.getJobDataMap();
	   deletePaymentForwardDataMap.put("deletePaymentForwardTask", deletePaymentForwardTask);
	   try{
		   CronTrigger trigger = new CronTrigger();
		   trigger.setName("deletePaymentForwardTrigger");
		   trigger.setCronExpression("0 0 0/1 * * ?");
		   trigger.setGroup( "grupo");
		   trigger.setJobGroup( "grupo");
		   trigger.setJobName( "deletePaymentForwardJobName");
		   trigger.setStartTime( Utils.today());

		   Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		   scheduler.start();
		   scheduler.scheduleJob(deletePaymentForwardJob, trigger);
	   }catch(ParseException e){
		   e.printStackTrace();
	   }catch(SchedulerException e){
		   e.printStackTrace();
	   }catch(Exception e){
		   e.printStackTrace();
	   }
	   
	   
	}
}