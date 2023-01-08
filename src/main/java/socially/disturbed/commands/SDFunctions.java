package socially.disturbed.commands;

/**
 * Can be invoked from Discord using ! and method name.
 * Ex: '!getPlayerByName playerName'
 */
public interface SDFunctions {
    String getPlayersByName(String playerNames);
    String getPlayersById(String playerIds);
    String getMatch(String matchId);
    String getLastMatch(String playerName);
}
