package socially.disturbed.discord;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.User;
import org.quartz.*;
import socially.disturbed.cron.CronJobs;

public class Bot {
    public Bot() throws SchedulerException {
        DiscordService service = DiscordService.getInstance() != null ? DiscordService.getInstance() : new DiscordService();
        CronJobs cronUpdate = new CronJobs();
    }
}
