package Connections;

import java.sql.*;

public class DatabaseManager {

    private static final String URL = "jdbc:mysql://localhost:3306/wordle_db";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // Vul hier je wachtwoord in als je dat hebt!

    public void saveGameResult(String playerName, boolean isWin, int guessesUsed) {
        // SQL query om te kijken of de speler al in de tabel staat
        String selectSql = "SELECT games_played, games_won, total_guesses, fastest_win FROM player_stats WHERE player_name = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

            PreparedStatement selectStmt = conn.prepareStatement(selectSql);
            selectStmt.setString(1, playerName);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                // SCENARIO A: De speler bestaat al! We halen de oude gegevens op en tellen erbij op.
                int gamesPlayed = rs.getInt("games_played") + 1;
                int gamesWon = rs.getInt("games_won") + (isWin ? 1 : 0);
                int totalGuesses = rs.getInt("total_guesses") + guessesUsed;

                // Bereken direct het nieuwe gemiddelde
                double avgGuesses = (double) totalGuesses / gamesPlayed;

                // Controleer het persoonlijk record (fastest win)
                int currentFastest = rs.getInt("fastest_win");
                boolean hadPriorWin = !rs.wasNull(); // Check of fastest_win niet stiekem leeg (NULL) was

                int newFastest = currentFastest;
                if (isWin) {
                    // Als dit de eerste winst is, OF dit potje was sneller dan het oude record:
                    if (!hadPriorWin || guessesUsed < currentFastest) {
                        newFastest = guessesUsed;
                    }
                }

                // Update de speler in de database
                String updateSql = "UPDATE player_stats SET games_played = ?, games_won = ?, total_guesses = ?, avg_guesses = ?, fastest_win = ? WHERE player_name = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                updateStmt.setInt(1, gamesPlayed);
                updateStmt.setInt(2, gamesWon);
                updateStmt.setInt(3, totalGuesses);
                updateStmt.setDouble(4, avgGuesses);

                // Als de speler nog nooit gewonnen heeft, laten we dit veld leeg (NULL) in de database
                if (isWin || hadPriorWin) {
                    updateStmt.setInt(5, newFastest);
                } else {
                    updateStmt.setNull(5, java.sql.Types.INTEGER);
                }

                updateStmt.setString(6, playerName);
                updateStmt.executeUpdate();

            } else {
                // SCENARIO B: Gloednieuwe speler! We maken een gloednieuwe rij aan.
                int gamesWon = isWin ? 1 : 0;
                double avgGuesses = guessesUsed; // Eerste potje, dus gemiddelde is gelijk aan deze beurt

                String insertSql = "INSERT INTO player_stats (player_name, games_played, games_won, total_guesses, avg_guesses, fastest_win) VALUES (?, 1, ?, ?, ?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                insertStmt.setString(1, playerName);
                insertStmt.setInt(2, gamesWon);
                insertStmt.setInt(3, guessesUsed);
                insertStmt.setDouble(4, avgGuesses);

                if (isWin) {
                    insertStmt.setInt(5, guessesUsed);
                } else {
                    insertStmt.setNull(5, java.sql.Types.INTEGER);
                }

                insertStmt.executeUpdate();
            }
            System.out.println("Stats succesvol bijgewerkt voor " + playerName);

        } catch (SQLException e) {
            System.out.println("Database fout: " + e.getMessage());
        }
    }
}