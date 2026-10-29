package ro.uvt.info.designpatternslab;

import lombok.Getter;

//pasi pentru declararea unei clase
//public class Nume + (optional) implements daca mosteneste
//private parametrii
//creezi constructor public cu parametrii, atribuind fiecarui parametru valoarea ce ar luao
//creezi prin @Override o functie (publica, daca e refolosita), in cazul nostru doar printam toti parametrii
public class Paragraph extends Element {
    @Getter
    private String text;
    private AlignStrategy textAlignment;
    public Paragraph(String text) {
        this.text = text;
    }

    public void setAlignStrategy(AlignStrategy strategy) {
        this.textAlignment = strategy;
    }
    @Override
    public void print() {
        if (textAlignment == null) {
            System.out.println("Paragraph: " + text);
        } else {
            textAlignment.render(this, new Context(40));
        }
    }
}
