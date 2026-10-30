// DeleteBookCommand.java
package ro.uvt.info.designpatternslab.commands;

import ro.uvt.info.designpatternslab.Book;
import ro.uvt.info.designpatternslab.persistence.CrudRepository;

public class DeleteBookCommand implements Command<Boolean> {
    private final CrudRepository<Book, Integer> repository;
    private final int id;

    public DeleteBookCommand(CrudRepository<Book, Integer> repository, int id) {
        this.repository = repository;
        this.id = id;
    }

    @Override
    public Boolean execute() {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}