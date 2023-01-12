package socially.disturbed.discord;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import discord4j.core.object.entity.channel.MessageChannel;
import org.quartz.*;
import socially.disturbed.command.CommandDto;
import socially.disturbed.command.CommandIntepreter;
import socially.disturbed.command.SDFunctionsImpl;
import socially.disturbed.cron.CronJobs;

public class Bot {
    GatewayDiscordClient gateway;
    private final CommandIntepreter commandIntepreter = new CommandIntepreter(new SDFunctionsImpl());
    public Bot() throws SchedulerException {
        CronJobs cronUpdate = new CronJobs();

        String token = System.getenv("disctoken");
        DiscordClient client = DiscordClient.create(token);
        gateway = client.login().block();

        gateway.getEventDispatcher().on(ReadyEvent.class)
                .subscribe(event -> {
                    User self = event.getSelf();
                    System.out.printf("Logged in as %s#%s%n", self.getUsername(), self.getDiscriminator());
                });

        gateway
                .on(MessageCreateEvent.class)
                .map(MessageCreateEvent::getMessage)
                .subscribe(this::handleMessage);

        gateway.onDisconnect().block();
    }

    protected void handleMessage(Message message) {
        String messageString = message.getContent();
        if (message.getAuthor().get().isBot() && messageString.indexOf("!") != 0) return;
        if (messageString.indexOf("!") == 0) {
            CommandDto commandDto = new CommandDto(message);
            commandDto = commandIntepreter.invokeMethod(commandDto);
            if (commandDto.deleteCommandMsg()) {
                message.delete().subscribe();
            }
            if (commandDto.getReturnMsgChannelId() == null) {
                MessageChannel channel = message.getChannel().block();
                sendMessage(channel, commandDto.getReturningMsg(), commandDto.deleteLastChannelMsg());
            }
            else {
                String channelId = commandDto.getReturnMsgChannelId();
                sendMessage(channelId, commandDto.getReturningMsg(), commandDto.deleteLastChannelMsg());
            }
        }
    }
}
