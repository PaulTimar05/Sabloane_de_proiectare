package ro.uvt.info.proj.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ro.uvt.info.proj.Category;
import ro.uvt.info.proj.Task;
import ro.uvt.info.proj.dto.CreateCategoryRequest;
import ro.uvt.info.proj.dto.CreateTaskRequest;
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
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createTask(request));
    }
}