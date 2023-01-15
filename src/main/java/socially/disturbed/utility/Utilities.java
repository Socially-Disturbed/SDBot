package socially.disturbed.utility;

import socially.disturbed.presentation.User;

import java.util.HashSet;
import java.util.Set;

public class Utilities {
    public static final String commands =
            "Updating SDList: \n" +
            "!updateSDWin <player> \n" +
            "!updateSDScore <player> <value> \n" +
            "!delete <player> NOT IMPLEMENTED\n\n" +
            "Updating GuestList: \n" +
            "!updateGuestWin <player> \n" +
            "!updateGuestScore <player> <value> \n" +
            "!deleteGuest <player> NOT IMPLEMENTED\n\n";

    public static String stringSetToStringWithDelim(Set<String> players, String delim) {
        StringBuilder sb = new StringBuilder();

        String loopDelim = "";
        for(String s : players) {
            sb.append(loopDelim);
            sb.append(s);

            loopDelim = delim;
        }

        return sb.toString();
    }

    public static String userSetToHighscoreList(Set<User> users) {
        StringBuilder newHigshcoreList = new StringBuilder();
        for (User p: users) {
            newHigshcoreList.append(p.toString());
        }
        return newHigshcoreList.toString();
    }

    public static Set<String> userSetToStringSetWithUsernames(Set<User> users) {
        Set<String> usernames = new HashSet<>();
        for (User player : users) {
            usernames.add(player.name);
        }

        return usernames;
    }
}
