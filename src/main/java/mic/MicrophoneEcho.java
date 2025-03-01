package mic;

import javax.sound.sampled.*;
import java.util.Arrays;

public class MicrophoneEcho {
    public static void main(String[] args) {
        // Define the audio format (Sample Rate: 44100 Hz, Sample Size: 16-bit, Channels: Stereo, Signed, Big-Endian)
        AudioFormat format = new AudioFormat(44100.0f, 16, 2, true, true);

        // Variables for echo effect
        int echoDelayMs = 500; // Echo delay in milliseconds
        float echoDecay = 0.6f; // Echo volume decay factor

        try {
            // Get and open the microphone input line
            TargetDataLine microphone = AudioSystem.getTargetDataLine(format);
            // Get the output line
            SourceDataLine speakers = AudioSystem.getSourceDataLine(format);

            microphone.open(format);
            speakers.open(format);

            microphone.start();
            speakers.start();

            System.out.println("Microphone is open. Start speaking with an echo...");

            // Buffer for capturing audio data
            byte[] buffer = new byte[4096];
            int bytesRead;

            // Echo circular buffer
            int delayBufferSize = (int) ((format.getSampleRate() * format.getFrameSize()) * (echoDelayMs / 1000.0));
            byte[] delayBuffer = new byte[delayBufferSize];
            int delayBufferIndex = 0;

            // Real-time processing
            while (true) {
                bytesRead = microphone.read(buffer, 0, buffer.length);

                // Apply echo effect
                for (int i = 0; i < bytesRead; i++) {
                    int delayIndex = (delayBufferIndex + delayBuffer.length - bytesRead + i) % delayBuffer.length;
                    byte originalSample = buffer[i];
                    byte delayedSample = delayBuffer[delayIndex];

                    int echoedSample = (int) (originalSample + (delayedSample * echoDecay));
                    echoedSample = Math.max(Byte.MIN_VALUE, Math.min(Byte.MAX_VALUE, echoedSample)); // Clip

                    // Write to the delay buffer
                    delayBuffer[delayBufferIndex] = (byte) echoedSample;
                    delayBufferIndex = (delayBufferIndex + 1) % delayBuffer.length;

                    // Write back to buffer for playback
                    buffer[i] = (byte) echoedSample;
                }

                // Play the modified buffer
                speakers.write(buffer, 0, bytesRead);
            }
        } catch (LineUnavailableException e) {
            System.err.println("Microphone or speaker line is unavailable.");
            e.printStackTrace();
        }
    }
}