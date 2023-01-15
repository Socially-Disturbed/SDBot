package socially.disturbed.api.pubg.service.impl;


import socially.disturbed.api.pubg.ApiClient;
import socially.disturbed.api.pubg.model.season.RankedStats;
import socially.disturbed.api.pubg.model.season.Season;
import socially.disturbed.api.pubg.service.SeasonService;
import socially.disturbed.api.pubg.service.util.SeasonJsonParser;

import java.util.Set;

public class DefaultSeasonService implements SeasonService {

    private final ApiClient apiClient = ApiClient.getInstance();
    private final SeasonJsonParser parser = new SeasonJsonParser();

    @Override
    public Set<Season> getSeasons() {
        String seasonsJsonResponse = apiClient.makeRequest(seasonsEndpoint);

        return parser.parseJsonToSeasonSet(seasonsJsonResponse);
    }

    @Override
    public Season getCurrentSeason() {
        Set<Season> seasons = getSeasons();
        for (Season season : seasons) {
            if (season.isCurrentSeason)
                return season;
        }
        System.out.println("Failed to get current PUBG season");
        return null;
    }

    @Override
    public RankedStats getRankedStats(String accountId, String seasonId) {
        String request = String.format(rankedStatsEndpoint, accountId, seasonId);
        String rankedStatsJson = apiClient.makeRequest(request);

        return parser.parseJsonToRankedStats(rankedStatsJson);
    }
}
