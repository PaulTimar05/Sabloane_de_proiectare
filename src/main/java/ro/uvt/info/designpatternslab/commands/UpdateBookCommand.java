// UpdateBookCommand.java
package ro.uvt.info.designpatternslab.commands;

import ro.uvt.info.designpatternslab.Book;
import ro.uvt.info.designpatternslab.models.BookDto;
import ro.uvt.info.designpatternslab.persistence.BooksRepository;
import java.util.Optional;

public class UpdateBookCommand implements Command<Book> {
    private final BooksRepository repository;
    private final int id;
    private final BookDto dto;

    public UpdateBookCommand(BooksRepository repository, int id, BookDto dto) {
        this.repository = repository;
        this.id = id;
        this.dto = dto;
    }

    @Override
    public Book execute() {
        Optional<Book> found = repository.findById(id);
        if (found.isEmpty()) {
            return null;
        }
        Book book = found.get();
        book.setTitle(dto.title());
        return repository.save(book);
    }
}