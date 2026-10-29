package ro.uvt.info.designpatternslab;
//pasi pentru declararea unei clase
//public class Nume + (optional) implements daca mosteneste
//private parametrii
//creezi constructor public cu parametrii, atribuind fiecarui parametru valoarea ce ar luao
//creezi prin @Override o functie, in cazul nostru doar printam toti parametrii
public class Image extends Element {
    private String url;
    public Image(String url) {
        this.url = url;
    }
    @Override
    public void print(){
        System.out.println("Link Url Imagine: " + url);
    }
}
