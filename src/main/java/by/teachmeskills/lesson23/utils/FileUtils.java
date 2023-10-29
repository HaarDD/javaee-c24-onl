package by.teachmeskills.lesson23.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    public static int DEFAULT_BYTE_BUFFER_SIZE = 4096;

    public static List<String> getFileNamesInDirectory(String directoryPath) {
        List<String> fileNames = new ArrayList<>();
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                fileNames.add(file.getName());
            }
            return fileNames;
        } else {
            return null;
        }
    }

    private static void writeBytesToOutputStream(InputStream inputStream, OutputStream outputStream) throws IOException {
        int bytesRead;
        byte[] buffer = new byte[DEFAULT_BYTE_BUFFER_SIZE];
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
    }

    public static void writeFileToOutputStream(IFileReadListener iFileReadListener) throws IOException {
        try (InputStream inputStream = iFileReadListener.getInputStream()) {
            if (inputStream != null) {
                iFileReadListener.fileFound();
                try (OutputStream outputStream = iFileReadListener.getOutputStream()) {
                    writeBytesToOutputStream(inputStream, outputStream);
                }
            } else {
                iFileReadListener.fileNotFound();
            }
        }
    }

    public interface IFileReadListener {

        InputStream getInputStream() throws IOException;

        OutputStream getOutputStream() throws IOException;

        void fileFound() throws IOException;

        void fileNotFound() throws IOException;

    }

}
