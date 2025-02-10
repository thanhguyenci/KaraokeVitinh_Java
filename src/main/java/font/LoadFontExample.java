package font;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class LoadFontExample {
    public static void main(String[] args) {
        try {
            // Load font from a file
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("font-full/VNI-Yahoo.ttf"));

            // Derive font with a specific size
            customFont = customFont.deriveFont(24f);

            // Register the font in the Graphics Environment (optional)
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);

            System.out.println("Font loaded successfully!");

        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }
}
