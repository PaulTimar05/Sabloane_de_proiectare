package ro.uvt.info.designpatternslab;

import java.util.ArrayList;
import java.util.List;

public class TableOfContents extends Element {
    private final List<String> entries = new ArrayList<>();

    public void addEntry(String entry) {
        entries.add(entry);
    }

    public void render() {
        System.out.println("Table of contents");
        for (String entry : entries) {
            System.out.println(entry);
        }
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}