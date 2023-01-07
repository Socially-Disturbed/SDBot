package socially.disturbed;

public class Main {
    public static void main(String[] args) {
        // Shall not be pushed to git more than once
        // To prevent token pushed to git
        String token = System.getenv("discordToken");
        DbService dbs = new DbService();
        Bot bot = new Bot(token, dbs);
    }
}