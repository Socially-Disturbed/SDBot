package socially.disturbed.command;

import socially.disturbed.function.SDFunctions;

import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;

import static socially.disturbed.command.CommandManager.Command.*;

public class CommandToFunctionMapper {

    private final Map<CommandManager.Command, UnaryOperator<CommandDto>> commandToFunctionMap = new HashMap<>();

    public CommandToFunctionMapper(SDFunctions functions) {
                commandToFunctionMap.put(getPlayersByName, functions::getPlayersByName);
                commandToFunctionMap.put(getPlayersById, functions::getPlayersById);
                commandToFunctionMap.put(getMatch, functions::getMatch);
                commandToFunctionMap.put(getLastMatch, functions::getLastMatch);
                commandToFunctionMap.put(updateGuestScore, functions::updateGuestScore);
                commandToFunctionMap.put(updateGuestWin, functions::updateGuestWin);
                commandToFunctionMap.put(updateSDScore, functions::updateSDScore);
                commandToFunctionMap.put(updateSDWin, functions::updateSDWin);
                commandToFunctionMap.put(getGuestScoreBoard, functions::getGuestScoreBoard);
                commandToFunctionMap.put(getSDScoreBoard, functions::getSDScoreBoard);
                commandToFunctionMap.put(help, functions::help);
    }

    public UnaryOperator<CommandDto> mapCommandToFunction(CommandManager.Command command) {
        UnaryOperator<CommandDto> function = commandToFunctionMap.get(command);
        if(function == null) throw new RuntimeException("Command not mapped: " + command);

        return function;
    }
}