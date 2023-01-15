package socially.disturbed.presentation;

public class User {
    public String name;
    public int wins;
    public float maxPoints;
    public float adr;
    public String rank;

    public User() {

    }

    public User(String name, int wins, float maxPoints, float adr, String rank) {
        this.name = name;
        this.wins = wins;
        this.maxPoints = maxPoints;
        this.adr = adr;
        this.rank = rank;
    }

    public String toString() {
        return "\n-------------------------\n"
                + this.whiteSpaces(this.name) + ": " + this.maxPoints + "\n"
                + this.whiteSpaces("Wins") + ": " + this.wins + "\n"
                + this.whiteSpaces("ADR") + ": " + this.adr + "\n"
                + "-------------------------";
    }

    private String whiteSpaces(String text) {
        return " ".repeat(Math.max(0, (10 - text.length()))) +
                text;
    }
}
