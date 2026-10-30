// GetBookByIdCommand.java
package ro.uvt.info.designpatternslab.commands;

import ro.uvt.info.designpatternslab.Book;
import ro.uvt.info.designpatternslab.persistence.CrudRepository;

public class GetBookByIdCommand implements Command<Book> {
    private final CrudRepository<Book, Integer> repository;
    private final int id;

    public GetBookByIdCommand(CrudRepository<Book, Integer> repository, int id) {
        this.repository = repository;
        this.id = id;
    }

    @Override
    public Book execute() {
        return repository.findById(id).orElse(null);
    }
}