// GetBookByIdCommand.java
package ro.uvt.info.designpatternslab.commands;

import ro.uvt.info.designpatternslab.Book;
import ro.uvt.info.designpatternslab.persistence.BooksRepository;

public class GetBookByIdCommand implements Command<Book> {
    private final BooksRepository repository;
    private final int id;

    public GetBookByIdCommand(BooksRepository repository, int id) {
        this.repository = repository;
        this.id = id;
    }

    @Override
    public Book execute() {
        return repository.findById(id).orElse(null);
    }
}