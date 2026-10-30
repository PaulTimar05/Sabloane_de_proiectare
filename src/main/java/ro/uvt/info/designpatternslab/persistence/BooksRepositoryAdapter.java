package ro.uvt.info.designpatternslab.persistence;

import org.springframework.stereotype.Component;
import ro.uvt.info.designpatternslab.Book;

import java.util.List;
import java.util.Optional;

@Component
public class BooksRepositoryAdapter implements CrudRepository<Book, Integer> {
    private final BooksRepository repository;

    public BooksRepositoryAdapter(BooksRepository repository) {
        this.repository = repository;
    }

    @Override public Book save(Book book)                 { return repository.save(book); }
    @Override public List<Book> findAll()                 { return repository.findAll(); }
    @Override public Optional<Book> findById(Integer id)  { return repository.findById(id); }
    @Override public boolean existsById(Integer id)       { return repository.existsById(id); }
    @Override public void deleteById(Integer id)          { repository.deleteById(id); }
}