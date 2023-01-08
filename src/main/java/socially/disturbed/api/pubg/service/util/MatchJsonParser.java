package socially.disturbed.api.pubg.service.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import socially.disturbed.api.pubg.model.match.MapNameMapper;
import socially.disturbed.api.pubg.model.match.Match;
import socially.disturbed.api.pubg.model.match.MatchId;
import socially.disturbed.api.pubg.model.player.Participant;
import socially.disturbed.api.pubg.model.player.ParticipantId;
import socially.disturbed.api.pubg.model.player.Player;
import socially.disturbed.api.pubg.model.player.PlayerId;

public class MatchJsonParser {

    private String createdAt = "";

    public Match parseJsonToObject(String jsonString) {
        MatchId matchId;
        Match match;

        JSONObject matchObject = new JSONObject(jsonString);
        JSONObject data = matchObject.getJSONObject("data");

        matchId = new MatchId(data.getString("id"));
        match = new Match(matchId);

        JSONObject attributes = data.getJSONObject("attributes");
        mapAttributes(attributes, match);

        JSONArray included = matchObject.getJSONArray("included");
        for (int i = 0; i < included.length(); i++) {
            JSONObject includedObject = included.getJSONObject(i);

            if (!"participant".equals(includedObject.getString("type"))) continue;

            ParticipantId id = new ParticipantId(includedObject.getString("id"));
            Participant participant = new Participant(id);
            mapParticipant(includedObject.getJSONObject("attributes"), participant);
            match.participants.add(participant);
        }

        this.createdAt = "";
        return match;
    }

    private void mapParticipant(JSONObject attributes, Participant participant) {
        JSONObject stats = attributes.getJSONObject("stats");

        try {

            participant.player = new Player(
                    new PlayerId(stats.getString("playerId")),
                    stats.getString("name")
            );
        } catch (JSONException je) {
            int a = 4;
        }
        participant.createdAt = this.createdAt;
        participant.assists = stats.getInt("assists");
        participant.damage = stats.getBigDecimal("damageDealt");
        participant.knocks = stats.getInt("DBNOs");
        participant.revives = stats.getInt("revives");
        participant.teamkills = stats.getInt("teamKills");
        participant.kills = stats.getInt("kills");
    }

    private void mapAttributes(JSONObject attributes, Match match) {
        match.createdAt = attributes.getString("createdAt");
        this.createdAt = match.createdAt;
        match.matchType = attributes.getString("matchType");
        match.gameMode = attributes.getString("gameMode");
        match.mapName = MapNameMapper.apiToCommonMap.get(attributes.getString("mapName"));
    }
}