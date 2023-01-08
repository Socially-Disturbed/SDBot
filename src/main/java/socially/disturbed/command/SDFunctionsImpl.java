package socially.disturbed.command;

import discord4j.core.object.entity.channel.MessageChannel;
import socially.disturbed.DbService;
import socially.disturbed.api.pubg.model.match.Match;
import socially.disturbed.api.pubg.model.match.MatchId;
import socially.disturbed.api.pubg.model.player.Participant;
import socially.disturbed.api.pubg.model.player.Player;
import socially.disturbed.api.pubg.service.MatchService;
import socially.disturbed.api.pubg.service.PlayerService;
import socially.disturbed.api.pubg.service.impl.DefaultMatchService;
import socially.disturbed.api.pubg.service.impl.DefaultPlayerService;
import socially.disturbed.discord.DiscordChannelID;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class SDFunctionsImpl implements SDFunctions {

    private final PlayerService playerService = new DefaultPlayerService();
    private final MatchService matchService = new DefaultMatchService();
    private final DbService dbService = new DbService();

    @Override
    public CommandDto getPlayersByName(CommandDto commandDto) {
        String playerName = commandDto.getCommandArguments();
        Set<Player> playersByName = playerService.getPlayersByName(playerName);
        if (playersByName.isEmpty()) commandDto.setReturningMsg("No players with name: " + playerName);

        StringBuilder builder = new StringBuilder();
        playersByName.forEach(builder::append);
        commandDto.setReturningMsg(builder.toString());

        return commandDto;
    }

    @Override
    public CommandDto getPlayersById(CommandDto commandDto) {
        String playerIds = commandDto.getCommandArguments();
        if (playerIds.equals("12345")) commandDto.setReturningMsg("Dy7t");
        else commandDto.setReturningMsg("No player with id: " + playerIds);
        return commandDto;
    }

    @Override
    public CommandDto getMatch(CommandDto commandDto) {
        String matchId = commandDto.getCommandArguments();
        if (matchId.equals("9999")) commandDto.setReturningMsg("Match #!FA!#FA");
        else commandDto.setReturningMsg("No match with id: " + matchId);
        return commandDto;
    }

    @Override
    public CommandDto getLastMatch(CommandDto commandDto) {
        String playerName = commandDto.getCommandArguments();
        Set<Player> playersByName = playerService.getPlayersByName(playerName);
        if (playersByName.isEmpty()) commandDto.setReturningMsg("No players with name: " + playerName);

        Player player = playersByName.iterator().next();

        MatchId matchId = player.getLastMatches().get(0);
        Match match = matchService.getMatch(matchId);
        Participant matchForPlayer = match.participants.stream()
                .filter(participant -> player.getPlayerName().equals(participant.player.getPlayerName()))
                .toList()
                .get(0);

        commandDto.setReturningMsg(matchForPlayer.toString());
        return commandDto;
    }

    @Override
    public CommandDto updateGuestScore(CommandDto commandDto) {
        String updateInfo = commandDto.getCommandArguments();
        String[] arrOfMsg = updateInfo.split(" ");
        System.out.println(Arrays.toString(arrOfMsg));
        if (arrOfMsg.length != 3) {
            commandDto.setReturningMsg("Wrong amount of arguments, should be 3: " + arrOfMsg);
            return commandDto;
        }
        String player = arrOfMsg[0];
        String scoreType = arrOfMsg[1];
        float scoreValue = Float.parseFloat(arrOfMsg[2]);

        commandDto.setReturningMsg(
                playersListToString(dbService.updatePlayer(player, true, scoreType, scoreValue)));
        commandDto.deleteLastChannelMsg(true);
        commandDto.deleteCommandMsg(true);
        commandDto.setReturnMsgChannelId(DiscordChannelID.GUEST_HIGHSCORE.label);
        return commandDto;
    }

    @Override
    public CommandDto updateGuestWin(CommandDto commandDto) {
        String playerName = commandDto.getCommandArguments();
        commandDto.setReturningMsg(
                playersListToString(dbService.updatePlayerWin(playerName, true)));
        commandDto.deleteLastChannelMsg(true);
        commandDto.deleteCommandMsg(true);
        commandDto.setReturnMsgChannelId(DiscordChannelID.GUEST_HIGHSCORE.label);
        return commandDto;
    }

    @Override
    public CommandDto updateSDScore(CommandDto commandDto) {
        String updateInfo = commandDto.getCommandArguments();
        String[] arrOfMsg = updateInfo.split(" ");
        if (arrOfMsg.length != 3) {
            commandDto.setReturningMsg("Wrong amount of arguments, should be 3: " + arrOfMsg);
            return commandDto;
        }
        System.out.println(Arrays.toString(arrOfMsg));
        String player = arrOfMsg[0];
        String scoreType = arrOfMsg[1];
        float scoreValue = Float.parseFloat(arrOfMsg[2]);

        commandDto.setReturningMsg(
                playersListToString(dbService.updatePlayer(player, false, scoreType, scoreValue)));
        commandDto.deleteLastChannelMsg(true);
        commandDto.deleteCommandMsg(true);
        commandDto.setReturnMsgChannelId(DiscordChannelID.SD_HIGHSCORE.label);
        return commandDto;
    }

    @Override
    public CommandDto updateSDWin(CommandDto commandDto) {
        String playerName = commandDto.getCommandArguments();
        commandDto.setReturningMsg(
                playersListToString(dbService.updatePlayerWin(playerName, false)));
        commandDto.deleteLastChannelMsg(true);
        commandDto.deleteCommandMsg(true);
        commandDto.setReturnMsgChannelId(DiscordChannelID.SD_HIGHSCORE.label);
        return commandDto;
    }

    private String playersListToString(List<socially.disturbed.presentation.Player> players) {
        StringBuilder message = new StringBuilder();
        for (socially.disturbed.presentation.Player p: players) {
            message.append(p.toString());
        }
        return message.toString();
    }

    private void deleteLastChannelMsg(MessageChannel channel) {
        try {
            channel.getLastMessage().block().delete().subscribe();
        } catch (Exception e) {
            System.out.println("Failed to delete last message, moving calmly forward");
        }
    }
}
