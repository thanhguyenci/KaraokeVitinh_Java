package video;

import com.sun.jna.NativeLibrary;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VideoPlayerWithSongList {
    private JFrame frame; // Main frame
    private JList<String> songList; // Song list component
    private DefaultListModel<String> listModel; // Manages song list contents
    private EmbeddedMediaPlayerComponent mediaPlayerComponent; // VLCJ media player
    
    public VideoPlayerWithSongList() {
        // Specify the path to the VLC library if necessary
        NativeLibrary.addSearchPath("libvlc", "C:\\Program Files\\VideoLAN\\VLC");

        frame = new JFrame("Java Video Player with Song List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600); // Set window size
        frame.setLayout(new BorderLayout());

        // Create the video player component
        mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
        frame.add(mediaPlayerComponent, BorderLayout.CENTER);

        // Create a panel for song list and controls
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BorderLayout());
        sidePanel.setPreferredSize(new Dimension(200, 600));

        // Create a list to display songs
        listModel = new DefaultListModel<>();
        songList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(songList);
        sidePanel.add(scrollPane, BorderLayout.CENTER);

        // Add some sample songs/videos to the list
        listModel.addElement("example-song.mp3"); // Replace with actual file paths
        listModel.addElement("example-video.mp4");
        listModel.addElement("videos/mountains.mp4");

        // Create playback controls
        JPanel controlPanel = new JPanel();
        JButton playButton = new JButton("Play");
        JButton pauseButton = new JButton("Pause");
        JButton stopButton = new JButton("Stop");

        controlPanel.add(playButton);
        controlPanel.add(pauseButton);
        controlPanel.add(stopButton);
        sidePanel.add(controlPanel, BorderLayout.SOUTH);

        frame.add(sidePanel, BorderLayout.EAST);

        // Add action listeners to buttons
        playButton.addActionListener(e -> playMedia());
        pauseButton.addActionListener(e -> mediaPlayerComponent.mediaPlayer().controls().pause());
        stopButton.addActionListener(e -> mediaPlayerComponent.mediaPlayer().controls().stop());

        // Add a double-click event to the list items
        songList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                playMedia();
            }
        });

        frame.setVisible(true);
    }

    private void playMedia() {
        String selectedMedia = songList.getSelectedValue();
        if (selectedMedia != null) {
            // Play the selected media
            mediaPlayerComponent.mediaPlayer().media().start(selectedMedia);
        } else {
            JOptionPane.showMessageDialog(frame, "Please select a song or video to play.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VideoPlayerWithSongList());
    }
}