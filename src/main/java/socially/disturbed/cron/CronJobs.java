package socially.disturbed.cron;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class CronJobs {
    public CronJobs() {
        try {
            Trigger rankedStatsTrigger = TriggerBuilder.newTrigger()
                    .withIdentity("cronTrigger", "group1")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0/30 0 ? * * *"))
                    .startNow()
                    .build();
            JobDetail updateRankedStatsJob = JobBuilder.newJob(UpdateRankedStatsJob.class)
                    .withIdentity("UpdateRankedStatsJob", "group1").build();
            Scheduler scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
            scheduler.scheduleJob(updateRankedStatsJob, rankedStatsTrigger);


            Trigger highscoreTrigger = TriggerBuilder.newTrigger()
                    .withIdentity("cronTrigger", "group2")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0/10 0 ? * * *"))
                    .startNow()
                    .build();
            JobDetail updateHighscoreJob = JobBuilder.newJob(UpdateHighscoreJob.class)
                    .withIdentity("UpdateHighscoreJob", "group2").build();
            Scheduler updateHighscoreScheduler = new StdSchedulerFactory().getScheduler();
            updateHighscoreScheduler.start();
            updateHighscoreScheduler.scheduleJob(updateHighscoreJob, highscoreTrigger);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
