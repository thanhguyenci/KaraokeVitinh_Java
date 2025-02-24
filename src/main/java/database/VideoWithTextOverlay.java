package database;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class VideoWithTextOverlay extends Application {
    private static final String DB_URL = "jdbc:sqlite:video_app.db";

    public static String fetchOverlayText(int videoId, Duration currentTime) {
        String overlayText = "Default Text";
        String sql = "SELECT overlay FROM videos WHERE video_id = ? AND time_start <= ? AND time_end >= ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, videoId);
            pstmt.setDouble(2, currentTime.toMillis());
            pstmt.setDouble(3, currentTime.toMillis());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                overlayText = rs.getString("overlay");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return overlayText;
    }

    @Override
    public void start(Stage primaryStage) {
        // Load video as before
        String videoPath = new File("videos/mountains.mp4").toURI().toString();
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);

        // Create overlay text
        Label overlayText = new Label(fetchOverlayText(1, Duration.ZERO));
        overlayText.setStyle("-fx-font-size: 24px; -fx-text-fill: white;");

        StackPane root = new StackPane(mediaView, overlayText);
        Scene scene = new Scene(root, 800, 600);

        primaryStage.setScene(scene);
        primaryStage.show();

        // Dynamically update overlay text during playback
        mediaPlayer.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
            overlayText.setText(fetchOverlayText(1, newTime));
        });

        mediaPlayer.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}