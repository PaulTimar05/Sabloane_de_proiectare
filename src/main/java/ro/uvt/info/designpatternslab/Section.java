package ro.uvt.info.designpatternslab;

import java.util.ArrayList;
import java.util.List;

public class Section extends Element {
    protected String title;
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

    @Override
    public void print() {
        System.out.println(title);
        for (Element e : children) {
            e.print();
        }
    }
}