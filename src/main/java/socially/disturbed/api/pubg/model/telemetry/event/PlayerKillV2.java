package socially.disturbed.api.pubg.model.telemetry.event;

import socially.disturbed.api.pubg.model.telemetry.Character;

public class PlayerKillV2 implements Event{
    public int attackId;
    public int dBNOId;
    public Character victim;
    public Character dBNOMaker;
    public Character finisher;
    public Character killer;
    public boolean isSuicide;

    public PlayerKillV2() {}
}
