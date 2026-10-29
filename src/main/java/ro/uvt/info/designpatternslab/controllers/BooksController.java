package ro.uvt.info.designpatternslab.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.uvt.info.designpatternslab.commands.*;
import ro.uvt.info.designpatternslab.models.BookDto;
import ro.uvt.info.designpatternslab.services.BooksService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/books")
public class BooksController {
    private final BooksService service;
    private final SynchronousCommandExecutor syncExecutor;
    private final AsynchronousCommandExecutor asyncExecutor;

    // cererile acceptate și încă în lucru / terminate, după requestId
    private final Map<String, CompletableFuture<BookDto>> requests = new ConcurrentHashMap<>();

    public BooksController(BooksService service,
                           SynchronousCommandExecutor syncExecutor,
                           AsynchronousCommandExecutor asyncExecutor) {
        this.service = service;
        this.syncExecutor = syncExecutor;
        this.asyncExecutor = asyncExecutor;
    }

    @GetMapping
    public List<BookDto> getAll() {
        return syncExecutor.execute(new GetAllBooksCommand(service)).join();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getById(@PathVariable int id) {
        BookDto book = syncExecutor.execute(new GetBookByIdCommand(service, id)).join();
        return book == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(book);
    }

    // ASINCRON: punem comanda în coadă și răspundem imediat cu 202
    @PostMapping
    public ResponseEntity<Map<String, String>> create(@RequestBody BookDto book) {
        String requestId = UUID.randomUUID().toString();
        requests.put(requestId, asyncExecutor.execute(new CreateBookCommand(service, book)));
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(Map.of("requestId", requestId, "statusUrl", "/books/requests/" + requestId));
    }

    // clientul întreabă periodic (polling) ce s-a întâmplat cu cererea lui
    @GetMapping("/requests/{requestId}")
    public ResponseEntity<Map<String, Object>> status(@PathVariable String requestId) {
        CompletableFuture<BookDto> future = requests.get(requestId);
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
    public ResponseEntity<BookDto> update(@PathVariable int id, @RequestBody BookDto book) {
        BookDto updated = syncExecutor.execute(new UpdateBookCommand(service, id, book)).join();
        return updated == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        boolean deleted = syncExecutor.execute(new DeleteBookCommand(service, id)).join();
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}