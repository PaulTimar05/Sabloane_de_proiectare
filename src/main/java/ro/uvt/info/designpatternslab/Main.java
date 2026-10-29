package ro.uvt.info.designpatternslab;

public class Main {
    public static void main(String[] args) throws Exception {
        renderDemo();
        statsDemo();
         tocDemo();
    }

    static void renderDemo() {
        Book b = new Book("Noapte buna, copii!");
        b.addAuthor(new Author("Radu", "Pavel"));
        Section cap1 = new Section("Capitolul 1");
        Section cap11 = new Section("Capitolul 1.1");
        b.addContent(new Paragraph("Multumesc celor care ..."));
        b.addContent(cap1);
        cap1.add(new Paragraph("Moto capitol"));
        cap1.add(cap11);
        cap11.add(new Paragraph("Text din 1.1"));
        b.accept(new RenderContentVisitor());
    }
    static void statsDemo() {
        Section cap1 = new Section("Capitolul 1");
        cap1.add(new Paragraph("Paragraph 1"));
        cap1.add(new Paragraph("Paragraph 2"));
        cap1.add(new Paragraph("Paragraph 3"));
        cap1.add(new Paragraph("Paragraph 4"));
        cap1.add(new ImageProxy("ImageOne"));
        cap1.add(new Image("ImageTwo"));
        cap1.add(new Paragraph("Some text"));
        cap1.add(new Table("Table 1"));

        BookStatistics stats = new BookStatistics();
        cap1.accept(stats);
        stats.printStatistics();
    }
    static void tocDemo() {
        Book b = new Book("Noapte buna, copii!");
        Section cap1 = new Section("Capitolul 1");
        Section cap11 = new Section("Capitolul 1.1");
        b.addContent(cap1);
        cap1.add(cap11);

        GenerateToC gen = new GenerateToC();
        b.accept(gen);
        gen.getToC().render();
    }
}