package socially.disturbed.api.pubg.model.match;

import socially.disturbed.api.pubg.model.common.Entity;
import socially.disturbed.api.pubg.model.player.Participant;

import java.util.HashSet;
import java.util.Set;

public class Match extends Entity {
    public String createdAt;
    public String gameMode;
    public String matchType;
    public String mapName;
    public final Set<Participant> participants = new HashSet<>();

    public Match(MatchId id) {
        super(id);
    }
}
