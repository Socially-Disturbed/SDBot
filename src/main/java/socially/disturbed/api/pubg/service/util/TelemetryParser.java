package socially.disturbed.api.pubg.service.util;

import org.json.JSONArray;
import org.json.JSONObject;
import socially.disturbed.api.pubg.model.telemetry.Character;
import socially.disturbed.api.pubg.model.telemetry.Telemetry;
import socially.disturbed.api.pubg.model.telemetry.event.Event;
import socially.disturbed.api.pubg.model.telemetry.event.PlayerKillV2;
import socially.disturbed.api.pubg.model.telemetry.event.PlayerKnock;
import socially.disturbed.api.pubg.model.telemetry.event.PlayerRevive;

import java.util.List;
import java.util.Map;

public class TelemetryParser {

    public static Telemetry parseTelemetryJsonToTelemetry(Map<String, List<Event>> events, String telemetryJson) {
        JSONArray eventArray = new JSONArray(telemetryJson);
        Telemetry telemetryEvents = new Telemetry();

        for (int i = 0; i < eventArray.length(); i++) {
            JSONObject eventObject = eventArray.getJSONObject(i);
            if (eventObject.getString("_T").equals("LOGPLAYERREVIVE")) {

            }
        }

        return telemetryEvents;
    }

    public static PlayerKillV2 createPlayerKillV2Event(JSONObject json) {
        PlayerKillV2 event = new PlayerKillV2();
        event.attackId = json.getInt("attackId");
        event.dBNOId = json.getInt("dBNOId");
        event.dBNOMaker = createCharacter(json.getJSONObject("dBNOMaker"));
        event.victim = createCharacter(json.getJSONObject("victim"));
        event.finisher = createCharacter(json.getJSONObject("finisher"));
        event.killer = createCharacter(json.getJSONObject("killer"));
        event.isSuicide = json.getBoolean("isSuicide");
        return event;
    }

    public static PlayerRevive createReviveEvent(JSONObject json) {
        PlayerRevive event = new PlayerRevive();
        return event;
    }

    public static PlayerKnock createKnockEvent(JSONObject json) {
        PlayerKnock event = new PlayerKnock();
        event.attacker = createCharacter(json.getJSONObject("attacker"));
        event.victim = createCharacter(json.getJSONObject("victim"));
        return event;
    }

    public static Character createCharacter(JSONObject charObject) {
        return new Character(charObject.getString("name"), charObject.getInt("teamId"));
    }
}
