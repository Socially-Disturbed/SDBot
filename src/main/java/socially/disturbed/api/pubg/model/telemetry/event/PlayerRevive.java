package socially.disturbed.api.pubg.model.telemetry.event;

import socially.disturbed.api.pubg.model.telemetry.Character;

public class PlayerRevive implements Event{
    public Character reviver;
    public Character victim;
    public PlayerRevive() {}
}
