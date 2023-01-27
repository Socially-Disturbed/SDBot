package socially.disturbed.api.pubg.model.telemetry.event;

import org.json.JSONObject;

import static socially.disturbed.api.pubg.service.util.TelemetryParser.*;

public class EventFunctionsImpl implements EventFunctions {

    @Override
    public Event LOGPLAYERKILLV2(JSONObject jsonObject) {
        return createPlayerKillV2Event(jsonObject);
    }

    @Override
    public Event LOGPLAYERREVIVE(JSONObject jsonObject) {
        return createReviveEvent(jsonObject);
    }

    @Override
    public Event LOGPLAYERMAKEGROGGY(JSONObject jsonObject) {
        return createKnockEvent(jsonObject);
    }
}
