package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class DatabaseSetup {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:video_app.db";

        // SQL to create a table to store video metadata
        String createTableSQL = "CREATE TABLE IF NOT EXISTS videos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "path TEXT NOT NULL, " +
                "title TEXT, " +
                "description TEXT, " +
                "overlay TEXT" +
                ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {

            // Create the table
            stmt.execute(createTableSQL);
            System.out.println("Table created successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }

        initializeDatabase(url);
    }

    private static void initializeDatabase(String DB_URL) {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS videosx ("
                + "video_id INTEGER NOT NULL, "
                + "time_start REAL NOT NULL, "
                + "time_end REAL NOT NULL, "
                + "overlay TEXT NOT NULL, "
                + "PRIMARY KEY (video_id, time_start, time_end)"
                + ")";

        String insertDataSQL = "INSERT INTO videosx (video_id, time_start, time_end, overlay) VALUES "
                + "(1, 0, 5000, 'Welcome to Mountains!'), "
                + "(1, 5000, 10000, 'Enjoy the scenic view...'), "
                + "(1, 10000, 15000, 'Nature at its best.'), "
                + "(1, 15000, 20000, 'Thank you for watching!')";

        /*try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement createTableStmt = conn.prepareStatement(createTableSQL);
             PreparedStatement insertDataStmt = conn.prepareStatement(insertDataSQL)) {

            createTableStmt.execute(); // Create table if not exists
            //insertDataStmt.execute();  // Insert sample data
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            // Create the table if it does not exist
            try (PreparedStatement createTableStmt = conn.prepareStatement(createTableSQL)) {
                createTableStmt.execute();
                System.out.println("Table 'videosx' created or already exists.");
            }

            // Insert data into the videosx table
            try (PreparedStatement insertDataStmt = conn.prepareStatement(insertDataSQL)) {
                insertDataStmt.execute();
                System.out.println("Sample data inserted into 'videosx' table.");
            }
        } catch (Exception e) {
            // Log any exceptions to identify runtime issues
            e.printStackTrace();
        }
    }
}