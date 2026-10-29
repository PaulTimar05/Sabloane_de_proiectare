package ro.uvt.info.designpatternslab;
//pasi pentru declararea unei clase
//public class Nume + (optional) implements daca mosteneste
//private parametrii
//creezi constructor public cu parametrii, atribuind fiecarui parametru valoarea ce ar luao
//creezi prin @Override o functie (publica, daca e refolosita), in cazul nostru doar printam toti parametrii
public class Paragraph extends Element {
    private String text;
    public Paragraph(String text) {
        this.text = text;
    }
    @Override
    public void print() {
        System.out.println("Paragraph: " + text);
    }
}
