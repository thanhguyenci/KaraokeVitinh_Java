package thanh;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import static javafx.application.Application.launch;


//public class Video extends Application JFrame implements ActionListener {
    public class Video extends JFrame implements ActionListener {


        public Video() {
        //super("");
        //setResizable(false);
        setSize(new Dimension(640, 480));
        //setMinimumSize(new Dimension(800, 600));
        //setMaximumSize(new Dimension(1280, 720));
        //getContentPane().setBackground(Color.WHITE);
        setTitle("Video");
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException |
                 UnsupportedLookAndFeelException e) {
            System.err.println(e.getCause());
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /*public static void start(Stage primaryStage) throws Exception {
        // TODO Auto-generated method stub
        try {
            //https://www.javatpoint.com/javafx-playing-video

            //Initialising path of the media file, replace this with your file path
            String path = "src/res/MP4/mountains (2).mp4";

            //Instantiating Media class
            Media media = new Media(new File(path).toURI().toString());

            //Instantiating MediaPlayer class
            MediaPlayer mediaPlayer = new MediaPlayer(media);

            //Instantiating MediaView class
            MediaView mediaView = new MediaView(mediaPlayer);

            //by setting this property to true, the Video will be played
            mediaPlayer.setAutoPlay(true);

            //setting group and scene
            Group root = new Group();
            root.getChildren().add(mediaView);
            Scene scene = new Scene(root, 1280, 720);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Playing Video");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getLocalizedMessage());
        }
    }*/

    public static void main(String[] args) {

        JFrame VideoFrame = new Video();
        VideoFrame.setVisible(true);
        //launch(args);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

}
