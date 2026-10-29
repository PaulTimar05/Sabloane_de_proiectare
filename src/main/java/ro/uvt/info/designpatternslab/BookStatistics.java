package ro.uvt.info.designpatternslab;

public class BookStatistics implements Visitor {
    private int images;
    private int tables;
    private int paragraphs;

    @Override public void visit(Book book)             { }
    @Override public void visit(Section section)       { }
    @Override public void visit(TableOfContents toc)   { }
    @Override public void visit(Paragraph paragraph)   { paragraphs++; }
    @Override public void visit(ImageProxy imageProxy) { images++; }   // nu încarcă imaginea!
    @Override public void visit(Image image)           { images++; }
    @Override public void visit(Table table)           { tables++; }

    public void printStatistics() {
        System.out.println("Book Statistics:");
        System.out.println("*** Number of images: " + images);
        System.out.println("*** Number of tables: " + tables);
        System.out.println("*** Number of paragraphs: " + paragraphs);
    }
}