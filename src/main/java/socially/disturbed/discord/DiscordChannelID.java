package socially.disturbed.discord;

public enum DiscordChannelID {
    SD_HIGHSCORE("1059552489621426188"),
    GUEST_HIGHSCORE("1060195081463799808"),
    SD_HIGHSCORE_TEST("1060194029750456370");

    public final String label;

    DiscordChannelID(String label) {
        this.label = label;
    }
}
