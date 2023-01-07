package sdbot;

public class Bot {

    public static void main(String[] args) {
        GatewayDiscordClient client = DiscordClientBuilder.create("MTA1OTUyNTMyMjE5NTg3Mzk1NA.Gk7i7M.l8NLHYbs0JuwEBNmxwezqIz-HdHl2nlew4SXUI").build().login().block();
    
        client.getEventDispatcher().on(ReadyEvent.class)
            .subscribe(event -> {
              User self = event.getSelf();
              System.out.println(String.format("Logged in as %s#%s", self.getUsername(), self.getDiscriminator()));
            });
    
        client.getEventDispatcher().on(MessageCreateEvent.class)
            .map(MessageCreateEvent::getMessage)
            .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
            .filter(message -> message.getContent().equalsIgnoreCase("!ping"))
            .flatMap(Message::getChannel)
            .flatMap(channel -> channel.createMessage("Pong!"))
            .subscribe();
    
        client.onDisconnect().block();
      }
}