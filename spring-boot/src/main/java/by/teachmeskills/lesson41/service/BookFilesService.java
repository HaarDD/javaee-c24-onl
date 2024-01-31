package by.teachmeskills.lesson41.service;

import by.teachmeskills.lesson41.entity.BookFileEntity;
import by.teachmeskills.lesson41.exception.ResourceNotFoundException;
import by.teachmeskills.lesson41.repository.HibernateBookFileRepository;
import by.teachmeskills.lesson41.repository.HibernateBookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
@Transactional(readOnly = true)
@Slf4j
public class BookFilesService {

    private final HibernateBookFileRepository bookFileRepository;

    private final HibernateBookRepository bookRepository;

    private final String bookFilesStoragePath;

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS");

    public Resource getBookFileResourceByBookId(Integer bookId) {
        String filePath = bookFilesStoragePath + bookFileRepository.getByBookId(bookId).orElseThrow().getFileKey();
        try {
            return new UrlResource(Paths.get(filePath).toUri());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void uploadBookFileByBookId(MultipartFile file, Integer bookId) {
        try {
            Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());

            String fileKey = sdf.format(timestamp) + file.getOriginalFilename();

            String filePath = bookFilesStoragePath + fileKey;

            Path path = Paths.get(filePath);

            Files.write(path, file.getBytes());
            log.info("Файл записан: {}", path);

            BookFileEntity bookFileEntity = new BookFileEntity()
                    .setFileName(file.getOriginalFilename())
                    .setFileSize((int) file.getSize())
                    .setFileKey(fileKey)
                    .setUploadDate(timestamp)
                    .setBook(bookRepository.getById(bookId)
                            .orElseThrow(() -> new ResourceNotFoundException("Книга с id %s не найдена!".formatted(bookId))));

            bookFileRepository.add(bookFileEntity);
            log.info("Файл отмечен в базе данных: {}", fileKey);

        } catch (Exception e) {
            log.error("Ошибка записи файла: ", e);
        }
    }

    @Transactional
    public void removeBookFileByBookId(Integer bookId) {
        String filePath = bookFilesStoragePath + bookFileRepository.getByBookId(bookId)
                .orElseThrow((() -> new ResourceNotFoundException("Файла для книги с id %s не найдено!".formatted(bookId)))).getFileKey();

        Path path = Paths.get(filePath);
        try {
            Files.delete(path);

        } catch (IOException e) {
            log.error("Ошибка удаления файла: ", e);
        }

        bookFileRepository.deleteByBookId(bookId);
    }

}
