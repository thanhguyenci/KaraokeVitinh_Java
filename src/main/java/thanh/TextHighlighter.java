package thanh;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class TextHighlighter {
    public static void main(String[] args) {
        // Create the JFrame (window)
        JFrame frame = new JFrame("Text Highlighter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);

        // Create JTextPane to display text
        JTextPane textPane = new JTextPane();
        textPane.setText("Java is a powerful programming language. Highlight important words!");

        // Highlight specific words
        highlightText(textPane, "Java", Color.YELLOW);
        highlightText(textPane, "powerful", Color.CYAN);
        highlightText(textPane, "Highlight", Color.PINK);

        // Add JTextPane inside a JScrollPane (to enable scrolling)
        frame.add(new JScrollPane(textPane));

        // Show the frame
        frame.setVisible(true);
    }

    // Method to highlight specific words
    public static void highlightText(JTextPane textPane, String word, Color color) {
        try {
            StyledDocument doc = textPane.getStyledDocument();
            String text = doc.getText(0, doc.getLength());

            int index = 0;
            while ((index = text.indexOf(word, index)) >= 0) {
                SimpleAttributeSet highlight = new SimpleAttributeSet();
                StyleConstants.setBackground(highlight, color);
                doc.setCharacterAttributes(index, word.length(), highlight, false);
                index += word.length();
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}
