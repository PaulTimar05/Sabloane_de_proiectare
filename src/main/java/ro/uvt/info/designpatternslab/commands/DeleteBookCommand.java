// DeleteBookCommand.java
package ro.uvt.info.designpatternslab.commands;

import ro.uvt.info.designpatternslab.persistence.BooksRepository;

public class DeleteBookCommand implements Command<Boolean> {
    private final BooksRepository repository;
    private final int id;

    public DeleteBookCommand(BooksRepository repository, int id) {
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