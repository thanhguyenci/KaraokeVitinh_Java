import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class QRCodeGenerator {

    public static void main(String[] args) {
        // The data you want to encode in the QR code
        String qrCodeData = "Hello, QR Code!"; // Replace with your data

        // Generate the QR code
        BufferedImage qrCodeImage = generateQRCodeImage(qrCodeData, 200, 200);

        // Create a JFrame (window)
        JFrame frame = new JFrame("QR Code");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a JLabel to display the image
        ImageIcon icon = new ImageIcon(qrCodeImage);
        JLabel label = new JLabel(icon);

        // Add the label to the frame
        frame.getContentPane().add(label, BorderLayout.CENTER);

        // Adjust the frame size
        frame.pack();
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setVisible(true);
    }

    public static BufferedImage generateQRCodeImage(String text, int width, int height) {
        try {
            // Create a QRCodeWriter
            QRCodeWriter qrCodeWriter = new QRCodeWriter();

            // Set the error correction level
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

            // Encode the data into a BitMatrix
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);

            // Create a BufferedImage from the BitMatrix
            return MatrixToImageWriter.toBufferedImage(bitMatrix);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }
}