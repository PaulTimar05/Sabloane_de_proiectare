package ro.uvt.info.designpatternslab.commands;

import ro.uvt.info.designpatternslab.Book;
import ro.uvt.info.designpatternslab.persistence.CrudRepository;

import java.util.List;

public class GetAllBooksCommand implements Command<List<Book>> {
    private final CrudRepository<Book, Integer> repository;

    public GetAllBooksCommand(CrudRepository<Book, Integer> repository) {
        this.repository = repository;
    }

    @Override
    public List<Book> execute() {
        return repository.findAll();
    }
}