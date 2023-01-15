package socially.disturbed.api.pubg.model.season;

public class RankTier {
    public String tier;
    public String subTier;

    public RankTier(String tier, String subTier) {
        this.tier = tier;
        this.subTier = subTier;
    }

    public String getRank() {
        return tier + " " + subTier;
    }
}
