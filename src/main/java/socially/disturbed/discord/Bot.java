package socially.disturbed.discord;

import discord4j.common.util.Snowflake;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import discord4j.core.object.entity.channel.MessageChannel;
import socially.disturbed.DbService;
import socially.disturbed.command.CommandDto;
import socially.disturbed.command.CommandIntepreter;
import socially.disturbed.command.SDFunctionsImpl;

public class Bot {
    DbService dbService;
    GatewayDiscordClient gateway;

    private final CommandIntepreter commandIntepreter = new CommandIntepreter(new SDFunctionsImpl());

    public Bot(String token, DbService dbService) {
        this.dbService = dbService;

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

    private void handleMessage(Message message) {
        CommandDto commandDto = new CommandDto(message);
        String messageString = message.getContent();
        if (message.getAuthor().get().isBot() && messageString.indexOf("!") != 0) return;
        if (messageString.indexOf("!") == 0) {
            commandDto = commandIntepreter.invokeMethod(commandDto);
            MessageChannel channel;
            if (commandDto.getReturnMsgChannelId() == null) {
                channel = message.getChannel().block();
            }
            else {
                channel = gateway.getChannelById(
                        Snowflake.of(commandDto.getReturnMsgChannelId())).cast(MessageChannel.class).block();
            }
            if (commandDto.deleteLastChannelMsg()) {
                deleteLastChannelMsg(channel);
            }
            if (commandDto.deleteCommandMsg()) {
                message.delete().subscribe();
            }
            channel.createMessage(commandDto.getReturningMsg()).subscribe();
        }
    }

    private void deleteLastChannelMsg(MessageChannel channel) {
        try {
            channel.getLastMessage().block().delete().subscribe();
        } catch (Exception e) {
            System.out.println("Failed to delete last message, moving calmly forward");
        }
    }
}
