package socially.disturbed.api.pubg.service.util;

import org.json.JSONArray;
import org.json.JSONObject;
import socially.disturbed.api.pubg.model.match.MatchId;
import socially.disturbed.api.pubg.model.player.Player;
import socially.disturbed.api.pubg.model.player.PlayerId;

import java.util.HashSet;
import java.util.Set;

public class PlayerJsonParser {

    public Set<Player> parseJsonToPlayerSet(String playersJson) {
        JSONObject allPlayersObject = new JSONObject(playersJson);
        JSONArray playersArray = allPlayersObject.getJSONArray("data");

        Set<Player> players = new HashSet<>(playersArray.length());

        for (int i = 0; i < playersArray.length(); i++) {

            JSONObject playerObject = playersArray.getJSONObject(i);
            players.add(createPlayer(playerObject));
        }

        return players;
    }

    private Player createPlayer(JSONObject playerObject) {
        PlayerId playerId;
        Player player;

        playerId = new PlayerId(playerObject.getString("id"));

        JSONObject attributes = playerObject.getJSONObject("attributes");
        String playerName = attributes.getString("name");

        player = new Player(playerId, playerName);

        JSONObject relationships = playerObject.getJSONObject("relationships");
        JSONArray matchArray = relationships.getJSONObject("matches").getJSONArray("data");

        for(int i = 0; i < matchArray.length(); i++) {
            String matchId = matchArray.getJSONObject(i).getString("id");
            player.addMatch(new MatchId(matchId));
        }

        return player;
    }
}
