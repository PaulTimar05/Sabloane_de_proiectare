package ro.uvt.info.designpatternslab;

public class AlignLeft implements AlignStrategy {
    @Override
    public void render(Paragraph paragraph, Context context) {
        System.out.println(paragraph.getText());
    }
}
