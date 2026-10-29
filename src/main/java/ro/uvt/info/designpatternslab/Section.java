package ro.uvt.info.designpatternslab;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(force = true)
public class Section extends Element {
    @Setter
    @Getter
    protected String title;
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    @OrderBy("id")
    protected List<Element> children = new ArrayList<>();
    public Section(String title) {
        this.title = title;
    }

    @Override
    public void add(Element e) {
        if (e.getParent() != null) {
            throw new IllegalStateException("Elementul are deja un părinte!");
        }
        e.setParent(this);
        children.add(e);
    }

    public void remove(Element e) {
        e.setParent(null);
        children.remove(e);

    }

    public Element get(int index) {
        return children.get(index);
    }

    public void render() {
        System.out.println(title);   // doar titlul, copiii îi vizitează accept
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
        acceptChildren(visitor);
    }

    protected void acceptChildren(Visitor visitor) {
        for (Element e : children) {
            e.accept(visitor);
        }
    }
}