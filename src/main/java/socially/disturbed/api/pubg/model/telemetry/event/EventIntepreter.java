package socially.disturbed.api.pubg.model.telemetry.event;

import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EventIntepreter {
    private final EventFunctions functions;

    public EventIntepreter(EventFunctions functions) {
        this.functions = functions;
    }

    public Event invokeMethod(JSONObject jsonObject) {
        try {
            Method method = functions.getClass().getDeclaredMethod(jsonObject.getString("_T"), JSONObject.class);
            return (Event) method.invoke(functions, jsonObject);
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            return null;
        }
    }
}
