package socially.disturbed.cron;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class CronJobs {
    public CronJobs() {
        try {
            JobDetail job = JobBuilder.newJob(UpdateRankedStatsJob.class)
                    .withIdentity("updateRankedStatsJob", "group1").build();
            Trigger cronTrigger = TriggerBuilder.newTrigger()
                    .withIdentity("cronTrigger", "group1")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0/5 * 1/1 * ? *"))
                    .build();
            Scheduler scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
            scheduler.scheduleJob(job, cronTrigger);
            System.out.println("Starting cronjob!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
