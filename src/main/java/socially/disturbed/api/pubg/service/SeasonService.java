package socially.disturbed.api.pubg.service;

import socially.disturbed.api.pubg.model.season.RankedStats;
import socially.disturbed.api.pubg.model.season.Season;

import java.util.Set;

public interface SeasonService {

    String seasonsEndpoint = "/seasons";
    String rankedStatsEndpoint = "/players/%s/seasons/%s/ranked";

    Set<Season> getSeasons();
    RankedStats getRankedStats(String accoundId, String seasonId);
}
