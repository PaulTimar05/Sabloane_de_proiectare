package ro.uvt.info.designpatternslab;

import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(force = true)

public class Paragraph extends Element {
    @Getter
    private String text;
    @Transient

    private AlignStrategy textAlignment;
    public Paragraph(String text) {
        this.text = text;
    }

    public void setAlignStrategy(AlignStrategy strategy) {
        this.textAlignment = strategy;
    }
    public void render() {
        if (textAlignment == null) {
            System.out.println("Paragraph: " + text);
        } else {
            textAlignment.render(this, new Context(40));
        }
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
