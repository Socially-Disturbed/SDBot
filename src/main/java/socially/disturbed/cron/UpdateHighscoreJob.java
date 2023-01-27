package socially.disturbed.cron;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import socially.disturbed.api.pubg.model.player.Player;
import socially.disturbed.api.pubg.model.season.Season;
import socially.disturbed.api.pubg.service.PlayerService;
import socially.disturbed.api.pubg.service.SeasonService;
import socially.disturbed.api.pubg.service.impl.DefaultPlayerService;
import socially.disturbed.api.pubg.service.impl.DefaultSeasonService;
import socially.disturbed.database.DbService;
import socially.disturbed.discord.DiscordID;
import socially.disturbed.discord.GatewayDiscordClientWrapper;

import java.util.HashSet;
import java.util.Set;

import static socially.disturbed.utility.Utilities.*;

public class UpdateHighscoreJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        // Check if we update guestlist og SDList based on players from discord

        // Get highscore from active players

        // If highscore is new on active player(s), update guest or SD list
        GatewayDiscordClientWrapper gatewayDiscordClientWrapper = new GatewayDiscordClientWrapper();
        String newGuestHighscoreList = userSetToHighscoreList(dbService.getAllUsers(true));
        String newSDHighscoreList = userSetToHighscoreList(dbService.getAllUsers(false));
        System.out.println(newGuestHighscoreList);
        System.out.println(newSDHighscoreList);
        boolean deleteLastMsg = true;
        gatewayDiscordClientWrapper.sendMessage(DiscordID.GUEST_HIGHSCORE_CHANNEL.label, newGuestHighscoreList, deleteLastMsg);
        gatewayDiscordClientWrapper.sendMessage(DiscordID.SD_HIGHSCORE_CHANNEL.label, newSDHighscoreList, deleteLastMsg);
    }
}
