package ro.uvt.info.designpatternslab.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import ro.uvt.info.designpatternslab.observer.AllBooksSubject;
import ro.uvt.info.designpatternslab.observer.SseObserver;

@RestController
public class BooksSseController {
    private final AllBooksSubject allBooksSubject;

    public BooksSseController(AllBooksSubject allBooksSubject) {
        this.allBooksSubject = allBooksSubject;
    }

    @GetMapping("/books-sse")
    public SseEmitter getBooksSse() {
        SseEmitter emitter = new SseEmitter(0L);   // 0L = fără timeout, conexiunea rămâne deschisă
        allBooksSubject.attach(new SseObserver(emitter));
        return emitter;
    }
}