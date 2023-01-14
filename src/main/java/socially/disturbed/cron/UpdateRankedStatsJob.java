package socially.disturbed.cron;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import socially.disturbed.database.DbService;
import socially.disturbed.api.pubg.model.player.Player;
import socially.disturbed.api.pubg.model.season.Season;
import socially.disturbed.api.pubg.service.PlayerService;
import socially.disturbed.api.pubg.service.SeasonService;
import socially.disturbed.api.pubg.service.impl.DefaultPlayerService;
import socially.disturbed.api.pubg.service.impl.DefaultSeasonService;
import socially.disturbed.discord.DiscordChannelID;
import socially.disturbed.discord.DiscordService;

import java.util.HashSet;
import java.util.Set;

import static socially.disturbed.utility.Utilities.*;

public class UpdateRankedStatsJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("Executing UpdateRankedStatsJob!");
        DbService dbService = new DbService();
        SeasonService seasonService = new DefaultSeasonService();
        Season currentSeason = seasonService.getCurrentSeason();

        Set<String> guestListPlayers = userSetToStringSetWithUsernames(dbService.getAllUsers(true));
        Set<String> sdPlayers = userSetToStringSetWithUsernames(dbService.getAllUsers(false));
        Set<String> uniquePlayers = new HashSet<>();
        uniquePlayers.addAll(sdPlayers);
        uniquePlayers.addAll(guestListPlayers);
        System.out.println("GuestListPlayers: " + guestListPlayers.size());
        System.out.println("SDListPlayers: " + sdPlayers.size());

        PlayerService playerService = new DefaultPlayerService();
        Set<Player> players = playerService.getPlayersByName(stringSetToStringWithDelim(uniquePlayers, ","));

        for (Player player : players) {
            player.rankedStats = seasonService.getRankedStats(player.getId().idValue, currentSeason.getId().idValue);
            for (String playerName : guestListPlayers) {
                if (player.getPlayerName().equals(playerName))
                    dbService.updateUserRankedStats(player, true);
            }
            for (String playerName : sdPlayers) {
                if (player.getPlayerName().equals(playerName))
                    dbService.updateUserRankedStats(player, false);
            }
        }

        System.out.println("Sending msg to discord");
        DiscordService discordService = DiscordService.getInstance();
        System.out.println("Got discService: " + discordService);
        String newGuestHighscoreList = userSetToHighscoreList(dbService.getAllUsers(true));
        String newSDHighscoreList = userSetToHighscoreList(dbService.getAllUsers(false));
        System.out.println("Guest highscorelist:");
        System.out.println(newGuestHighscoreList);
        System.out.println("SD highscorelist:");
        System.out.println(newSDHighscoreList);
        boolean deleteLastMsg = true;
        discordService.sendMessage(DiscordChannelID.GUEST_HIGHSCORE.label, newGuestHighscoreList, deleteLastMsg);
        discordService.sendMessage(DiscordChannelID.SD_HIGHSCORE.label, newSDHighscoreList, deleteLastMsg);
        System.out.println("Msg sent to discord");
    }
}
