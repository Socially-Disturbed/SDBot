package socially.disturbed.api.pubg.model.telemetry.event;

import socially.disturbed.api.pubg.model.telemetry.Character;

// LOGPLAYERMAKEGROGGY
public class PlayerKnock implements Event{
    public Character attacker;
    public Character victim;

    public PlayerKnock() {}
}
