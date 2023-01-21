package socially.disturbed.command;

import socially.disturbed.command.CommandManager.Command;
import socially.disturbed.function.SDFunctions;

import java.util.function.UnaryOperator;

public class CommandResolver {

    private final CommandToFunctionMapper commandToFunctionMapper;

    public CommandResolver(SDFunctions functions) {
        this.commandToFunctionMapper = new CommandToFunctionMapper(functions);
    }

    public CommandDto invokeMethod(CommandDto commandDto) {

        String methodName = commandDto.getMethodName();
        Command command = CommandManager.getCommandFromString(methodName);
        if (command == null) {
            commandDto.setReturningMsg("Method name does not exist: " + methodName);
        }

        UnaryOperator<CommandDto> function = commandToFunctionMapper.mapCommandToFunction(command);
        return function.apply(commandDto);
    }
}
