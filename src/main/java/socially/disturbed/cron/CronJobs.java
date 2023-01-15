package socially.disturbed.cron;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class CronJobs {
    public CronJobs() {
        try {
            JobDetail job = JobBuilder.newJob(UpdateRankedStatsJob.class)
                    .withIdentity("UpdateRankedStatsJob", "group1").build();
            Trigger cronTrigger = TriggerBuilder.newTrigger()
                    .withIdentity("cronTrigger", "group1")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0/2 * * * ?"))
//                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0/30 0 ? * * *"))
                    .startNow()
                    .build();
            Scheduler scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
            scheduler.scheduleJob(job, cronTrigger);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
