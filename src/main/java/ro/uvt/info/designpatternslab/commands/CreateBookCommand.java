// CreateBookCommand.java
package ro.uvt.info.designpatternslab.commands;

import ro.uvt.info.designpatternslab.Book;
import ro.uvt.info.designpatternslab.models.BookDto;
import ro.uvt.info.designpatternslab.persistence.BooksRepository;

public class CreateBookCommand implements Command<Book> {
    private final BooksRepository repository;
    private final BookDto dto;

    public CreateBookCommand(BooksRepository repository, BookDto dto) {
        this.repository = repository;
        this.dto = dto;
    }

    @Override
    public Book execute() {
        Book book = dto.toBook();
        return repository.save(book);   // salvează în baza de date și returnează cartea, acum cu id
    }
}