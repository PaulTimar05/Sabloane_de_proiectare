package ro.uvt.info.designpatternslab;

import java.util.ArrayList;
import java.util.List;

public class Book extends Section {
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