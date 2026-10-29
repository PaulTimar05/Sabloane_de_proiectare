package ro.uvt.info.designpatternslab;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import java.util.concurrent.TimeUnit;
@Entity
@NoArgsConstructor(force = true)
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
    public void render() {
            System.out.println("Paragraph: " + url);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
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
