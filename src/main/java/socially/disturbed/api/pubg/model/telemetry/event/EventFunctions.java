package socially.disturbed.api.pubg.model.telemetry.event;

import org.json.JSONObject;

public interface EventFunctions {
    Event LOGPLAYERKILLV2(JSONObject jsonObject);
    Event LOGPLAYERREVIVE(JSONObject jsonObject);
    Event LOGPLAYERMAKEGROGGY(JSONObject jsonObject);
}
