package ro.uvt.info.proj.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ro.uvt.info.proj.Node;
import ro.uvt.info.proj.services.NodeService;

import java.util.List;

@RestController
public class TreeController {
    private final NodeService service;

    public TreeController(NodeService service) {
        this.service = service;
    }

    @GetMapping("/nodes")
    public List<Node> getTree() {
        return service.getRoots();
    }

    @GetMapping("/nodes/{id}")
    public Node getSubtree(@PathVariable int id) {
        return service.getNode(id);
    }
}