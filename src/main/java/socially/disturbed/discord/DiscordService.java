package socially.disturbed.discord;

import discord4j.common.util.Snowflake;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import discord4j.core.object.entity.channel.MessageChannel;
import socially.disturbed.command.CommandDto;
import socially.disturbed.command.CommandIntepreter;
import socially.disturbed.command.SDFunctionsImpl;

public class DiscordService {

    private static DiscordService discService;
    private final CommandIntepreter commandIntepreter = new CommandIntepreter(new SDFunctionsImpl());
    GatewayDiscordClient gateway;

    public static DiscordService getInstance() {
        if (discService == null) {
            discService = new DiscordService();
        }
        System.out.println("Getting discService");
        return discService;
    }

    private DiscordService() {
        System.out.println("Discord constructor");
        String token = System.getenv("disctoken");
        DiscordClient client = DiscordClient.create(token);
        gateway = client.login().block();
        System.out.println("login block");

        gateway.getEventDispatcher().on(ReadyEvent.class)
                .subscribe(event -> {
                    User self = event.getSelf();
                    System.out.printf("Logged in as %s#%s%n", self.getUsername(), self.getDiscriminator());
                });

        gateway
                .on(MessageCreateEvent.class)
                .map(MessageCreateEvent::getMessage)
                .subscribe(this::handleMessage);

        System.out.println("Discord blocking");
        gateway.onDisconnect().block();
        System.out.println("Discord created");
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

    public void sendMessage(MessageChannel channel, String message, boolean deleteLastMsg) {
        if (deleteLastMsg)
            deleteLastChannelMsg(channel);
        channel.createMessage(message).subscribe();
    }

    public void sendMessage(String channelId, String message, boolean deleteLastMsg) {
        MessageChannel channel = gateway.getChannelById(
                Snowflake.of(channelId)).cast(MessageChannel.class).block();
        if (deleteLastMsg)
            deleteLastChannelMsg(channel);
        channel.createMessage(message).subscribe();
    }

    public void deleteLastChannelMsg(MessageChannel channel) {
        try {
            channel.getLastMessage().block().delete().subscribe();
        } catch (Exception e) {
            System.out.println("Failed to delete last message, moving calmly forward");
        }
    }
}
