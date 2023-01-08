package socially.disturbed.commands;

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

    public String invokeMethod(String commandString) {
        String methodName = parseCommand(commandString);
        if (GET_ALL_METHODS.equals(methodName)) return listOfMethods();

        String arguments = commandString.substring(commandString.indexOf(" ") + 1);

        try {
            Method method = functions.getClass().getDeclaredMethod(methodName, String.class);
            return (String) method.invoke(functions, arguments);
        } catch (NoSuchMethodException e) {
            return "Method not found: " + methodName;
        } catch (InvocationTargetException | IllegalAccessException e) {
            return methodName + " <- can't be invoked with arguments: " + arguments +
                    ", " + e.getMessage();
        }
    }

    private String parseCommand(String commandString) {
        return commandString.contains(" ") ?
                commandString.substring(1, commandString.indexOf(" ")) :
                commandString.substring(1);
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
