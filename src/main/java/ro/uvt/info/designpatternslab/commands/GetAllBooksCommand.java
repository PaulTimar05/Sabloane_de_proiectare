// GetAllBooksCommand.java
package ro.uvt.info.designpatternslab.commands;

import ro.uvt.info.designpatternslab.Book;
import ro.uvt.info.designpatternslab.persistence.BooksRepository;
import java.util.List;

public class GetAllBooksCommand implements Command<List<Book>> {
    private final BooksRepository repository;

    public GetAllBooksCommand(BooksRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Book> execute() {
        return repository.findAll();
    }
}