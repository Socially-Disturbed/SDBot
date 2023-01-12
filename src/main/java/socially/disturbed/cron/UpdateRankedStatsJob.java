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
import socially.disturbed.presentation.User;

import java.util.HashSet;
import java.util.Set;

import static socially.disturbed.utility.Utilities.stringSetToStringWithDelim;
import static socially.disturbed.utility.Utilities.userSetToHighscoreList;

public class UpdateRankedStatsJob implements Job {

    private final DbService dbService = new DbService();
    private final DiscordService discordService = new DiscordService();
    private final SeasonService seasonService = new DefaultSeasonService();
    private final PlayerService playerService = new DefaultPlayerService();
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Season currentSeason = getCurrentSeason();

        Set<String> guestListPlayers = getPlayerNames(dbService.getAllUsers(true));
        Set<String> sdPlayers = getPlayerNames(dbService.getAllUsers(false));
        Set<String> uniquePlayers = new HashSet<>();
        uniquePlayers.addAll(sdPlayers);
        uniquePlayers.addAll(guestListPlayers);

        Set<Player> players = playerService.getPlayersByName(stringSetToStringWithDelim(uniquePlayers, ","));

        // Get player RankedStats {playerId, currentSeasonId}
        for (Player player : players) {
            player.rankedStats = seasonService.getRankedStats(player.getId().idValue, currentSeason.getId().idValue);
            for (String playerName: guestListPlayers) {
                if (player.getPlayerName().equals(playerName))
                    updateRankedStats(player, true);
            }
            for (String playerName: sdPlayers) {
                if (player.getPlayerName().equals(playerName))
                    updateRankedStats(player, false);
            }
        }

        String newGuestHighscoreList = userSetToHighscoreList(dbService.getAllUsers(true));
        String newSDHighscoreList = userSetToHighscoreList(dbService.getAllUsers(false));
        boolean deleteLastMsg = true;
        discordService.sendMessage(DiscordChannelID.GUEST_HIGHSCORE.label, newGuestHighscoreList, deleteLastMsg);
        discordService.sendMessage(DiscordChannelID.SD_HIGHSCORE.label, newSDHighscoreList, deleteLastMsg);
    }

    private void updateRankedStats(Player player, boolean guest) {
        dbService.updateUserRankedStats(player, guest);
    }

    private Season getCurrentSeason() {
        Set<Season> seasons = seasonService.getSeasons();
        for (Season season : seasons) {
            if (season.isCurrentSeason)
                return season;
        }
        System.out.println("Failed to get current PUBG season");
        return null;
    }

    private Set<String> getPlayerNames(Set<User> players) {
        Set<String> playerNames = new HashSet<>();
        for (User player : players) {
            playerNames.add(player.name);
        }

        return playerNames;
    }
}
