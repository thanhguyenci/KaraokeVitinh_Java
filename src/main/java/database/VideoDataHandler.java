package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class VideoDataHandler {
    private static final String DB_URL = "jdbc:sqlite:video_app.db";

    // Insert video metadata
    public void insertVideoData(String path, String title, String description, String overlay) {
        String sql = "INSERT INTO videos(path, title, description, overlay) VALUES(?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, path);
            pstmt.setString(2, title);
            pstmt.setString(3, description);
            pstmt.setString(4, overlay);
            pstmt.executeUpdate();
            System.out.println("Video metadata inserted.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Retrieve video metadata
    public void getVideoData() {
        String sql = "SELECT * FROM videos";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Path: " + rs.getString("path"));
                System.out.println("Title: " + rs.getString("title"));
                System.out.println("Description: " + rs.getString("description"));
                System.out.println("Overlay: " + rs.getString("overlay"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        VideoDataHandler handler = new VideoDataHandler();

        // Example: Insert data
        handler.insertVideoData("videos/mountains.mp4", "Mountains", "Beautiful mountain scenes", "Welcome!");

        // Example: Retrieve data
        handler.getVideoData();
    }
}