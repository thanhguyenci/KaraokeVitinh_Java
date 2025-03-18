import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
public class QRCodeScanner {
    public static void main(String[] args) {
        String imagePath = "src/main/java/Screenshot 2025-03-18 205357.png"; // Replace with your QR code image path
        String decodedText = decodeQRCode(imagePath);
        if (decodedText != null) {
            System.out.println("Decoded text: " + decodedText);
        } else {
            System.out.println("Could not decode QR code.");
        }
    }
    public static String decodeQRCode(String imagePath) {
        BufferedImage bufferedImage = null;
        try {
            // Read the image from the file
            bufferedImage = ImageIO.read(new File(imagePath));
            // Convert BufferedImage to LuminanceSource
            LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
            // Convert LuminanceSource to BinaryBitmap
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            // Decode the QR code
            Result result = new MultiFormatReader().decode(bitmap);
            // Return the decoded text
            return result.getText();
        } catch (IOException | NotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}