package socially.disturbed.discord;

import org.quartz.*;
import socially.disturbed.cron.CronJobs;

public class Bot {
    public Bot() throws SchedulerException {
        DiscordService service = DiscordService.getInstance();
        CronJobs cronUpdate = new CronJobs();
    }
}
