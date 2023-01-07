package socially.disturbed;

public class Main {
    public static void main(String[] args) {
        // Shall not be pushed to git more than once
        // To prevent token pushed to git
        String token = "MTA1OTUyNTMyMjE5NTg3Mzk1NA.Gk7i7M.l8NLHYbs0JuwEBNmxwezqIz-HdHl2nlew4SXUI";
        DbService dbs = new DbService();
        Bot bot = new Bot(token, dbs);
    }
}