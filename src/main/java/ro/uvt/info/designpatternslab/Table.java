package ro.uvt.info.designpatternslab;

//pasi pentru declararea unei clase
//public class Nume + (optional) implements daca mosteneste
//private parametrii
//creezi constructor public cu parametrii, atribuind fiecarui parametru valoarea ce ar luao
//creezi prin @Override o functie (publica, daca e refolosita), in cazul nostru doar printam toti parametrii
public class Table extends Element {
    private String titlu;
    public Table(String titlu){
        this.titlu = titlu;
    }

    public void render() {
        System.out.println("Paragraph: " + titlu);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
