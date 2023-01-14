package socially.disturbed.api.pubg.service.util;

import org.json.JSONArray;
import org.json.JSONObject;
import socially.disturbed.api.pubg.model.match.MatchId;
import socially.disturbed.api.pubg.model.season.RankTier;
import socially.disturbed.api.pubg.model.season.RankedStats;
import socially.disturbed.api.pubg.model.season.Season;
import socially.disturbed.api.pubg.model.season.SeasonId;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;

public class SeasonJsonParser {

    public Set<Season> parseJsonToSeasonSet(String seasonJson) {
        JSONObject allSeasonsObject = new JSONObject(seasonJson);
        JSONArray seasonArray = allSeasonsObject.getJSONArray("data");

        Set<Season> seasons = new HashSet<>(seasonArray.length());

        for (int i = 0; i < seasonArray.length(); i++) {
            JSONObject seasonObject = seasonArray.getJSONObject(i);
            seasons.add(createSeason(seasonObject));
        }

        return seasons;
    }

    private Season createSeason(JSONObject seasonObject) {
        SeasonId seasonId = new SeasonId(seasonObject.getString("id"));
        boolean isCurrentSeason = seasonObject.getJSONObject("attributes").getBoolean("isCurrentSeason");

        return new Season(seasonId, isCurrentSeason);
    }

    public RankedStats parseJsonToRankedStats(String rankedStatsJson) {
        JSONObject rankedStatsObject = new JSONObject(rankedStatsJson);
        JSONObject data = rankedStatsObject.getJSONObject("data");
        String seasonId = data.getJSONObject("relationships")
                .getJSONObject("season")
                .getJSONObject("data")
                .getString("id");
        JSONObject squadFppStats = data.getJSONObject("attributes")
                .getJSONObject("rankedGameModeStats")
                .getJSONObject("squad-fpp");
        JSONObject currentTier = squadFppStats.getJSONObject("currentTier");

        RankedStats rankedStats = new RankedStats(new SeasonId(seasonId));
        rankedStats.rankTier = new RankTier(currentTier.getString("tier"), currentTier.getString("subTier"));
        rankedStats.damageDealt = squadFppStats.getFloat("damageDealt");
        rankedStats.roundsPlayed = squadFppStats.getInt("roundsPlayed");
        rankedStats.averageDamage = rankedStats.damageDealt / rankedStats.roundsPlayed;

        return rankedStats;
    }
}
