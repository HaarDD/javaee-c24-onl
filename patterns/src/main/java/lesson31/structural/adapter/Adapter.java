package lesson31.structural.adapter;

import lesson31.structural.adapter.graphics_clients.LegacyGraphicsClient;
import lesson31.structural.adapter.graphics_libraries.LegacyGraphicsLibrary;
import lesson31.structural.adapter.graphics_libraries.ModernGraphicsLibrary;
import lesson31.structural.adapter.graphics_adapters.ModernToLegacyGraphicsAdapter;

public class Adapter {

    public static void main(String[] args) {
        LegacyGraphicsLibrary legacyGraphicsLibrary = new LegacyGraphicsLibrary();
        ModernGraphicsLibrary modernGraphicsLibrary = new ModernGraphicsLibrary();

        LegacyGraphicsLibrary modernToLegacyGraphicsAdapter = new ModernToLegacyGraphicsAdapter(modernGraphicsLibrary);

        LegacyGraphicsClient legacyGraphicsClient = new LegacyGraphicsClient(legacyGraphicsLibrary);

        //Рисуем меню через legacy
        legacyGraphicsClient.drawMenu();

        //Рисуем меню методами из legacy, но реализация методов будет из Modern библиотеки
        legacyGraphicsClient = new LegacyGraphicsClient(modernToLegacyGraphicsAdapter);
        legacyGraphicsClient.drawMenu();
    }

}
