package font;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FontLoaderTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Font Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 300);
            frame.setLayout(new FlowLayout());

            JLabel label = new JLabel("Quên Đi Hết Đam Mê Custom Font Test");
            label.setFont(loadFont("font-full/JumpstartKindergartenVietnam.ttf", 24f)); // Change the path

            frame.add(label);
            frame.setVisible(true);
        });
    }

    public static Font loadFont(String fontPath, float size) {
        try {
            // Load custom font from file
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File(fontPath));
            customFont = customFont.deriveFont(size); // Set the font size

            // Register the font in the system (optional)
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);

            System.out.println("Custom font loaded: " + customFont.getFontName());
            return customFont;
        } catch (IOException | FontFormatException e) {
            System.err.println("Error loading font. Using default font.");
            e.printStackTrace();
            return new Font("Serif", Font.PLAIN, (int) size); // Fallback font
        }
    }
}
