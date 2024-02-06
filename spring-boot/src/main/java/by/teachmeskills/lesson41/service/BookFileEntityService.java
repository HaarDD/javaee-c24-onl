package by.teachmeskills.lesson41.service;

import by.teachmeskills.lesson41.dto.BookFileDto;
import by.teachmeskills.lesson41.exception.ResourceNotCreatedException;
import by.teachmeskills.lesson41.exception.ResourceNotFoundException;
import by.teachmeskills.lesson41.mapper.BookFileMapper;
import by.teachmeskills.lesson41.repository.BookFileRepository;
import by.teachmeskills.lesson41.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
class BookFileEntityService {

    private final BookFileRepository bookFileRepository;

    private final BookRepository bookRepository;

    private final BookFileMapper bookFileMapper;

    public BookFileDto getBookFileByBookId(Integer bookId) {
        return bookFileMapper.toDto(bookFileRepository.getByBookId(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Запись о файле для книги с id %s не найдена!".formatted(bookId))));
    }

    public void addBookFile(BookFileDto bookFileDto, Integer bookId) {
        try {
            bookFileRepository.save(bookFileMapper.toEntity(bookFileDto).setBook(bookRepository.findById(bookId)
                    .orElseThrow(() -> new ResourceNotFoundException("Ошибка добавления записи о файле для книги с id %s!".formatted(bookId)))));
        } catch (Exception e) {
            log.warn("Запись о файле не была создана! bookFileDto: {}", bookFileDto);
            throw new ResourceNotCreatedException("Запись о файле не была создана!");
        }
    }

    @Transactional
    public void deleteBookFileByBookId(Integer bookId) {
        try {
            bookFileRepository.deleteByBookId(bookId);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Ошибка удаления записи о файле для книги с id %s!".formatted(bookId));
        }
    }

}
