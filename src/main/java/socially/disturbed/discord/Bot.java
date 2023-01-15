package socially.disturbed.discord;

import discord4j.core.GatewayDiscordClient;
import socially.disturbed.DbService;

public class Bot {
    private final DbService dbService;
    private final GatewayDiscordClient gateway;

    public Bot(DbService dbService) {
        this.dbService = dbService;

        GatewayDiscordClientWrapper gatewayDiscordClientWrapper = new GatewayDiscordClientWrapper();
        gateway = gatewayDiscordClientWrapper.getGateway();
    }
}
