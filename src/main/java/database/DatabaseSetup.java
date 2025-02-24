package database;

import java.sql.Connection;
import java.sql.DriverManager;
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
    }
}