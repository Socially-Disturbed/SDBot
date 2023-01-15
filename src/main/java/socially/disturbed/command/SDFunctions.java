package socially.disturbed.command;

/**
 * Can be invoked from Discord using ! and method name.
 * Ex: '!getPlayerByName playerName'
 */
public interface SDFunctions {
    CommandDto getPlayersByName(CommandDto commandDto);
    CommandDto getPlayersById(CommandDto commandDto);
    CommandDto getMatch(CommandDto commandDto);
    CommandDto getLastMatch(CommandDto commandDto);
    CommandDto updateGuestScore(CommandDto commandDto);
    CommandDto updateGuestWin(CommandDto commandDto);
    CommandDto updateSDScore(CommandDto commandDto);
    CommandDto updateSDWin(CommandDto commandDto);
    CommandDto getGuestScoreBoard(CommandDto commandDto);
    CommandDto getSDScoreBoard(CommandDto commandDto);
    CommandDto help(CommandDto commandDto);
}
