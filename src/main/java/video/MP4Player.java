package video;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import java.io.File;

public class MP4Player extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Load the video file
        String videoPath = "videos\\test_x264.mp4"; // Use full absolute path
        Media media = new Media(new File(videoPath).toURI().toString());

        // Create MediaPlayer and MediaView
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);

        // Play video in HD
        mediaPlayer.setAutoPlay(true);
        mediaView.setPreserveRatio(true);
        
        // Bind the Stage size to the MediaView size
        /*primaryStage.widthProperty().bind(mediaView.fitWidthProperty());
        primaryStage.heightProperty().bind(mediaView.fitHeightProperty());*/
        
        /*mediaPlayer.setOnReady(() -> {
            // Get the video's width and height from the Media object
            double videoWidth = media.getWidth()/2;
            double videoHeight = media.getHeight()/2;

            // Set the stage size to match the video's dimensions
            primaryStage.setWidth(videoWidth);
            primaryStage.setHeight(videoHeight);
        });*/
        
        mediaView.setFitWidth(1280/2);  // Initial width
        mediaView.setFitHeight(720/2);

        // Create Scene and show stage
        StackPane root = new StackPane(mediaView);
        Scene scene = new Scene(root, 1280/2, 720/2); // Set resolution
        //Scene scene = new Scene(root, 1920, 1080);
        //Scene scene = new Scene(root);

        primaryStage.setTitle("JavaFX MP4 Player");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
