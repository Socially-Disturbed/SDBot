package socially.disturbed.commands;

import socially.disturbed.api.pubg.model.match.Match;
import socially.disturbed.api.pubg.model.match.MatchId;
import socially.disturbed.api.pubg.model.player.Participant;
import socially.disturbed.api.pubg.model.player.Player;
import socially.disturbed.api.pubg.service.MatchService;
import socially.disturbed.api.pubg.service.PlayerService;
import socially.disturbed.api.pubg.service.impl.DefaultMatchService;
import socially.disturbed.api.pubg.service.impl.DefaultPlayerService;

import java.util.Set;

public class SDFunctionsImpl implements SDFunctions {

    private final PlayerService playerService = new DefaultPlayerService();
    private final MatchService matchService = new DefaultMatchService();

    @Override
    public String getPlayersByName(String playerNames) {
        Set<Player> playersByName = playerService.getPlayersByName(playerNames);
        if (playersByName.isEmpty()) return "No players with name: " + playerNames;

        StringBuilder builder = new StringBuilder();
        playersByName.forEach(builder::append);

        return builder.toString();
    }

    @Override
    public String getPlayersById(String playerIds) {
        if (playerIds.equals("12345")) return "Dy7t";
        else return "No player with id: " + playerIds;
    }

    @Override
    public String getMatch(String matchId) {
        if (matchId.equals("9999")) return "Match #!FA!#FA";
        else return "No match with id: " + matchId;
    }

    @Override
    public String getLastMatch(String playerName) {
        Set<Player> playersByName = playerService.getPlayersByName(playerName);
        if (playersByName.isEmpty()) return "No players with name: " + playerName;

        Player player = playersByName.iterator().next();

        MatchId matchId = player.getLastMatches().get(0);
        Match match = matchService.getMatch(matchId);
        Participant matchForPlayer = match.participants.stream()
                .filter(participant -> player.getPlayerName().equals(participant.player.getPlayerName()))
                .toList()
                .get(0);

        return matchForPlayer.toString();
    }
}
