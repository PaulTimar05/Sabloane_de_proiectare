package ro.uvt.info.designpatternslab;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(force = true)
@Getter
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String surname;
    public Author(String name, String surname) {
        this.name=name;
        this.surname=surname;
    }
    public void  print(){
        System.out.println("Nume: "+ name +" "+ surname);
    }
}
