package socially.disturbed.api.pubg.service.impl;

import socially.disturbed.api.pubg.ApiClient;
import socially.disturbed.api.pubg.model.match.Match;
import socially.disturbed.api.pubg.model.match.MatchId;
import socially.disturbed.api.pubg.service.MatchService;
import socially.disturbed.api.pubg.service.util.MatchJsonParser;

public class DefaultMatchService implements MatchService {

    private final ApiClient apiClient = ApiClient.getInstance();
    private final MatchJsonParser parser = new MatchJsonParser();

    @Override
    public Match getMatch(MatchId matchId) {
        String request = matchEndpoint + "/" + matchId;
        String matchJson = apiClient.makeRequest(request);

        return parser.parseJsonToObject(matchJson);
    }
}
