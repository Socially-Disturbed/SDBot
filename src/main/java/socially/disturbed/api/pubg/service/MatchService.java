package socially.disturbed.api.pubg.service;

import socially.disturbed.api.pubg.model.match.Match;
import socially.disturbed.api.pubg.model.match.MatchId;

public interface MatchService {

    String matchEndpoint = "/matches";

    Match getMatch(MatchId matchId);
}
