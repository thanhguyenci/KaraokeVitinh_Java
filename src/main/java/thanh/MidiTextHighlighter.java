package thanh;

import javax.sound.midi.*;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.io.File;

public class MidiTextHighlighter {
    private static JTextPane textPane;
    private static StyledDocument doc;
    private static String[] lyrics = {
            "Hello", "world,", "this", "is", "a", "MIDI", "highlight", "test!"
    }; // Example lyrics
    private static int currentWordIndex = 0;

    public static void main(String[] args) {
        // Setup UI
        JFrame frame = new JFrame("MIDI Player with Text Highlighting");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 300);

        textPane = new JTextPane();
        doc = textPane.getStyledDocument();

        // Set the text in JTextPane
        try {
            for (String word : lyrics) {
                doc.insertString(doc.getLength(), word + " ", null);
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        frame.add(new JScrollPane(textPane));
        frame.setVisible(true);

        // Play MIDI and sync text highlighting
        playMidi("MIDI California Vietnamese Vol20\\QUÊN ĐI HẾT ĐAM MÊ_(830215).mid");
    }

    public static void playMidi(String midiFilePath) {
        try {
            // Load the MIDI file
            File midiFile = new File(midiFilePath);
            Sequence sequence = MidiSystem.getSequence(midiFile);
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.setSequence(sequence);

            // Set up a listener to highlight words as the MIDI plays
            sequencer.addMetaEventListener(event -> {
                if (event.getType() == 47) { // End of Track event
                    System.out.println("MIDI playback finished.");
                    sequencer.close();
                } else {
                    highlightNextWord();
                }
            });

            // Start playback
            sequencer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void highlightNextWord() {
        if (currentWordIndex >= lyrics.length) return; // Stop if no more words

        SwingUtilities.invokeLater(() -> {
            try {
                // Remove previous highlight
                SimpleAttributeSet defaultStyle = new SimpleAttributeSet();
                StyleConstants.setBackground(defaultStyle, Color.WHITE);
                doc.setCharacterAttributes(0, doc.getLength(), defaultStyle, false);

                // Apply new highlight
                SimpleAttributeSet highlightStyle = new SimpleAttributeSet();
                StyleConstants.setBackground(highlightStyle, Color.YELLOW);

                // Find word position
                int start = findWordPosition(currentWordIndex);
                int length = lyrics[currentWordIndex].length();

                doc.setCharacterAttributes(start, length, highlightStyle, false);
                currentWordIndex++;
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        });
    }

    public static int findWordPosition(int index) throws BadLocationException {
        String text = doc.getText(0, doc.getLength());
        String[] words = text.split("\\s+");
        int position = 0;

        for (int i = 0; i < index; i++) {
            position += words[i].length() + 1; // +1 for space
        }

        return position;
    }
}

