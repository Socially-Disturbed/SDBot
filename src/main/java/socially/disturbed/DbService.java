package socially.disturbed;

import socially.disturbed.presentation.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbService {

    public final String GET_SD_PLAYERS = "SELECT * FROM \"SD_SCORE\"";
    public final String GET_GUEST_PLAYERS = "SELECT * FROM \"SD_GUEST_SCORE\"";
    public final String GET_PLAYER = "SELECT * FROM \"SD_SCORE\"";
    public final String GET_GUEST_PLAYER = "SELECT * FROM \"SD_GUEST_SCORE\"";
    public final String INSERT_NEW_GUEST = "INSERT INTO \"SD_GUEST_SCORE\"(\"NAME\")";
    public final String INSERT_NEW_SD = "INSERT INTO \"SD__SCORE\"(\"NAME\")";
    public final String UPDATE_SD_SCORE = "UPDATE \"SD_SCORE\"";
    public final String UPDATE_GUEST_SCORE = "UPDATE \"SD_GUEST_SCORE\"";


    public DbService() {}

    public void addPlayer(boolean guest, String playerName) {
        String statement = "";
        if (guest) {
            statement = INSERT_NEW_GUEST;
        }
        else {
            statement = INSERT_NEW_SD;
        }
        statement += " VALUES ('" + playerName+ "')";
        System.out.println(statement);

        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:8080/postgres", "postgres", "admin");
             PreparedStatement preparedStatement = conn.prepareStatement(statement)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println(resultSet);
            return;
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }

    public List<Player> updatePlayer(String playerName, boolean guestList, String scoreType, float value) {
        Player player = getPlayer(playerName, guestList);
        if (player == null) {
            System.out.println("No players registered named: " + playerName + "\n adding player to list");
            addPlayer(guestList, playerName);
            return updatePlayer(playerName, guestList, "WINS", 1);

        }
        List<Player> players = new ArrayList<>();
        String statement = "";
        if (guestList) {
            statement = UPDATE_GUEST_SCORE;
        }
        else {
            statement = UPDATE_SD_SCORE;
        }
        statement += " SET \"" +scoreType + "\" = " + value + " WHERE \"NAME\" = '" + playerName + "'";
        System.out.println(statement);
        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:8080/postgres", "postgres", "admin");
             PreparedStatement preparedStatement = conn.prepareStatement(statement)) {

            int rowUpdated = preparedStatement.executeUpdate();
            if (rowUpdated > 0) {
                players = getAllPlayers(guestList);
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return players;
    }

    public List<Player> updatePlayerWin(String playerName, boolean guestList) {
        Player player = getPlayer(playerName, guestList);
        if (player == null) {
            System.out.println("No players registered named: " + playerName + "\n adding player to list");
            addPlayer(guestList, playerName);
            return updatePlayer(playerName, guestList, "WINS", 1);
        }
        List<Player> players = new ArrayList<>();
        System.out.println(player);
        int wins = player.getWins() + 1;

        String statement = "";
        if (guestList) {
            statement += UPDATE_GUEST_SCORE;
        }
        else {
            statement += UPDATE_SD_SCORE;
        }
        statement += " SET \"WINS\" = " + wins + " WHERE \"NAME\" = '" + playerName + "'";
        System.out.println(statement);

        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:8080/postgres", "postgres", "admin");
             PreparedStatement preparedStatement = conn.prepareStatement(statement)) {

            int rowUpdated = preparedStatement.executeUpdate();
            if (rowUpdated > 0) {
                players = getAllPlayers(guestList);
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return players;
    }

    public Player getPlayer(String playerName, boolean guestList) {
        String statement = "";
        if (guestList) {
            statement += GET_GUEST_PLAYER;
        }
        else {
            statement += GET_PLAYER;
        }
        statement += " WHERE \"NAME\" = '" + playerName + "'";
        System.out.println(statement);
        // auto close connection and preparedStatement
        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:8080/postgres", "postgres", "admin");
             PreparedStatement preparedStatement = conn.prepareStatement(statement)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            Player player = null;
            if (resultSet.next()) {
                String name = resultSet.getString("NAME");
                float score = resultSet.getFloat("SCORE");
                int wins = resultSet.getInt("WINS");
                int adr = resultSet.getInt("ADR");

                player = new Player();
                player.setName(name);
                player.setMaxPoints(score);
                player.setWins(wins);
                player.setAdr(adr);
            }
            return player;

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Player> getAllPlayers(boolean guestList) {
        String statement = "";
        if (guestList) {
            statement = GET_GUEST_PLAYERS;
        } else {
            statement = GET_SD_PLAYERS;
        }
        List<Player> playerList = new ArrayList<>();
        // auto close connection and preparedStatement
        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:8080/postgres", "postgres", "admin");
            PreparedStatement preparedStatement = conn.prepareStatement(statement)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                String name = resultSet.getString("NAME");
                float score = resultSet.getFloat("SCORE");
                int wins = resultSet.getInt("WINS");
                int adr = resultSet.getInt("ADR");

                Player player = new Player();
                player.setName(name);
                player.setMaxPoints(score);
                player.setWins(wins);
                player.setAdr(adr);

                playerList.add(player);

            }
            playerList.forEach(p -> System.out.println(p));

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return playerList;
    }
}