package thanh;

import javax.sound.midi.*;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class MidiPlayerWithLyrics2 {
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
            lyricFrame.setSize(800, 400);
            lyricFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            lyricFrame.setLayout(new BorderLayout());

            JTextPane lyricPane = new JTextPane();
            lyricPane.setEditable(false); // Prevent editing
            lyricPane.setFont(new Font("Arial", Font.BOLD, 24));
            lyricPane.setForeground(Color.BLACK);
            lyricPane.setBackground(Color.WHITE);
            JScrollPane scrollPane = new JScrollPane(lyricPane);
            lyricFrame.add(scrollPane, BorderLayout.CENTER);

            lyricFrame.setVisible(true); // Show GUI

            // Prepare a StyledDocument for highlighting functionality
            StyledDocument styledDoc = lyricPane.getStyledDocument();
            Style defaultStyle = styledDoc.addStyle("Default", null);
            Style highlightedStyle = styledDoc.addStyle("Highlighted", null);
            StyleConstants.setForeground(defaultStyle, Color.BLACK);
            StyleConstants.setForeground(highlightedStyle, Color.BLUE);
            StyleConstants.setBold(highlightedStyle, true);

            // Display lyrics in sync with MIDI playback
            new Thread(() -> {
                try {
                    while (sequencer.isRunning()) {
                        long tickPosition = sequencer.getTickPosition();

                        lyricsMap.entrySet().removeIf(entry -> {
                            if (entry.getKey() <= tickPosition) {
                                String currentLyric = entry.getValue();

                                SwingUtilities.invokeLater(() -> {
                                    try {
                                        // Clear previous text
                                        styledDoc.remove(0, styledDoc.getLength());
                                        // Insert current lyric with highlight
                                        styledDoc.insertString(0, currentLyric, highlightedStyle);
                                    } catch (BadLocationException e) {
                                        e.printStackTrace();
                                    }
                                });
                                return true; // Remove displayed lyric
                            }
                            return false;
                        });
                        Thread.sleep(100); // Check every 100ms
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();

            // Start playback
            sequencer.start();

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