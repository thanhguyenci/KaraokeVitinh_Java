import javax.sound.sampled.*;

public class ListAudioFormats {
    public static void main(String[] args) {
        Mixer.Info[] mixers = AudioSystem.getMixerInfo();
        for (Mixer.Info mixerInfo : mixers) {
            Mixer mixer = AudioSystem.getMixer(mixerInfo);
            System.out.println("Mixer: " + mixerInfo.getName());

            Line.Info[] targetLines = mixer.getTargetLineInfo();
            for (Line.Info info : targetLines) {
                if (info instanceof DataLine.Info) {
                    AudioFormat[] formats = ((DataLine.Info) info).getFormats();
                    for (AudioFormat format : formats) {
                        System.out.println("Supported Format: " + format);
                    }
                }
            }
        }
    }
}
