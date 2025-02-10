package thanh;
import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class MidiPlayerWithLyrics {
    public static void main(String[] args) {
        try {
            // Load the MIDI file
            File midiFile = new File("MIDI California Vietnamese Vol20\\RU LẠI CÂU HÒ_(828892).mid");  // Replace with your MIDI file path
            Sequence sequence = MidiSystem.getSequence(midiFile);
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.setSequence(sequence);

            // Extract and store lyrics with timestamps
            Map<Long, String> lyricsMap = new TreeMap<>();
            for (Track track : sequence.getTracks()) {
                for (int i = 0; i < track.size(); i++) {
                    MidiEvent event = track.get(i);
                    MidiMessage message = event.getMessage();
                    if (message instanceof MetaMessage) {
                        MetaMessage metaMessage = (MetaMessage) message;
                        if (metaMessage.getType() == 0x05) { // 0x05 is the MIDI Lyrics Meta Event
                            String lyrics = new String(metaMessage.getData());
                            lyricsMap.put(event.getTick(), lyrics);
                        }
                    }
                }
            }

            // Start playback
            sequencer.start();

            // Display lyrics in sync with MIDI playback
            new Thread(() -> {
                try {
                    while (sequencer.isRunning()) {
                        long tickPosition = sequencer.getTickPosition();
                        lyricsMap.entrySet().removeIf(entry -> {
                            if (entry.getKey() <= tickPosition) {
                                System.out.println(entry.getValue()); // Display lyric line
                                return true; // Remove displayed lyrics
                            }
                            return false;
                        });
                        Thread.sleep(200); // Check every 200ms
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();

            // Wait for playback to finish
            while (sequencer.isRunning()) {
                Thread.sleep(500);
            }

            sequencer.close();
        } catch (IOException | MidiUnavailableException | InvalidMidiDataException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
