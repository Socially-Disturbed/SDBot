package socially.disturbed.discord;

import java.util.ArrayList;
import java.util.List;

public enum SDPlayer {
    VEBIS("Vebis"),
    TURTLEB34R("TurtleB34r"),
    MEZZYFEZ("Mezzyfez"),
    DY7T("Dy7t");

    public final String label;

    SDPlayer(String label) {
        this.label = label;
    }

    public List<String> getAllSDPlayers() {
        List<String> players = new ArrayList<>(4);
        for (SDPlayer player : SDPlayer.values()) {
            players.add(player.label);
        }
        return players;
    }

    public boolean isSDPlayer(String username) {
        for (SDPlayer s : SDPlayer.values()) {
            if (s.name().equals(username)) {
                return true;
            }
        }
        return false;
    }
}
