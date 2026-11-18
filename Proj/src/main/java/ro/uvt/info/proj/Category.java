package ro.uvt.info.proj;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ro.uvt.info.proj.visitor.NodeVisitor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
public class Category extends Node {

    @Getter
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id")
    private List<Node> children = new ArrayList<>();

    public Category(String title) {
        super(title);
    }

    public void add(Node node) {
        if (node.getParent() != null) {
            throw new IllegalStateException("Nodul are deja un părinte!");
        }
        node.setParent(this);
        children.add(node);
    }
    public void remove(Node node) {
        children.remove(node);
        node.setParent(null);
    }
    @Override
    public String getType() {
        return "category";

    }
    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
        for (Node child : children) {
            child.accept(visitor);
        }
    }
}