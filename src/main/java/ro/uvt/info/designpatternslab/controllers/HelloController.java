package ro.uvt.info.designpatternslab.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.uvt.info.designpatternslab.difexamples.ClientComponent;

@RestController
public record HelloController(ClientComponent c1, ClientComponent c2) {
    @GetMapping("/")
    public String hello() {
        return "c1 = " + c1 + " | c2 = " + c2;
    }
}