package ro.uvt.info.designpatternslab;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(force = true)
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
