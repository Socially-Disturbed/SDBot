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
                + this.name + ": " + this.maxPoints + "\n"
                + "Wins: " + this.wins + "\n"
                + "ADR: " + this.adr + "\n"
                + "Rank: " + this.rank + "\n"
                + "-------------------------";
    }
}
