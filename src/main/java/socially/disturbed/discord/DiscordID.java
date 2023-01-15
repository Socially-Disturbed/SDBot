package socially.disturbed.discord;

public enum DiscordID {
    SD_HIGHSCORE_CHANNEL("1059552489621426188"),
    GUEST_HIGHSCORE_CHANNEL("1060195081463799808"),
    SD_HIGHSCORE_TEST("1060194029750456370");

    public final String label;

    DiscordID(String label) {
        this.label = label;
    }
}
