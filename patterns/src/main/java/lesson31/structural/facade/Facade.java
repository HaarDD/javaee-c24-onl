package lesson31.structural.facade;

import lesson31.structural.facade.audio.AudioProgramFacade;

public class Facade {

    public static void main(String[] args) {
        AudioProgramFacade audioProgramFacade = new AudioProgramFacade();

        audioProgramFacade.playAudio("music.mp3", 30);

        audioProgramFacade.stopAudio();
    }

}
