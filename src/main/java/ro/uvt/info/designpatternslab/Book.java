package ro.uvt.info.designpatternslab;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Entity
@NoArgsConstructor(force = true)
public class Book extends Section {
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Getter
    private final List<Author> authors = new ArrayList<>();

    public Book(String title) {
        super(title);
    }

    public void addAuthor(Author author) {
        authors.add(author);
    }

    public void addContent(Element e) {
        add(e);
    }

    public void render() {
        System.out.println("Book: " + getTitle());
        System.out.println("Authors:");
        for (Author a : authors) {
            a.print();
        }
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);    // aici ajunge la visit(Book), nu visit(Section)
        acceptChildren(visitor);
    }
}