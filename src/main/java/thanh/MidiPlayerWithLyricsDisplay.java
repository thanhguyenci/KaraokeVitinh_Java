package thanh;

import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class MidiPlayerWithLyricsDisplay {
    public static void main(String[] args) {
        try {
            // Load the MIDI file
            File midiFile = new File("MIDI California Vietnamese Vol20\\QUÊN ĐI HẾT ĐAM MÊ_(830215).mid"); // Replace with your MIDI file path
            Sequence sequence = MidiSystem.getSequence(midiFile);
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.setSequence(sequence);

            // Extract and store lyrics with timestamps
            Map<Long, String> lyricsMap = new TreeMap<>();
            for (Track track : sequence.getTracks()) {
                for (int i = 0; i < track.size(); i++) {
                    MidiEvent event = track.get(i);
                    MidiMessage message = event.getMessage();
                    if (message instanceof MetaMessage) {
                        MetaMessage metaMessage = (MetaMessage) message;
                        if (metaMessage.getType() == 0x05) { // 0x05 is the MIDI Lyrics Meta Event
                            String lyrics = new String(metaMessage.getData());
                            lyricsMap.put(event.getTick(), lyrics);
                        }
                    }
                }
            }

            // Create a GUI for displaying lyrics
            JFrame lyricFrame = new JFrame("Karaoke Lyrics");
            lyricFrame.setSize(800, 200);
            lyricFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            lyricFrame.setLayout(new BorderLayout());

            JLabel lyricLabel = new JLabel("", SwingConstants.CENTER);
            lyricLabel.setFont(new Font("Arial", Font.BOLD, 24));
            lyricLabel.setForeground(Color.BLUE);
            lyricFrame.add(lyricLabel, BorderLayout.CENTER);

            lyricFrame.setVisible(true);

            // Start playback
            sequencer.start();

            // Display lyrics in sync with MIDI playback
            new Thread(() -> {
                try {
                    while (sequencer.isRunning()) {
                        long tickPosition = sequencer.getTickPosition();
                        lyricsMap.entrySet().removeIf(entry -> {
                            if (entry.getKey() <= tickPosition) {
                                String currentLyric = entry.getValue();
                                SwingUtilities.invokeLater(() -> lyricLabel.setText(currentLyric)); // Update GUI
                                return true; // Remove displayed lyrics
                            }
                            return false;
                        });
                        Thread.sleep(100); // Check every 100ms
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();

            // Wait for playback to finish
            while (sequencer.isRunning()) {
                Thread.sleep(500);
            }

            sequencer.close();
        } catch (IOException | MidiUnavailableException | InvalidMidiDataException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}