package ro.uvt.info.proj.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ro.uvt.info.proj.Category;
import ro.uvt.info.proj.Task;
import ro.uvt.info.proj.dto.CreateCategoryRequest;
import ro.uvt.info.proj.dto.CreateTaskRequest;
import ro.uvt.info.proj.dto.SetDoneRequest;
import ro.uvt.info.proj.services.NodeService;

@RestController
public class NodesController {
    private final NodeService service;

    public NodesController(NodeService service) {
        this.service = service;
    }

    @PostMapping("/categories")
    public ResponseEntity<Category> createCategory(@RequestBody CreateCategoryRequest request) {
        if (request.title() == null || request.title().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Titlul este obligatoriu");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createCategory(request));
    }

    @PostMapping("/tasks")
    public ResponseEntity<Task> createTask(@RequestBody CreateTaskRequest request) {
        if (request.title() == null || request.title().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Titlul este obligatoriu");
        }
        if (request.estimatedMinutes() != null && request.estimatedMinutes() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Durata nu poate fi negativă");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createTask(request));
    }
    @PatchMapping("/tasks/{id}/done")
    public Task setDone(@PathVariable int id, @RequestBody SetDoneRequest request) {
        return service.setDone(id, request.done());
    }
    @DeleteMapping("/nodes/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.deleteNode(id);
        return ResponseEntity.noContent().build();
    }
}