package socially.disturbed;

public class Main {
    public static void main(String[] args) {
        String token = System.getenv("discordToken");
        DbService dbs = new DbService();
        Bot bot = new Bot(token, dbs);
    }
}