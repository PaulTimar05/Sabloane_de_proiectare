package ro.uvt.info.designpatternslab;

import lombok.Getter;

@Getter
public abstract class Element {
    private Section parent;

    void setParent(Section parent) { this.parent = parent; }

    public abstract void print();

    public void add(Element e)    { throw new UnsupportedOperationException(); }
}