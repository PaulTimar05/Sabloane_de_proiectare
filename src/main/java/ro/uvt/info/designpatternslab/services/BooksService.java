package ro.uvt.info.designpatternslab.services;

import org.springframework.stereotype.Service;
import ro.uvt.info.designpatternslab.models.BookDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class BooksService {
    private final Map<Integer, BookDto> books = new ConcurrentHashMap<>();
    private final AtomicInteger nextId = new AtomicInteger(1);

    public List<BookDto> getAll() {
        return new ArrayList<>(books.values());
    }

    public BookDto getById(int id) {
        return books.get(id);
    }

    public BookDto create(BookDto book) {
        simulateIsbnService();
        int id = nextId.getAndIncrement();
        BookDto saved = new BookDto(id, book.title(), book.author());
        books.put(id, saved);
        return saved;
    }

    public BookDto update(int id, BookDto book) {
        if (!books.containsKey(id)) {
            return null;
        }
        BookDto updated = new BookDto(id, book.title(), book.author());
        books.put(id, updated);
        return updated;
    }

    public boolean delete(int id) {
        return books.remove(id) != null;
    }

    // simulează serviciul extern care dă ISBN-ul (din Task 8: durată necunoscută)
    private void simulateIsbnService() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}