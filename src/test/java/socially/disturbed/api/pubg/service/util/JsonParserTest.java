package socially.disturbed.api.pubg.service.util;

import org.junit.jupiter.api.Test;
import socially.disturbed.api.pubg.model.match.MapNameMapper;
import socially.disturbed.api.pubg.model.match.Match;
import socially.disturbed.api.pubg.model.player.Player;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Objects;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class JsonParserTest {

    @Test
    public void parsePlayerJson() throws IOException {
        PlayerJsonParser parser = new PlayerJsonParser();

        File playerJson = getPlayerJson();
        Set<Player> players = parser.parseJsonToPlayerSet(Files.readString(playerJson.toPath()));

        assertEquals(players.size(), 1);
        assertEquals(players.iterator().next().getPlayerName(), "Dy7t");
    }

    @Test
    public void parseMatchJson() throws IOException {
        MatchJsonParser parser = new MatchJsonParser();

        File matchJson = getMatchJson();
        Match match = parser.parseJsonToObject(Files.readString(matchJson.toPath()));

        assertEquals(match.mapName, MapNameMapper.apiToCommonMap.get("Baltic_Main"));
        boolean exists = match.participants.stream()
                .anyMatch(participant -> participant.player.getPlayerName().equals("Dy7t"));
        assertTrue(exists);
    }

    private File getPlayerJson() {
        URL playerJsonURL = this.getClass().getClassLoader().getResource("pubg-api/responseExample/player.json");
        return getFile(playerJsonURL);
    }

    private File getMatchJson() {
        URL matchJsonURL = this.getClass().getClassLoader().getResource("pubg-api/responseExample/match.json");
        return getFile(matchJsonURL);
    }

    private File getFile(URL url) {
        try {
            return new File(Objects.requireNonNull(url).toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
