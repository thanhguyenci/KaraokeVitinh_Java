package mic; // Keep your package declaration

import javax.sound.sampled.*;
import java.io.*;
import java.util.Scanner;

public class MicToWavRecorder { // Renamed class for clarity

    // Define the audio format for capture (PCM - standard for WAV)
    private static final AudioFormat PCM_FORMAT = new AudioFormat(
            AudioFormat.Encoding.PCM_SIGNED, // Encoding
            44100.0F,                        // Sample Rate (e.g., 44.1 kHz)
            16,                              // Sample Size in bits (e.g., 16-bit)
            2,                               // Channels (1=mono, 2=stereo)
            4,                               // Frame Size (bytes per frame: sample size in bits / 8 * channels)
            44100.0F,                        // Frame Rate (same as sample rate)
            false                            // Big Endian? (false=little endian, common for WAV)
    );

    private TargetDataLine microphoneLine;
    private volatile boolean recording = false; // volatile for thread safety
    private Thread recordingThread;
    private String outputFilePath;

    /**
     * Starts the audio capture process and saves to a WAV file.
     *
     * @param wavFilePath Path where the final WAV file should be saved.
     * @throws LineUnavailableException If the microphone line cannot be opened or the format is not supported.
     * @throws IOException If there's an I/O error during setup.
     */
    public void startRecording(String wavFilePath) throws LineUnavailableException, IOException {
        if (recording) {
            System.out.println("Already recording.");
            return;
        }

        this.outputFilePath = wavFilePath;

        DataLine.Info info = new DataLine.Info(TargetDataLine.class, PCM_FORMAT);
        if (!AudioSystem.isLineSupported(info)) {
            System.err.println("PCM format not supported by microphone line: " + PCM_FORMAT);
            // You might want to try other common PCM formats here (e.g., different sample rates, mono)
            throw new LineUnavailableException("PCM format not supported: " + PCM_FORMAT);
        }

        // --- MP3 specific check removed ---

        microphoneLine = (TargetDataLine) AudioSystem.getLine(info);
        // Consider setting buffer size explicitly if needed, otherwise use default
        microphoneLine.open(PCM_FORMAT, microphoneLine.getBufferSize());
        microphoneLine.start(); // Start capturing data into the line's buffer

        recording = true;

        // Create a separate thread to read data and write to the file
        recordingThread = new Thread(() -> {
            // The AudioInputStream is created directly from the microphone line
            try (AudioInputStream audioStream = new AudioInputStream(microphoneLine)) {

                File outputFile = new File(outputFilePath);
                System.out.println("Recording to: " + outputFile.getAbsolutePath());

                // --- No conversion to MP3 needed ---

                // Define the file type as WAVE
                AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;

                // Write the PCM audio stream directly to the WAVE file
                int bytesWritten = AudioSystem.write(audioStream, fileType, outputFile);

                // Check if stopped manually or ran until completion (less likely for mic capture)
                if (!recording) {
                    System.out.println("Recording stopped. Bytes written: " + bytesWritten);
                } else {
                    // This might not be reached if recording is stopped externally
                    System.out.println("Recording finished (unexpectedly?). Bytes written: " + bytesWritten);
                }

                // audioStream is closed by try-with-resources

            } catch (IOException e) {
                // Log error without stopping the finally block
                System.err.println("Error during recording or writing WAV file: " + e.getMessage());
                e.printStackTrace();
            } finally {
                // Ensure the line is stopped and closed regardless of errors or how the thread exits
                stopMicrophoneLine(); // Stop reading data
                recording = false; // Ensure state reflects that recording stopped
            }
        }, "AudioRecorder Thread");

        recordingThread.start();
        System.out.println("Recording started...");
    }

    /**
     * Stops the audio capture.
     */
    public void stopRecording() {
        if (!recording) {
            System.out.println("Not currently recording.");
            return;
        }

        System.out.println("Stopping recording...");
        recording = false; // Signal the thread to stop processing data from the stream

        // Stop the line AFTER setting recording = false.
        // This prevents the AudioInputStream from blocking indefinitely if stop() is called
        // before the flag is set, and allows any buffered data to potentially be written.
        stopMicrophoneLine();

        // Wait for the recording thread to finish writing remaining buffer and close file
        try {
            if (recordingThread != null) {
                recordingThread.join(5000); // Wait up to 5 seconds for the thread to die
                if (recordingThread.isAlive()) {
                    System.err.println("Warning: Recording thread did not terminate cleanly after stop request.");
                    // Consider interrupting if absolutely necessary, but it risks corrupting the file end
                    // recordingThread.interrupt();
                }
            }
        } catch (InterruptedException e) {
            System.err.println("Interrupted while waiting for recording thread to finish: " + e.getMessage());
            Thread.currentThread().interrupt(); // Preserve interrupt status
        }
        System.out.println("Recording stopped.");
    }

    /** Safely stops and closes the microphone line. */
    private void stopMicrophoneLine() {
        // Use synchronized block if multiple threads could potentially call this,
        // though in this design, it's mainly called from stopRecording and the recording thread's finally block.
        synchronized (this) {
            if (microphoneLine != null) {
                if (microphoneLine.isRunning()) {
                    microphoneLine.stop(); // Stop capturing data into the internal buffer
                    System.out.println("Microphone line stopped.");
                }
                if (microphoneLine.isOpen()) {
                    microphoneLine.close(); // Release system resources
                    System.out.println("Microphone line closed.");
                }
                // Set to null to prevent reuse attempts after closing
                // microphoneLine = null; // Optional: depends if you might restart later
            }
        }
    }

    public boolean isRecording() {
        return recording;
    }

    // --- Example Usage ---
    public static void main(String[] args) {
        MicToWavRecorder recorder = new MicToWavRecorder(); // Use the renamed class
        String fileName = "recording.wav"; // Output file name changed to .wav

        try {
            recorder.startRecording(fileName);

            System.out.println("Press ENTER to stop recording...");
            try (Scanner scanner = new Scanner(System.in)) {
                scanner.nextLine(); // Wait for user input
            }

            recorder.stopRecording();

        } catch (LineUnavailableException e) {
            System.err.println("Microphone line unavailable or format not supported: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("I/O error during recording setup or file writing: " + e.getMessage());
            e.printStackTrace();
        }
        // Removed UnsupportedOperationException catch block as it was for MP3 conversion
    }
}