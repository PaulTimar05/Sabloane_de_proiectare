package ro.uvt.info.proj;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


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