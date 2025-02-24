package video;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.File;

public class MP4PlayerWithControls extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Load the video file
        String videoPath = "videos\\test_x264.mp4"; // Path to your video file
        Media media = new Media(new File(videoPath).toURI().toString());

        // Create MediaPlayer and MediaView
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);

        // Set MediaView properties
        mediaView.setPreserveRatio(true);
        mediaView.setFitWidth(640); // Set the video width
        mediaView.setFitHeight(360); // Set the video height

        // Create control buttons
        Button playButton = new Button("Play");
        Button pauseButton = new Button("Pause");
        Button fastForwardButton = new Button("Fast Forward");
        Button backwardButton = new Button("Backward");

        // Set button actions
        playButton.setOnAction(e -> mediaPlayer.play()); // Play the video
        pauseButton.setOnAction(e -> mediaPlayer.pause()); // Pause the video
        fastForwardButton.setOnAction(e -> {
            // Increase the rate of playback for fast forward
            mediaPlayer.setRate(mediaPlayer.getRate() + 0.5);
        });
        backwardButton.setOnAction(e -> {
            // Rewind the video and reset to normal playback speed
            mediaPlayer.seek(mediaPlayer.getCurrentTime().subtract(javafx.util.Duration.seconds(5))); // Rewind 5 seconds
            mediaPlayer.setRate(1.0); // Reset playback speed to normal
        });

        // Create a horizontal box for the controls
        HBox controls = new HBox(10, playButton, pauseButton, fastForwardButton, backwardButton);
        controls.setStyle("-fx-alignment: center; -fx-padding: 10;"); // Style the control box

        // Combine MediaView and controls in a vertical box
        VBox root = new VBox(10, mediaView, controls);
        root.setStyle("-fx-alignment: center; -fx-padding: 10;");

        // Create and set the scene
        Scene scene = new Scene(root, 640, 400); // Set the window size
        primaryStage.setTitle("JavaFX MP4 Player with Controls");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}