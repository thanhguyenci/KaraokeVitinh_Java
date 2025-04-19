package thanh;

import org.junit.jupiter.api.Test;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import java.io.File;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class MidiLyricDisplayTest {

    @Test
    void testExtractLyrics() throws Exception {
        // Arrange
        String midiFilePath = "California Vol 18\\QUÊN ĐI HẾT ĐAM MÊ_(830215).mid"; // Replace with your MIDI file path
        File midiFile = new File(midiFilePath);
        assertTrue(midiFile.exists(), "MIDI file does not exist: " + midiFilePath);

        // Act
        Sequence sequence = MidiSystem.getSequence(midiFile);
        List<LyricEvent> lyrics = extractLyrics(sequence);

        // Assert
        assertNotNull(lyrics, "Lyrics list should not be null");
        assertFalse(lyrics.isEmpty(), "Lyrics list should not be empty");

        // You can add more specific assertions here, e.g.,
        // assertEquals("Expected Lyric", lyrics.get(0).text);
        // assertTrue(lyrics.get(0).tick > 0);
        // ...
        System.out.println("Lyrics extracted successfully. Number of lyrics: " + lyrics.size());
        for (LyricEvent lyric : lyrics) {
            System.out.println("Lyric: " + lyric.text + ", Tick: " + lyric.tick);
        }
    }

    private List<LyricEvent> extractLyrics(Sequence sequence) {
        List<LyricEvent> lyrics = new java.util.ArrayList<>();

        for (javax.sound.midi.Track track : sequence.getTracks()) {
            for (int i = 0; i < track.size(); i++) {
                javax.sound.midi.MidiEvent event = track.get(i);
                javax.sound.midi.MidiMessage message = event.getMessage();

                if (message instanceof javax.sound.midi.MetaMessage) {
                    javax.sound.midi.MetaMessage metaMessage = (javax.sound.midi.MetaMessage) message;
                    if (metaMessage.getType() == 0x05) { // Lyric event
                        java.nio.charset.Charset charset = java.nio.charset.Charset.forName("windows-1252");
                        String lyric = new String(metaMessage.getData(), charset);
                        lyrics.add(new LyricEvent(lyric, event.getTick()));
                    }
                }
            }
        }
        return lyrics;
    }
}