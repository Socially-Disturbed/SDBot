package socially.disturbed.database;

public enum SQL {

    GET_SD_USERS("SELECT * FROM \"SD_SCORE\""),
    GET_GUEST_USERS("SELECT * FROM \"SD_GUEST_SCORE\""),
    GET_SD_USER("SELECT * FROM \"SD_SCORE\""),
    GET_GUEST_USER("SELECT * FROM \"SD_GUEST_SCORE\""),
    INSERT_NEW_GUEST_USER("INSERT INTO \"SD_GUEST_SCORE\"(\"NAME\", \"WIN\")"),
    INSERT_NEW_SD_USER("INSERT INTO \"SD_SCORE\"(\"NAME\")"),
    UPDATE_SD_SCORE("UPDATE \"SD_SCORE\""),
    UPDATE_GUEST_SCORE("UPDATE \"SD_GUEST_SCORE\"");

    public final String label;

    SQL(String label) {
        this.label = label;
    }
}
