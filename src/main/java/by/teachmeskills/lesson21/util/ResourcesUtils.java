package by.teachmeskills.lesson21.util;

import java.io.FileInputStream;
import java.io.IOException;

public class ResourcesUtils {

    public static byte[] extractBytes(String path) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(path);
        byte[] bytes = new byte[fileInputStream.available()];
        fileInputStream.read(bytes);
        fileInputStream.close();
        return bytes;
    }
}
