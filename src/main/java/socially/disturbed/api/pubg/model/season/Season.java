package socially.disturbed.api.pubg.model.season;

import socially.disturbed.api.pubg.model.common.Entity;
import socially.disturbed.api.pubg.model.common.Id;

public class Season extends Entity {

    public final boolean isCurrentSeason;

    public Season(Id id, boolean isCurrentSeason) {
        super(id);
        this.isCurrentSeason = isCurrentSeason;
    }

    public SeasonId getId() {
        return (SeasonId) super.getId();
    }
}
