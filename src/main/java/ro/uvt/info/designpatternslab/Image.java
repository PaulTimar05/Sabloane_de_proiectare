package ro.uvt.info.designpatternslab;

import java.util.concurrent.TimeUnit;

//pasi pentru declararea unei clase
//public class Nume + (optional) implements daca mosteneste
//private parametrii
//creezi constructor public cu parametrii, atribuind fiecarui parametru valoarea ce ar luao
//creezi prin @Override o functie, in cazul nostru doar printam toti parametrii
public class Image extends Element implements Picture {
    private String url;
    public Image(String url) {
        this.url = url;
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void print(){
        System.out.println("Link Url Imagine: " + url);
    }
    @Override
    public String url() {
        return url;
    }
    @Override
    public ImageContent content() {
        return new ImageContent("content of " + url);
    }
}
