package socially.disturbed.discord;

import discord4j.common.util.Snowflake;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.entity.channel.MessageChannel;

public class DiscordService {
    GatewayDiscordClient gateway;
    public DiscordService() {
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
