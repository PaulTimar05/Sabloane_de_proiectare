package ro.uvt.info.designpatternslab;

public class GenerateToC implements Visitor {
    private final TableOfContents toc = new TableOfContents();

    public TableOfContents getToC() {
        return toc;
    }

    @Override
    public void visit(Section section) {
        int level = 0;
        Section p = section.getParent();
        while (p != null) {
            level++;
            p = p.getParent();
        }
        toc.addEntry("  ".repeat(level) + section.getTitle());
    }

    @Override public void visit(Book book)             { }
    @Override public void visit(TableOfContents t)     { }
    @Override public void visit(Paragraph paragraph)   { }
    @Override public void visit(ImageProxy imageProxy) { }
    @Override public void visit(Image image)           { }
    @Override public void visit(Table table)           { }
}