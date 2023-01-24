package socially.disturbed.command;

public class CommandManager {

    public enum Command {
        getPlayersByName,
        getPlayersById,
        getMatch,
        getLastMatch,
        updateGuestScore,
        updateGuestWin,
        updateSDScore,
        updateSDWin,
        getGuestScoreBoard,
        getSDScoreBoard,
        help
    }

    public static Command getCommandFromString(String commandString) {
        try {
            return Command.valueOf(commandString);

        } catch (IllegalArgumentException ignored) {
            return null;
        }
    }
}
