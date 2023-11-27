package lesson31.structural.facade.audio;

public class AudioPlayer {

    public void loadAudio(AudioFile audioFile) {
        System.out.println("Загрузка аудио: " + audioFile.fileName());
    }

    public void play() {
        System.out.println("Воспроизведение аудио");
    }

    public void stop() {
        System.out.println("Остановка воспроизведения аудио");
    }

}
