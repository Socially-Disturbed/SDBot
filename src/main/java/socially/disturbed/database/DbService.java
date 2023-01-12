package socially.disturbed.database;

import socially.disturbed.api.pubg.model.player.Player;
import socially.disturbed.presentation.User;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class DbService {

    public final String GET_SD_USERS = "SELECT * FROM \"SD_SCORE\"";
    public final String GET_GUEST_USERS = "SELECT * FROM \"SD_GUEST_SCORE\"";
    public final String GET_SD_USER = "SELECT * FROM \"SD_SCORE\"";
    public final String GET_GUEST_USER = "SELECT * FROM \"SD_GUEST_SCORE\"";
    public final String INSERT_NEW_GUEST_USER = "INSERT INTO \"SD_GUEST_SCORE\"(\"NAME\", \"WIN\")";
    public final String INSERT_NEW_SD_USER = "INSERT INTO \"SD_SCORE\"(\"NAME\")";
    public final String UPDATE_SD_SCORE = "UPDATE \"SD_SCORE\"";
    public final String UPDATE_GUEST_SCORE = "UPDATE \"SD_GUEST_SCORE\"";


    public DbService() {}

    public void addUser(boolean guest, String username) {
        String statement = "";
        if (guest) {
            statement = INSERT_NEW_GUEST_USER + " VALUES ('" + username + "', 1)";
        }
        else {
            statement = INSERT_NEW_SD_USER + " VALUES ('" + username+ "')";
        }
        System.out.println(statement);

        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:8080/postgres", "postgres", "admin");
             PreparedStatement preparedStatement = conn.prepareStatement(statement)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println(resultSet);
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Set<User> updateUserScore(String username, boolean guestList, float value) {
        User user = getUser(username, guestList);
        if (user == null) {
            System.out.println("No user registered named: " + username + "\n adding user to list");
            addUser(guestList, username);
        }
        Set<User> users = new HashSet<>();
        String statement = "";
        if (guestList) {
            statement = UPDATE_GUEST_SCORE;
        }
        else {
            statement = UPDATE_SD_SCORE;
        }
        statement += " SET \"SCORE\" = " + value + " WHERE \"NAME\" = '" + username + "'";
        System.out.println(statement);
        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:8080/postgres", "postgres", "admin");
             PreparedStatement preparedStatement = conn.prepareStatement(statement)) {

            int rowUpdated = preparedStatement.executeUpdate();
            if (rowUpdated > 0) {
                users = getAllUsers(guestList);
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public void updateUserRankedStats(Player player, boolean guestList) {
        String statement = "";
        if (guestList) {
            statement = UPDATE_GUEST_SCORE;
        }
        else {
            statement = UPDATE_SD_SCORE;
        }
        String columsAndValues = String.format("\"ADR\" = %f \"RANK\" = '%s'",
                player.rankedStats.averageDamage, player.rankedStats.rankTier.getRank());
        statement += " SET "+ columsAndValues + " WHERE \"NAME\" = '" + player.getPlayerName() + "'";
        System.out.println(statement);
        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:8080/postgres", "postgres", "admin");
             PreparedStatement preparedStatement = conn.prepareStatement(statement)) {

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Set<User> updateUserWin(String username, boolean guestList) {
        User user = getUser(username, guestList);
        if (user == null) {
            System.out.println("No players registered named: " + username + "\n adding player to list");
            addUser(guestList, username);
            return getAllUsers(guestList);
        }
        Set<User> users = new HashSet<>();
        System.out.println(user);
        int wins = user.wins + 1;

        String statement = "";
        if (guestList) {
            statement += UPDATE_GUEST_SCORE;
        }
        else {
            statement += UPDATE_SD_SCORE;
        }
        statement += " SET \"WINS\" = " + wins + " WHERE \"NAME\" = '" + username + "'";
        System.out.println(statement);

        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:8080/postgres", "postgres", "admin");
             PreparedStatement preparedStatement = conn.prepareStatement(statement)) {

            int rowUpdated = preparedStatement.executeUpdate();
            if (rowUpdated > 0) {
                users = getAllUsers(guestList);
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public User getUser(String username, boolean guestList) {
        String statement = "";
        if (guestList) {
            statement += GET_GUEST_USER;
        }
        else {
            statement += GET_SD_USER;
        }
        statement += " WHERE \"NAME\" = '" + username + "'";
        System.out.println(statement);
        // auto close connection and preparedStatement
        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:8080/postgres", "postgres", "admin");
             PreparedStatement preparedStatement = conn.prepareStatement(statement)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                String name = resultSet.getString("NAME");
                float score = resultSet.getFloat("SCORE");
                int wins = resultSet.getInt("WINS");
                int adr = resultSet.getInt("ADR");

                user = new User(name, wins, score, adr, "");
            }
            return user;

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Set<User> getAllUsers(boolean guestList) {
        String statement = "";
        if (guestList) {
            statement = GET_GUEST_USERS;
        } else {
            statement = GET_SD_USERS;
        }
        Set<User> users = new HashSet<>();
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
                String rank = resultSet.getString("rank");

                User user = new User(name, wins, score, adr, rank);
                users.add(user);

            }
            users.forEach(u -> System.out.println(u));

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }
}
