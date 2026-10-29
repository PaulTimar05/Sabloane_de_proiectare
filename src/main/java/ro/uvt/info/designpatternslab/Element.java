package ro.uvt.info.designpatternslab;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Element {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Section parent;

    @JsonIgnore
    public Section getParent() { return parent; }

    void setParent(Section parent) { this.parent = parent; }

    public abstract void accept(Visitor visitor);

    public void add(Element e)    { throw new UnsupportedOperationException(); }
    public void remove(Element e) { throw new UnsupportedOperationException(); }
    public Element get(int i)     { throw new UnsupportedOperationException(); }
}