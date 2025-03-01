package video;

import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MidiKaraoke {

    // Example: Map timestamp (milliseconds) to corresponding lyric
    private static final Map<Long, String> lyrics = new HashMap<>();

    static {
        // Lyrics with timestamps in milliseconds
        lyrics.put(0L, "Welcome to the karaoke!");
        lyrics.put(2000L, "This is the first line of the song...");
        lyrics.put(5000L, "And here comes the second line...");
        lyrics.put(8000L, "Now the chorus! Sing along...");
        lyrics.put(12000L, "The song ends here. Thanks for singing!");
    }

    static class KaraokeDisplay extends JFrame {
        private JLabel lyricLabel;

        public KaraokeDisplay() {
            setTitle("MIDI Karaoke");
            setSize(600, 200);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            lyricLabel = new JLabel("", SwingConstants.CENTER);
            lyricLabel.setFont(new Font("Arial", Font.BOLD, 24));
            add(lyricLabel);
            setVisible(true);
        }

        public void updateLyric(String lyric) {
            lyricLabel.setText(lyric);
        }
    }

    public static void main(String[] args) {
        //File midiFile = new File("path/to/your/midi/file.mid");  // Replace with your MIDI file path
        File midiFile = new File("MIDI California Vietnamese Vol20\\QUÊN ĐI HẾT ĐAM MÊ_(830215).mid");  // Replace with your MIDI file path

        // Create the karaoke display
        KaraokeDisplay display = new KaraokeDisplay();

        try {
            // Open the MIDI sequencer
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.setSequence(MidiSystem.getSequence(midiFile));

            // Add a listener to update the lyrics while playing
            sequencer.addMetaEventListener(metaEvent -> {
                long currentTime = sequencer.getMicrosecondPosition() / 1000; // Convert to milliseconds
                String lyric = getLyricForTime(currentTime);
                if (lyric != null) {
                    display.updateLyric(lyric);
                }
            });

            System.out.println("Starting MIDI playback...");
            sequencer.start();

            // Wait for the playback to finish
            while (sequencer.isRunning()) {
                Thread.sleep(100); // Keep the main thread alive
            }

            sequencer.close();
            System.out.println("MIDI playback finished.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Helper method to get the lyric for the current timestamp
    private static String getLyricForTime(long currentTime) {
        String currentLyric = null;
        for (Map.Entry<Long, String> entry : lyrics.entrySet()) {
            if (currentTime >= entry.getKey()) {
                currentLyric = entry.getValue();
            } else {
                break;
            }
        }
        return currentLyric;
    }
}