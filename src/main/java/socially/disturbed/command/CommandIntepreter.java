package socially.disturbed.command;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class CommandIntepreter {

    private static final String GET_ALL_METHODS = "getAllMethods";

    private final SDFunctions functions;

    public CommandIntepreter(SDFunctions functions) {
        this.functions = functions;
    }

    public CommandDto invokeMethod(CommandDto commandDto) {
        if (GET_ALL_METHODS.equals(commandDto.getMethodName())) {
            commandDto.setReturningMsg(listOfMethods());
            return commandDto;
        }

        try {
            Method method = functions.getClass().getDeclaredMethod(commandDto.getMethodName(), CommandDto.class);
            return (CommandDto) method.invoke(functions, commandDto);
        } catch (NoSuchMethodException e) {
            commandDto.setReturningMsg("Method not found: " + commandDto.getMethodName());
        } catch (InvocationTargetException | IllegalAccessException e) {
            commandDto.setReturningMsg(commandDto.getMethodName() + " <- can't be invoked with commandArguments: "
                    + commandDto.getCommandArguments() + ", " + e.getMessage());
        }
        return commandDto;
    }

    private String listOfMethods() {
        List<String> allMethods = Arrays.stream(functions.getClass().getDeclaredMethods())
                .map(Method::getName)
                .toList();
        StringBuilder builder = new StringBuilder();
        allMethods.forEach(method -> builder.append("!").append(method).append("\r\n"));
        return builder.toString();
    }
}
