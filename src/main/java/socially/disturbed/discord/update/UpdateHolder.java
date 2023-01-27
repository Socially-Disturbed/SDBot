package socially.disturbed.discord.update;

import java.util.HashMap;
import java.util.Map;

public class UpdateHolder {
    Map<String, Update> updates = new HashMap<>();
    public UpdateHolder() {}

    public Update getUpdate(String updateId) {
        return updates.get(updateId);
    }

    public void addUpdate(String updateId, Update update) {
        updates.put(updateId, update);
    }

}
