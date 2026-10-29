package ro.uvt.info.designpatternslab;

import java.util.ArrayList;
import java.util.List;

public class Section implements Element {
    protected String title;
    protected List<Element> children = new ArrayList<>();

    public Section(String title) {
        this.title = title;
    }

    @Override
    public void add(Element e) {
        children.add(e);
    }

    @Override
    public void remove(Element e) {
        children.remove(e);
    }

    @Override
    public Element get(int index) {
        return children.get(index);
    }

    @Override
    public void print() {
        System.out.println(title);
        for (Element e : children) {
            e.print();
        }
    }
}