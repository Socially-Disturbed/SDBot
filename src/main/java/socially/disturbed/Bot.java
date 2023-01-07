package socially.disturbed;

import discord4j.common.util.Snowflake;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import discord4j.core.object.entity.channel.MessageChannel;

import java.util.Arrays;
import java.util.List;

import static socially.disturbed.DiscordChannelID.SD_HIGHSCORE_TEST;

public class Bot {
    DbService dbService;
    GatewayDiscordClient gateway;

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

        if (message.getContent().split(" ")[0].equalsIgnoreCase("!updateGuest")) {
            updateGuestList(message);
        }
        else if (message.getContent().split(" ")[0].equalsIgnoreCase("!update")) {
            updateSDList(message);
        }
        else if (message.getContent().split(" ")[0].equalsIgnoreCase("!addGuest")) {
            addPlayerToGuestList(message);
        }

//        if (message.getContent().equals("!spillere")) {
//            MessageChannel channel = message.getChannel().block();
//            message.delete().subscribe();
//            sendPlayers(true, SD_HIGHSCORE_TEST);
//        }
    }

    private void addPlayerToGuestList(Message message) {
        System.out.println("Adding player to guestList");
        String[] arrOfMsg = message.getContent().split(" ");
        dbService.addPlayer(true, arrOfMsg[1]);

        clearAndUpdateHighscoreChannel(message, true);
    }

    private void updateGuestList(Message message) {
        System.out.println("Updating guestlist");

        String messageString = message.getContent();
        String[] arrOfMsg = messageString.split(" ");
        System.out.println(Arrays.toString(arrOfMsg));
        if (arrOfMsg.length >= 3) {
            String player = arrOfMsg[1];
            String scoreType = arrOfMsg[2];

            System.out.println("Scoretype " + scoreType);
            if (scoreType.equalsIgnoreCase("win")) {
                dbService.updatePlayerWin(player, true);
            }
            else {
                float scoreValue = Float.parseFloat(arrOfMsg[3]);
                dbService.updatePlayer(player, true, scoreType, scoreValue);
            }

        }
        clearAndUpdateHighscoreChannel(message, true);
    }

    private void updateSDList(Message message) {
        String messageString = message.getContent();
        String[] arrOfMsg = messageString.split(" ");
        System.out.println(Arrays.toString(arrOfMsg));
        if (arrOfMsg.length > 3) {
            String player = arrOfMsg[1];
            String scoreType = arrOfMsg[2];
            if (scoreType.equalsIgnoreCase("win")) {
                dbService.updatePlayerWin(player, true);
            }
            else {
                float scoreValue = Float.parseFloat(arrOfMsg[3]);
                dbService.updatePlayer(player, true, scoreType, scoreValue);
            }

        }
        clearAndUpdateHighscoreChannel(message, false);
    }

    private void sendPlayers(boolean guestList) {
        String channelId = "";
        if (guestList) {
            channelId = DiscordChannelID.GUEST_HIGHSCORE.label;
        } else {
            channelId = DiscordChannelID.SD_HIGHSCORE.label;
        }
        MessageChannel channel = gateway.getChannelById(
                Snowflake.of(channelId))
                .cast(MessageChannel.class).block();
        if (channel == null) {
            System.out.println("Could not find channel by id " + channelId);
            return;
        }

        List<Player> players = dbService.getAllPlayers(guestList);
        StringBuilder message = new StringBuilder();
        for (Player p: players) {
            message.append(p.toString());
        }
        try {
            channel.getLastMessage().block().delete().subscribe();
        } catch (Exception e) {
            System.out.println("Failed to delete last message, moving calmly forward");
        }
        channel.createMessage(message.toString()).subscribe();
    }

    private void clearAndUpdateHighscoreChannel(Message message, boolean guestList) {
        // Delete command msg
        MessageChannel channel = message.getChannel().block();
        channel.getLastMessage().block().delete().subscribe();

        // Send updated highscore
        sendPlayers(guestList);
    }
}
