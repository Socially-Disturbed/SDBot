package socially.disturbed.api.pubg.model;

import org.junit.jupiter.api.Test;
import socially.disturbed.api.pubg.model.match.MatchId;
import socially.disturbed.api.pubg.model.player.Player;
import socially.disturbed.api.pubg.model.player.PlayerId;

public class PlayerTest {

    @Test
    public void testPrint() {

        Player player = new Player(
                new PlayerId("12345"),
                "Dy7t"
        );

        player.addMatch(new MatchId("54321"));

        System.out.println(player);
    }
}