package socially.disturbed.api.pubg.model.player;

import socially.disturbed.api.pubg.model.common.Entity;

import java.math.BigDecimal;

public class Participant extends Entity {

    public Participant(ParticipantId id) {
        super(id);
    }

    public Player player;
    public String createdAt;
    public int kills = 0;
    public int knocks = 0;
    public int assists = 0;
    public BigDecimal damage = new BigDecimal(0);
    public int revives = 0;
    public int teamkills = 0;

    @Override
    public String toString() {
        return "Participant{" +
                "player=" + player.getPlayerName() +
                ", createdAt='" + createdAt + '\'' +
                ", kills=" + kills +
                ", knocks=" + knocks +
                ", assists=" + assists +
                ", damage=" + damage +
                ", revives=" + revives +
                ", teamkills=" + teamkills +
                '}';
    }
}
