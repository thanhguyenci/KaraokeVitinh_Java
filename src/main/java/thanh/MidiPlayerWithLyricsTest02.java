package thanh;

import javax.sound.midi.*;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class MidiPlayerWithLyricsTest02 {
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

            // Create a GUI to display lyrics
            JFrame lyricFrame = new JFrame("Karaoke Lyrics");
            lyricFrame.setSize(800, 300);
            lyricFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JTextPane lyricPane = new JTextPane();
            lyricPane.setEditable(false);
            lyricPane.setFont(new Font("Arial", Font.BOLD, 28));
            lyricPane.setForeground(Color.BLACK);
            lyricPane.setBackground(Color.WHITE);
            lyricFrame.add(lyricPane);
            lyricFrame.setVisible(true);

            // Prepare the StyledDocument for text styling
            StyledDocument styledDoc = lyricPane.getStyledDocument();
            Style currentStyle = styledDoc.addStyle("CurrentStyle", null);
            Style nextStyle = styledDoc.addStyle("NextStyle", null);

            // Set styles for the current line and next line
            StyleConstants.setForeground(currentStyle, Color.BLUE);
            StyleConstants.setBold(currentStyle, true);
            StyleConstants.setFontSize(currentStyle, 30);

            StyleConstants.setForeground(nextStyle, Color.GRAY);
            StyleConstants.setBold(nextStyle, false);
            StyleConstants.setFontSize(nextStyle, 24);

            // Track lyrics to display in two lines
            Object[] timestamps = lyricsMap.keySet().toArray();
            Object[] lyricLines = lyricsMap.values().toArray();

            // Start playback
            sequencer.start();

            // Handle lyric line updates during playback
            new Thread(() -> {
                int currentIndex = 0;
                try {
                    while (sequencer.isRunning()) {
                        long tickPosition = sequencer.getTickPosition();

                        // Highlight the current line and dim the next line
                        while (currentIndex < timestamps.length && (long) timestamps[currentIndex] <= tickPosition) {
                            final int indexToDisplay = currentIndex;

                            SwingUtilities.invokeLater(() -> {
                                try {
                                    // Update the text with two lines
                                    styledDoc.remove(0, styledDoc.getLength()); // Clear the pane first

                                    // Add current line (highlighted)
                                    styledDoc.insertString(
                                            styledDoc.getLength(),
                                            lyricLines[indexToDisplay] + "\n",
                                            currentStyle
                                    );

                                    // Add next line (dimmed), if available
                                    if (indexToDisplay + 1 < lyricLines.length) {
                                        styledDoc.insertString(
                                                styledDoc.getLength(),
                                                lyricLines[indexToDisplay + 1].toString(),
                                                nextStyle
                                        );
                                    }
                                } catch (BadLocationException e) {
                                    e.printStackTrace();
                                }
                            });

                            currentIndex++;
                        }

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