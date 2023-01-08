package socially.disturbed.api.pubg.model.player;

import socially.disturbed.api.pubg.model.common.Entity;
import socially.disturbed.api.pubg.model.match.MatchId;

import java.util.ArrayList;
import java.util.List;

public class Player extends Entity {

    private final String playerName;
    private final List<MatchId> lastMatches = new ArrayList<>();

    public Player(PlayerId playerId, String playerName) {
        super(playerId);
        this.playerName = playerName;
    }

    public PlayerId getId() {
        return (PlayerId) super.getId();
    }

    public String getPlayerName() {
        return playerName;
    }

    public List<MatchId> getLastMatches() {
        return lastMatches;
    }

    public void addMatch(MatchId matchId) {
        lastMatches.add(matchId);
    }

    @Override
    public String toString() {
        return "\r\n" +
                "Name: " + playerName + "\r\n" +
                "Id: " + getId().idValue + "\r\n" +
                "Last Match: " + (lastMatches.isEmpty() ? "NA" : lastMatches.get(0).getId())
                + "\r\n";
    }
}
