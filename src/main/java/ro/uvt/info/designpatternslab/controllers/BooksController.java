package ro.uvt.info.designpatternslab.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.uvt.info.designpatternslab.Book;
import ro.uvt.info.designpatternslab.commands.*;
import ro.uvt.info.designpatternslab.models.BookDto;
import ro.uvt.info.designpatternslab.persistence.CrudRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import ro.uvt.info.designpatternslab.observer.AllBooksSubject;

@RestController
@RequestMapping("/books")

public class BooksController {
    private final CrudRepository repository;
    private final SynchronousCommandExecutor syncExecutor;
    private final AsynchronousCommandExecutor asyncExecutor;

    private final Map<String, CompletableFuture<Book>> requests = new ConcurrentHashMap<>();
    private final AllBooksSubject allBooksSubject;

    public BooksController(CrudRepository<Book, Integer> repository,
                           SynchronousCommandExecutor syncExecutor,
                           AsynchronousCommandExecutor asyncExecutor,
                           AllBooksSubject allBooksSubject) {
        this.repository = repository;
        this.syncExecutor = syncExecutor;
        this.asyncExecutor = asyncExecutor;
        this.allBooksSubject = allBooksSubject;
    }

    @GetMapping
    public List<Book> getAll() {
        return syncExecutor.execute(new GetAllBooksCommand(repository)).join();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getById(@PathVariable int id) {
        Book book = syncExecutor.execute(new GetBookByIdCommand(repository, id)).join();
        return book == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(book);
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> create(@RequestBody BookDto dto) {
        String requestId = UUID.randomUUID().toString();
        CompletableFuture<Book> future = asyncExecutor.execute(new CreateBookCommand(repository, dto));
        future.thenAccept(allBooksSubject::add);   // când cartea e salvată, anunțăm abonații
        requests.put(requestId, future);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(Map.of("requestId", requestId, "statusUrl", "/books/requests/" + requestId));
    }

    @GetMapping("/requests/{requestId}")
    public ResponseEntity<Map<String, Object>> status(@PathVariable String requestId) {
        CompletableFuture<Book> future = requests.get(requestId);
        if (future == null) {
            return ResponseEntity.notFound().build();
        }
        Map<String, Object> body = new HashMap<>();
        if (future.isDone()) {
            body.put("status", "DONE");
            body.put("book", future.join());
        } else {
            body.put("status", "PENDING");
        }
        return ResponseEntity.ok(body);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> update(@PathVariable int id, @RequestBody BookDto dto) {
        Book updated = syncExecutor.execute(new UpdateBookCommand(repository, id, dto)).join();
        return updated == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        boolean deleted = syncExecutor.execute(new DeleteBookCommand(repository, id)).join();
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

}
