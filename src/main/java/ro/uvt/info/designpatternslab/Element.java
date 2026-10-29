package ro.uvt.info.designpatternslab;

public interface Element {
    void print();
    default void add(Element e) {
        throw new UnsupportedOperationException("Nu se poate adăuga într-o frunză");
    }
    default void remove(Element e) {
        throw new UnsupportedOperationException("Nu se poate șterge dintr-o frunză");
    }
    default Element get(int index) {
        throw new UnsupportedOperationException("Nu are copii");
    }
}
