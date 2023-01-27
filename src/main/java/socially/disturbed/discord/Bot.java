package socially.disturbed.discord;

import org.quartz.*;
import socially.disturbed.api.pubg.service.util.TelemetryParser;
import socially.disturbed.cron.CronJobs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Bot {
    public Bot() throws SchedulerException, IOException {
        CronJobs cronUpdate = new CronJobs();
        String json = Files.readString(Path.of("src/test/resources/pubg-api/responseExample/telemetry.json"));
//        TelemetryParser.parseTelemetryJsonToTelemetry(json);
        GatewayDiscordClientWrapper gatewayDiscordClientWrapper = new GatewayDiscordClientWrapper();
        gatewayDiscordClientWrapper.init();
    }
}
