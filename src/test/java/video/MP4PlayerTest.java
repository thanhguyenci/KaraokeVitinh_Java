package video;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class MP4PlayerTest {

    private MP4Player mp4Player;
    private MediaPlayer mediaPlayer;

    @Start
    public void start(Stage stage) {
        mp4Player = new MP4Player();
        mp4Player.start(stage);
        mediaPlayer = new MediaPlayer(new Media(new File("videos\\test_x264.mp4").toURI().toString()));
    }

    @BeforeEach
    void setUp() {
        // Initialize any resources before each test
    }

    @AfterEach
    void tearDown() {
        // Clean up any resources after each test
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
        }
    }

    @Test
    void testVideoPlayback(FxRobot robot) {
        // Arrange
        String videoPath = "videos\\test_x264.mp4"; // Replace with your MP4 file path
        File videoFile = new File(videoPath);
        assertTrue(videoFile.exists(), "Video file does not exist: " + videoPath);

        // Act
        // The video should start playing automatically due to mediaPlayer.setAutoPlay(true);

        // Assert
        assertNotNull(mediaPlayer, "MediaPlayer should not be null");
        assertEquals(MediaPlayer.Status.PLAYING, mediaPlayer.getStatus(), "Video should be playing");
        // You can add more specific assertions here, e.g.,
        // assertTrue(mediaPlayer.getDuration().greaterThan(Duration.ZERO));
        // ...
        System.out.println("Video playback test passed.");
    }
}