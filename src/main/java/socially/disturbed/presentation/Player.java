package socially.disturbed.presentation;

public class Player {
    private String name;
    private int wins;
    private float maxPoints;
    private int adr;

    public Player() {

    }
    public Player(String name, int wins, float maxPoints, int adr) {
        this.name = name;
        this.wins = wins;
        this.maxPoints = maxPoints;
        this.adr = adr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public float getMaxPoints() {
        return maxPoints;
    }

    public void setMaxPoints(float maxPoints) {
        this.maxPoints = maxPoints;
    }

    public int getAdr() {
        return adr;
    }

    public void setAdr(int adr) {
        this.adr = adr;
    }

    public String toString() {
        return "\n-------------------------\n"
                + this.name + ": " + this.maxPoints + "\n"
                + "Wins: " + this.wins + "\n"
                + "ADR: " + this.adr + "\n"
                + "-------------------------";
    }
}
