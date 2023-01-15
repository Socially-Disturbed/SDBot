package socially.disturbed.api.pubg.service.impl;

import socially.disturbed.api.pubg.ApiClient;
import socially.disturbed.api.pubg.model.player.Player;
import socially.disturbed.api.pubg.service.PlayerService;
import socially.disturbed.api.pubg.service.util.PlayerJsonParser;

import java.util.Set;

public class DefaultPlayerService implements PlayerService {

    private final ApiClient apiClient = ApiClient.getInstance();
    private final PlayerJsonParser parser = new PlayerJsonParser();

    @Override
    public Set<Player> getPlayersByName(String playerNames) {

        String request = playerEndpoint + playersByNameFilter + playerNames;
        String playerJsonResponse = apiClient.makeRequest(request);

        return parser.parseJsonToPlayerSet(playerJsonResponse);
    }

    @Override
    public Set<Player> getPlayersById(String playerIds) {

        String request = playerEndpoint + playersByNameFilter + "Dy7t";



        return null;
    }

}
