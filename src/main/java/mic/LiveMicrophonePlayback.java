package mic;

import javax.sound.sampled.*;

public class LiveMicrophonePlayback {
    public static void main(String[] args) {
        // Define an audio format (must be compatible with both input and output)
        AudioFormat format = new AudioFormat(44100.0f, 16, 2, true, true);

        try {
            // Get the input line (microphone)
            DataLine.Info micInfo = new DataLine.Info(TargetDataLine.class, format);
            if (!AudioSystem.isLineSupported(micInfo)) {
                System.err.println("Microphone format not supported.");
                return;
            }
            TargetDataLine microphone = (TargetDataLine) AudioSystem.getLine(micInfo);
            microphone.open(format);
            microphone.start();

            // Get the output line (speakers)
            DataLine.Info speakerInfo = new DataLine.Info(SourceDataLine.class, format);
            if (!AudioSystem.isLineSupported(speakerInfo)) {
                System.err.println("Speaker format not supported.");
                return;
            }
            SourceDataLine speakers = (SourceDataLine) AudioSystem.getLine(speakerInfo);
            speakers.open(format);
            speakers.start();

            System.out.println("Listening to the microphone with playback...");

            // Buffer for transferring from microphone to speakers
            byte[] buffer = new byte[1024]; // A 4 kB buffer for audio data
            int bytesRead;

            // Infinite loop for real-time playback
            while (true) {
                // Read from the microphone
                bytesRead = microphone.read(buffer, 0, buffer.length);
                // Write to the speakers
                speakers.write(buffer, 0, bytesRead);
            }

        } catch (LineUnavailableException ex) {
            System.err.println("Audio line unavailable: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}