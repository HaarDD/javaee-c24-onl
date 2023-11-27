package lesson31.structural.adapter.graphics_adapters;

import lesson31.structural.adapter.graphics_libraries.LegacyGraphicsLibrary;
import lesson31.structural.adapter.graphics_libraries.ModernGraphicsLibrary;

public class ModernToLegacyGraphicsAdapter extends LegacyGraphicsLibrary {
    private ModernGraphicsLibrary modernGraphicsLibrary;

    public ModernToLegacyGraphicsAdapter(ModernGraphicsLibrary modernGraphics) {
        this.modernGraphicsLibrary = modernGraphics;
    }

    @Override
    public void drawSquare(int x1, int y1, int x2, int y2) {
        modernGraphicsLibrary.drawSquare(x1, y2, x2 - x1, y2 - y1);
    }
}
