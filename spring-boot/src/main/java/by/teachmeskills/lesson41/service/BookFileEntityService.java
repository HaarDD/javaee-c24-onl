package by.teachmeskills.lesson41.service;

import by.teachmeskills.lesson41.dto.BookFileDto;
import by.teachmeskills.lesson41.exception.ResourceNotCreatedException;
import by.teachmeskills.lesson41.exception.ResourceNotFoundException;
import by.teachmeskills.lesson41.mapper.BookFileMapper;
import by.teachmeskills.lesson41.repository.HibernateBookFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
class BookFileEntityService {

    private final HibernateBookFileRepository bookFileRepository;

    private final BookFileMapper bookFileMapper;

    public BookFileDto getBookFileByBookId(Integer bookId) {
        return bookFileMapper.toDto(bookFileRepository.getByBookId(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Запись о файле для книги с id %s не найдена!".formatted(bookId))));
    }

    @Transactional
    public void addBookFile(BookFileDto bookFileDto) {
        bookFileRepository.add(bookFileMapper.toEntity(bookFileDto))
                .orElseThrow(() -> new ResourceNotCreatedException("Ошибка создания записи о файле книги!", bookFileDto));
    }

    @Transactional
    public void deleteBookFileByBookId(Integer bookId) {
        bookFileRepository.deleteByBookId(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Ошибка удаления, запись о файле для книги с id %s не найдена!".formatted(bookId)));
    }

}
