package thanh;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import java.io.File;

public class VideoPlayer extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Path to the video file
        String videoPath = new File("videos\\mountains_-_59291 (1080p)_mpeg1video_x264.mp4").toURI().toString();
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);

        // Create Scene and show the stage
        Scene scene = new Scene(new javafx.scene.layout.StackPane(mediaView), 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("JavaFX Video Player");
        primaryStage.show();

        // Start playing the video
        mediaPlayer.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
