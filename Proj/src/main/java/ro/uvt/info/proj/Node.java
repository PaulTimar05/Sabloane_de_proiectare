package ro.uvt.info.proj;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ro.uvt.info.proj.visitor.NodeVisitor;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor
@Getter
public abstract class Node {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Setter
    private String title;
    public abstract String getType();
    public abstract void accept(NodeVisitor visitor);
    protected Node(String title) {
        this.title = title;
    }
    @ManyToOne
    @JsonIgnore
    private Category parent;

    void setParent(Category parent) {
        this.parent = parent;
    }
    @JsonIgnore
    public Category getParent() {
        return parent;
    }

}