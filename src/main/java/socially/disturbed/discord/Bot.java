package socially.disturbed.discord;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import discord4j.core.object.entity.channel.MessageChannel;
import socially.disturbed.DbService;
import socially.disturbed.commands.CommandIntepreter;
import socially.disturbed.commands.SDFunctionsImpl;

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

        if (message.getAuthor().get().isBot()) return;
        String messageString = message.getContent();
        if (messageString.indexOf("!") == 0) {
            String result = commandIntepreter.invokeMethod(messageString);
            MessageChannel channel = message.getChannel().block();
            channel.createMessage(result).subscribe();
        }
    }
}
