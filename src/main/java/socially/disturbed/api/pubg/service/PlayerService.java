package socially.disturbed.api.pubg.service;

import socially.disturbed.api.pubg.model.player.Player;

import java.util.Set;

public interface PlayerService {

    String playerEndpoint = "/players";
    String playersByNameFilter = "?filter[playerNames]=";
    String playersByIdsFilter = "?filter[playerIds]=";

    Set<Player> getPlayersByName(String playerNames);
    Set<Player> getPlayersById(String playerIds);
}
