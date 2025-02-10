package thanh;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.File;
import javafx.util.Duration;

public class VideoWithTextOverlay extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Load the video file
        String videoPath = new File("videos\\mountains_-_59291 (1080p)_mpeg1video_x264.mp4").toURI().toString();
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);

        // Create a label to overlay text on the video
        Label overlayText = new Label("Welcome to the Video!");
        overlayText.setStyle("-fx-font-size: 24px; -fx-text-fill: white; -fx-background-color: rgba(0, 0, 0, 0.5); -fx-padding: 10;");

        // Create a layout that overlays the text on the video
        StackPane root = new StackPane();
        root.getChildren().addAll(mediaView, overlayText);

        // Scene setup
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Video with Overlay Text");
        primaryStage.show();

        // Start the video
        mediaPlayer.play();

        // Change text dynamically based on video time (Example: Show new text at 5 seconds)
        mediaPlayer.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
            if (newTime.greaterThanOrEqualTo(Duration.seconds(5))) {
                overlayText.setText("Now Playing: Amazing Scene!");
            }
            if (newTime.greaterThanOrEqualTo(Duration.seconds(10))) {
                overlayText.setText("Enjoy the Video!");
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
