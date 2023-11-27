package lesson31.structural.adapter.graphics_clients;

import lesson31.structural.adapter.graphics_libraries.LegacyGraphicsLibrary;

public class LegacyGraphicsClient {

    private final LegacyGraphicsLibrary legacyGraphicsLibrary;

    public LegacyGraphicsClient(LegacyGraphicsLibrary legacyGraphics) {
        this.legacyGraphicsLibrary = legacyGraphics;
    }

    public void drawMenu() {
        legacyGraphicsLibrary.drawSquare(1, 1, 100, 50);
    }

}
