package socially.disturbed.api.pubg.model.telemetry;

import socially.disturbed.api.pubg.model.telemetry.event.Event;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Telemetry {
    Map<Character, List<Event>> eventMap = new HashMap<>();
    public Telemetry() {

    }

    public void addEvent(Character character, Event event) {
        List<Event> charEvents = eventMap.get(character);
        charEvents.add(event);
        eventMap.put(character, charEvents);
    }
}
