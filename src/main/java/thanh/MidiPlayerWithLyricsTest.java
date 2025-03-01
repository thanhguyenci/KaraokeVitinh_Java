package thanh;

import javax.sound.midi.*;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class MidiPlayerWithLyricsTest {
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
            lyricFrame.setSize(800, 500);
            lyricFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            lyricFrame.setLayout(new BorderLayout());

            JTextPane lyricPane = new JTextPane();
            lyricPane.setEditable(false);
            lyricPane.setFont(new Font("Arial", Font.BOLD, 20));
            lyricPane.setForeground(Color.BLACK);
            lyricPane.setBackground(Color.WHITE);
            JScrollPane scrollPane = new JScrollPane(lyricPane);
            lyricFrame.add(scrollPane, BorderLayout.CENTER);

            lyricFrame.setVisible(true);

            // Prepare the StyledDocument for controlling text styling
            StyledDocument styledDoc = lyricPane.getStyledDocument();
            Style defaultStyle = styledDoc.addStyle("Default", null);
            Style highlightedStyle = styledDoc.addStyle("Highlighted", null);
            StyleConstants.setForeground(defaultStyle, Color.BLACK);
            StyleConstants.setFontSize(defaultStyle, 20);

            StyleConstants.setForeground(highlightedStyle, Color.BLUE);
            StyleConstants.setBold(highlightedStyle, true);
            StyleConstants.setFontSize(highlightedStyle, 24);

            // Add all lyrics to the JTextPane
            lyricsMap.forEach((timestamp, lyricLine) -> {
                try {
                    //styledDoc.insertString(styledDoc.getLength(), lyricLine + "\n", defaultStyle);
                    styledDoc.insertString(styledDoc.getLength(), lyricLine + "\n", defaultStyle);
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
            });

            // Track the position of lyrics to highlight during playback
            Object[] timestamps = lyricsMap.keySet().toArray();
            Object[] lyricLines = lyricsMap.values().toArray();

            // Start playback
            sequencer.start();

            // Display highlighted lyrics during MIDI playback
            new Thread(() -> {
                try {
                    int currentIndex = 0;
                    while (sequencer.isRunning()) {
                        long tickPosition = sequencer.getTickPosition();

                        // Highlight the current lyric as it plays
                        while (currentIndex < timestamps.length && (long) timestamps[currentIndex] <= tickPosition) {
                            final int indexToHighlight = currentIndex;
                            SwingUtilities.invokeLater(() -> {
                                try {
                                    // Clear highlighting on all lines
                                    styledDoc.setCharacterAttributes(0, styledDoc.getLength(), defaultStyle, true);

                                    // Highlight the current lyric line
                                    int start = styledDoc.getDefaultRootElement()
                                            .getElement(indexToHighlight).getStartOffset();
                                    int end = styledDoc.getDefaultRootElement()
                                            .getElement(indexToHighlight).getEndOffset();
                                    styledDoc.setCharacterAttributes(start, end - start, highlightedStyle, true);

                                    // Scroll to the current line
                                    lyricPane.scrollRectToVisible(new Rectangle(lyricPane.modelToView(start)));
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