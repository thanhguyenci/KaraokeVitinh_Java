package mic;

import javax.sound.sampled.*;
import java.io.*;
import java.util.Scanner;

public class MicToMp3Recorder {

    // Define the audio format for capture (PCM)
    private static final AudioFormat PCM_FORMAT = new AudioFormat(
            AudioFormat.Encoding.PCM_SIGNED, // Encoding
            44100.0F,                        // Sample Rate
            16,                              // Sample Size in bits
            2,                               // Channels (2=stereo)
            4,                               // Frame Size (bytes per frame)
            44100.0F,                        // Frame Rate
            false                            // Big Endian? (false=little endian)
    );

    // Define the target audio format (MP3)
    // Note: Bitrate, quality settings might be handled by the SPI implementation
    private static final AudioFormat MP3_FORMAT = new AudioFormat(
            // MPEG1 Layer 3 encoding
            // Using a common placeholder - the SPI might adjust details
            // Or use specific classes from the SPI if available, e.g., Tritonus's MPEGFormat
            new AudioFormat.Encoding("MPEG1L3"),
            PCM_FORMAT.getSampleRate(),      // Sample Rate (match source)
            AudioSystem.NOT_SPECIFIED,       // Sample Size in bits (MP3 doesn't use fixed bits like PCM)
            PCM_FORMAT.getChannels(),        // Channels (match source)
            AudioSystem.NOT_SPECIFIED,       // Frame Size (variable in MP3)
            AudioSystem.NOT_SPECIFIED,       // Frame Rate (N/A for MP3)
            false                            // Big Endian? (irrelevant for MP3 format spec)
    );


    private TargetDataLine microphoneLine;
    private volatile boolean recording = false; // volatile for thread safety
    private Thread recordingThread;
    private String outputFilePath;

    /**
     * Starts the audio capture process.
     *
     * @param mp3FilePath Path where the final MP3 file should be saved.
     * @throws LineUnavailableException If the microphone line cannot be opened.
     * @throws IOException If there's an I/O error during setup.
     */
    public void startRecording(String mp3FilePath) throws LineUnavailableException, IOException {
        if (recording) {
            System.out.println("Already recording.");
            return;
        }

        this.outputFilePath = mp3FilePath;

        DataLine.Info info = new DataLine.Info(TargetDataLine.class, PCM_FORMAT);
        if (!AudioSystem.isLineSupported(info)) {
            System.err.println("PCM format not supported: " + PCM_FORMAT);
            // You might want to try other common PCM formats here
            throw new LineUnavailableException("PCM format not supported: " + PCM_FORMAT);
        }

        // Check if MP3 conversion is supported *before* starting capture
        if (!AudioSystem.isConversionSupported(MP3_FORMAT, PCM_FORMAT)) {
            System.err.println("MP3 conversion not supported for format: " + MP3_FORMAT);
            System.err.println("Ensure MP3 SPI libraries (like tritonus-mp3) are in the classpath.");
            // Depending on requirements, you might throw an exception here
            // or proceed to record WAV and suggest external conversion.
            // For this example, we'll throw an error.
            throw new UnsupportedOperationException("MP3 encoding not supported. Check SPI libraries.");
        }


        microphoneLine = (TargetDataLine) AudioSystem.getLine(info);
        microphoneLine.open(PCM_FORMAT, microphoneLine.getBufferSize());
        microphoneLine.start(); // Start capturing data into the buffer

        recording = true;

        // Create a separate thread to read data and write to the file
        recordingThread = new Thread(() -> {
            try (AudioInputStream pcmAudioStream = new AudioInputStream(microphoneLine)) {
                // Get an MP3 stream from the PCM stream
                AudioInputStream mp3AudioStream = AudioSystem.getAudioInputStream(MP3_FORMAT, pcmAudioStream);

                // Write the MP3 stream to the file
                File outputFile = new File(outputFilePath);
                System.out.println("Recording to: " + outputFile.getAbsolutePath());

                // AudioSystem.write requires the file type
                AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE; // Or determine dynamically if needed

                // Write the stream to the file
                int bytesWritten = AudioSystem.write(mp3AudioStream, fileType, outputFile);

                if (recording) { // Check if stopped prematurely
                    System.out.println("Recording finished. Bytes written: " + bytesWritten);
                } else {
                    System.out.println("Recording stopped. Bytes written: " + bytesWritten);
                }

                // Close streams (handled by try-with-resources for pcmAudioStream)
                mp3AudioStream.close();

            } catch (IOException e) {
                System.err.println("Error during recording or writing file: " + e.getMessage());
                e.printStackTrace();
            } finally {
                // Ensure the line is closed even if errors occur
                stopMicrophoneLine();
                recording = false; // Ensure state is updated
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
        recording = false; // Signal the thread to stop

        // Important: Stop the line *after* setting recording = false
        // This allows the thread to finish reading buffered data
        stopMicrophoneLine();

        // Wait for the recording thread to finish writing
        try {
            if (recordingThread != null) {
                recordingThread.join(5000); // Wait up to 5 seconds
                if (recordingThread.isAlive()) {
                    System.err.println("Warning: Recording thread did not finish cleanly.");
                    // Consider interrupting if needed, but it might corrupt the file
                    // recordingThread.interrupt();
                }
            }
        } catch (InterruptedException e) {
            System.err.println("Interrupted while waiting for recording thread: " + e.getMessage());
            Thread.currentThread().interrupt(); // Restore interrupt status
        }
        System.out.println("Recording stopped.");
    }

    /** Safely stops and closes the microphone line. */
    private void stopMicrophoneLine() {
        if (microphoneLine != null) {
            if (microphoneLine.isRunning()) {
                microphoneLine.stop(); // Stop capturing data
            }
            if (microphoneLine.isOpen()) {
                microphoneLine.close(); // Release system resources
            }
            System.out.println("Microphone line closed.");
        }
    }

    public boolean isRecording() {
        return recording;
    }

    // --- Example Usage ---
    public static void main(String[] args) {
        MicToMp3Recorder recorder = new MicToMp3Recorder();
        String fileName = "recording.mp3"; // Output file name

        try {
            recorder.startRecording(fileName);

            System.out.println("Press ENTER to stop recording...");
            try (Scanner scanner = new Scanner(System.in)) {
                scanner.nextLine(); // Wait for user input
            }


            recorder.stopRecording();

        } catch (LineUnavailableException e) {
            System.err.println("Microphone line unavailable: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
            e.printStackTrace();
        } catch (UnsupportedOperationException e) {
            System.err.println("Error: " + e.getMessage());
            // Handle the case where MP3 encoding isn't supported
        }
    }
}