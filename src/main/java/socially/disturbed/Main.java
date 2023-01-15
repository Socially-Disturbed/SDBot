package socially.disturbed;

import socially.disturbed.discord.Bot;

public class Main {
    public static void main(String[] args) {
        // Shall not be pushed to git more than once
        // To prevent token pushed to git
        DbService dbs = new DbService();
        Bot bot = new Bot(dbs);
    }
}