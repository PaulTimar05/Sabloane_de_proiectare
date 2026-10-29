package ro.uvt.info.designpatternslab;

public class AlignCenter implements AlignStrategy{
    public void render(Paragraph paragraph, Context context) {
        String text = paragraph.getText();
        int spaces = Math.max(0, (context.getWidth() - text.length()) / 2);
        System.out.println(" ".repeat(spaces) + text);
    }
}
