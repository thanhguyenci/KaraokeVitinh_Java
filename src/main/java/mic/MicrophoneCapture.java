package mic;

import javax.sound.sampled.*;

public class MicrophoneCapture {
    public static void main(String[] args) {
        // Define the audio format
        AudioFormat format = new AudioFormat(44100.0f, 16, 2, true, true);

        // Get and open the target data line (microphone input)
        try (TargetDataLine microphone = AudioSystem.getTargetDataLine(format)) {
            // Open the line with the specified format
            microphone.open(format);
            microphone.start();

            System.out.println("Microphone is open. Start speaking...");

            // Buffer for storing audio data
            byte[] buffer = new byte[4096]; // 4 KB buffer
            int bytesRead;

            // Capture audio data for 10 seconds
            long startTime = System.currentTimeMillis();
            while (System.currentTimeMillis() - startTime < 10 * 1000) { // 10 seconds
                bytesRead = microphone.read(buffer, 0, buffer.length);
                System.out.println("Bytes read from microphone: " + bytesRead);
                // Optional: Process audio data from the buffer
                // For example, save it or analyze it
            }

            System.out.println("Finished capturing audio.");
        } catch (LineUnavailableException ex) {
            System.err.println("Microphone is unavailable or audio line cannot be opened.");
            ex.printStackTrace();
        }
    }
}
