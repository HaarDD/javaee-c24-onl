package lesson31.structural.facade.audio;

public class AudioProgramFacade {

    private final AudioPlayer audioPlayer;
    private final Speaker speaker;

    public AudioProgramFacade() {
        this.audioPlayer = new AudioPlayer();
        this.speaker = new Speaker();
    }

    public void playAudio(String fileName, int volume) {
        System.out.println("Подготовка к воспроизведению...");

        AudioFile audioFile = new AudioFile(fileName);
        audioPlayer.loadAudio(audioFile);
        audioPlayer.play();
        speaker.setVolume(volume);

        System.out.println("Воспроизведение началось");
    }

    public void stopAudio() {
        System.out.println("Воспроизведение аудио остановлено");
        audioPlayer.stop();
    }

}
