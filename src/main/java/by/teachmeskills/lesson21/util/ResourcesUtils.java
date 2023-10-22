package by.teachmeskills.lesson21.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ResourcesUtils {

    public static byte[] extractBytes(String path) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(path);
            byte[] bytes = new byte[fileInputStream.available()];
            int bytesRead = fileInputStream.read(bytes);
            if (bytesRead > 0) {
                return bytes;
            } else {
                return null;
            }
        } catch (IOException e) {
            System.out.println(path);
            e.printStackTrace();
            return null;
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    System.out.println(path);
                    e.printStackTrace();
                }
            }
        }
    }
}
