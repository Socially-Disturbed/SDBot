package socially.disturbed.api.pubg.model;

import org.junit.jupiter.api.Test;
import socially.disturbed.api.pubg.model.player.Participant;
import socially.disturbed.api.pubg.model.player.ParticipantId;
import socially.disturbed.api.pubg.model.player.Player;
import socially.disturbed.api.pubg.model.player.PlayerId;

public class ParticipantTest {

    @Test
    public void testParticipantToString() {

        Participant participant = new Participant(new ParticipantId("12345"));
        participant.player = new Player(new PlayerId("playerId"), "Dy7t");

        System.out.println(participant);
    }
}