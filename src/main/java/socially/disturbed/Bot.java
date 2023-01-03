package socially.disturbed;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import discord4j.core.object.entity.channel.MessageChannel;

public class Bot {

    public Bot(String token) {

        DiscordClient client = DiscordClient.create(token);
        GatewayDiscordClient gateway = client.login().block();

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
        if (message.getContent().equalsIgnoreCase("!ping")) {

            System.out.println(message.getContent());

            MessageChannel channel = message.getChannel().block();
            channel.createMessage("ping pong mfkr").subscribe();
        }
    }
}
