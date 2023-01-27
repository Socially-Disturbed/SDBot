package socially.disturbed.discord.update;

import socially.disturbed.command.CommandDto;

import java.util.Set;

public class Update {
    int validationsNeeded = 3;
    Set<String> usersValidated;
    CommandDto command;
    String userTryingToUpdate;

    public Update(CommandDto command, String userTryingToUpdate) {
        this.command = command;
        this.userTryingToUpdate = userTryingToUpdate;
    }

    boolean validate(String username) {
        if (username.equals(userTryingToUpdate))
            return false;
        // Check if user is active in voice channel
        usersValidated.add(username);
        return usersValidated.size() == validationsNeeded;
    }
}
