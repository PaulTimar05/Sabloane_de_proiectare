package ro.uvt.info.proj.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ro.uvt.info.proj.Category;
import ro.uvt.info.proj.Node;
import ro.uvt.info.proj.Task;
import ro.uvt.info.proj.dto.CreateCategoryRequest;
import ro.uvt.info.proj.dto.CreateTaskRequest;
import ro.uvt.info.proj.persistence.NodeRepository;
import ro.uvt.info.proj.visitor.Summary;
import ro.uvt.info.proj.visitor.SummaryVisitor;

import java.util.List;

@Service
public class NodeService {
    private final NodeRepository repository;

    public NodeService(NodeRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Category createCategory(CreateCategoryRequest request) {
        Category category = new Category(request.title());
        attachToParent(category, request.parentId());
        return repository.save(category);
    }

    @Transactional
    public Task createTask(CreateTaskRequest request) {
        Task task = new Task(request.title(), request.estimatedMinutes(), request.deadline());
        attachToParent(task, request.parentId());
        return repository.save(task);
    }

    private void attachToParent(Node node, Integer parentId) {
        if (parentId == null) {
            return;   // nod de nivel superior, fără părinte
        }
        Node parent = repository.findById(parentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Părintele nu există"));
        if (!(parent instanceof Category category)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Părintele trebuie să fie o categorie");
        }
        category.add(node);   // metoda din etapa model: leagă copilul de părinte
    }
    @Transactional(readOnly = true)
    public List<Node> getRoots() {
        return repository.findByParentIsNull();
    }

    @Transactional(readOnly = true)
    public Node getNode(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nodul nu există"));
    }
    @Transactional(readOnly = true)
    public Summary getSummary(int id) {
        Node node = getNode(id);
        SummaryVisitor visitor = new SummaryVisitor();
        node.accept(visitor);
        return visitor.getSummary();
    }

    @Transactional(readOnly = true)
    public Summary getSummaryOfAll() {
        SummaryVisitor visitor = new SummaryVisitor();
        for (Node root : repository.findByParentIsNull()) {
            root.accept(visitor);
        }
        return visitor.getSummary();
    }
    @Transactional
    public Task setDone(int id, boolean done) {
        Node node = getNode(id);
        if (!(node instanceof Task task)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Doar sarcinile pot fi marcate ca terminate");
        }
        task.setDone(done);
        return repository.save(task);
    }
    @Transactional
    public void deleteNode(int id) {
        Node node = getNode(id);
        Category parent = node.getParent();
        if (parent != null) {
            parent.remove(node);   // orphanRemoval îl șterge din bază, împreună cu subarborele
        } else {
            repository.delete(node);   // nod de nivel superior
        }
    }
}