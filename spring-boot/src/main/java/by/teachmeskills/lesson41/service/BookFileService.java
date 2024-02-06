package by.teachmeskills.lesson41.service;

import by.teachmeskills.lesson41.dto.BookFileDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookFileService {

    private final BookFileEntityService bookFileEntityService;

    private final String bookFilesStoragePath;

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS");

    public Resource getBookFileResourceByBookId(Integer bookId) {
        String filePath = bookFilesStoragePath + bookFileEntityService.getBookFileByBookId(bookId).getFileKey();
        try {
            return new UrlResource(Paths.get(filePath).toUri());
        } catch (MalformedURLException e) {
            log.error("Ошибка выдачи файла: ", e);
            throw new RuntimeException(e);
        }
    }

    public void uploadBookFileByBookId(MultipartFile file, Integer bookId) {
        try {
            Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());

            String fileKey = sdf.format(timestamp) + file.getOriginalFilename();

            String filePath = bookFilesStoragePath + fileKey;

            Path path = Paths.get(filePath);

            Files.write(path, file.getBytes());

            log.info("Файл записан: {}", path);

            BookFileDto bookFileDto = new BookFileDto()
                    .setFileName(file.getOriginalFilename())
                    .setFileSize((int) file.getSize())
                    .setFileKey(fileKey)
                    .setUploadDate(timestamp);

            bookFileEntityService.addBookFile(bookFileDto, bookId);

            log.info("Файл отмечен в базе данных: {}", fileKey);

        } catch (Exception e) {
            log.error("Ошибка записи файла: ", e);
        }
    }

    public void removeBookFileByBookId(Integer bookId) {
        String fileKey = bookFileEntityService.getBookFileByBookId(bookId).getFileKey();
        String filePath = bookFilesStoragePath + fileKey;

        Path path = Paths.get(filePath);
        try {
            Files.delete(path);
            log.info("Файл удален: {}", path);
        } catch (IOException e) {
            log.error("Ошибка удаления файла: ", e);
        }

        bookFileEntityService.deleteBookFileByBookId(bookId);
        log.info("Файл удален из базы в базе данных: {}", fileKey);
    }

}
