package ro.uvt.info.designpatternslab.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.uvt.info.designpatternslab.Book;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
}