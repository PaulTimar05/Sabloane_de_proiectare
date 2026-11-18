package ro.uvt.info.proj;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
public class Category extends Node {

    @Getter
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
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
}