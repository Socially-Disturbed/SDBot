package socially.disturbed.api.pubg.model.season;

import socially.disturbed.api.pubg.model.common.Entity;
import socially.disturbed.api.pubg.model.common.Id;

public class RankedStats extends Entity {

    public int roundsPlayed;
    public float damageDealt;
    public float averageDamage;
    public RankTier rankTier;

    public RankedStats(Id seasonId) {
        super(seasonId);
    }
}
