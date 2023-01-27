package socially.disturbed;

import org.quartz.SchedulerException;
import socially.disturbed.discord.Bot;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // Shall not be pushed to git more than once
        // To prevent token pushed to git
        try {
            Bot bot = new Bot();
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}