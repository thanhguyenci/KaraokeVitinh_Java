package thanh;
import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MidiLyricDisplay {
    public static void main(String[] args) {
        String midiFilePath = "MIDI California Vietnamese Vol20\\QUÊN ĐI HẾT ĐAM MÊ_(830215).mid"; // Change to your file path
        
        try {
            // Load MIDI file
            Sequence sequence = MidiSystem.getSequence(new File(midiFilePath));
            List<LyricEvent> lyrics = extractLyrics(sequence);
            
            // Create and show GUI
            SwingUtilities.invokeLater(() -> new LyricDisplayFrame(lyrics, sequence));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static List<LyricEvent> extractLyrics(Sequence sequence) {
        List<LyricEvent> lyrics = new ArrayList<>();
        
        for (Track track : sequence.getTracks()) {
            for (int i = 0; i < track.size(); i++) {
                MidiEvent event = track.get(i);
                MidiMessage message = event.getMessage();
                
                if (message instanceof MetaMessage) {
                    MetaMessage metaMessage = (MetaMessage) message;
                    if (metaMessage.getType() == 0x05) { // Lyric event
                        //String lyric = new String(metaMessage.getData(), StandardCharsets.ISO_8859_1);
                        //lyric = new String(lyric.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.ISO_8859_1);

                        //String lyric = new String(metaMessage.getData(), StandardCharsets.ISO_8859_1);
                        //lyric = new String(lyric.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.ISO_8859_1);

                        Charset charset = Charset.forName("windows-1252");
                        String lyric = new String(metaMessage.getData(), charset);

                        lyrics.add(new LyricEvent(lyric, event.getTick()));
                    }
                }
            }
        }
        return lyrics;
    }
}

class LyricEvent {
    String text;
    long tick;

    public LyricEvent(String text, long tick) {
        this.text = text;
        this.tick = tick;
    }
}

class LyricDisplayFrame extends JFrame {
    private JLabel lyricLabel;
    private JLabel nextLyricsLabel;
    private List<LyricEvent> lyrics;
    private Sequencer sequencer;
    
    public LyricDisplayFrame(List<LyricEvent> lyrics, Sequence sequence) {
        this.lyrics = lyrics;
        
        // Setup GUI
        setTitle("MIDI Lyric Display");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        JPanel panel = new JPanel(new GridLayout(2, 1));
        lyricLabel = new JLabel("", SwingConstants.CENTER);
        lyricLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        
        nextLyricsLabel = new JLabel("", SwingConstants.CENTER);
        nextLyricsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        
        panel.add(lyricLabel);
        panel.add(nextLyricsLabel);
        add(panel, BorderLayout.CENTER);
        
        setVisible(true);
        startMidiPlayback(sequence);
    }
    
    private void startMidiPlayback(Sequence sequence) {
        try {
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.setSequence(sequence);
            sequencer.start();
            startLyricDisplay();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void startLyricDisplay() {
        new Thread(() -> {
            while (sequencer.isRunning()) {
                long currentTick = sequencer.getTickPosition();
                for (int i = 0; i < lyrics.size(); i++) {
                    LyricEvent lyric = lyrics.get(i);
                    if (lyric.tick <= currentTick) {
                        final String currentLyric = new String(lyric.text.getBytes(), StandardCharsets.UTF_8);
                        final String nextLyrics = lyrics.subList(Math.min(i + 1, lyrics.size()), Math.min(i + 101, lyrics.size()))
                                .stream()
                                .map(l -> new String(l.text.getBytes(), StandardCharsets.UTF_8))
                                .collect(Collectors.joining(" "));
                        
                        SwingUtilities.invokeLater(() -> {
                            lyricLabel.setText(currentLyric);
                            lyricLabel.setForeground(Color.RED); // Highlight active lyric
                            nextLyricsLabel.setText("Next: " + nextLyrics);
                        });
                        lyrics.remove(i);
                        break;
                    }
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
