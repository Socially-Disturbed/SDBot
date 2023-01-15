package socially.disturbed.discord;

import discord4j.core.GatewayDiscordClient;
import org.quartz.*;
import socially.disturbed.cron.CronJobs;

public class Bot {
    public Bot() throws SchedulerException {
        CronJobs cronUpdate = new CronJobs();
        GatewayDiscordClientWrapper gatewayDiscordClientWrapper = new GatewayDiscordClientWrapper();
        gatewayDiscordClientWrapper.init();
    }
}
