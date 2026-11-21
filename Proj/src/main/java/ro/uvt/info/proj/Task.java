package ro.uvt.info.proj;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ro.uvt.info.proj.visitor.NodeVisitor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Task extends Node {
    private Integer estimatedMinutes;   // opțional (null = necunoscută)
    private LocalDate deadline;         // opțional
    private boolean done;

    public Task(String title, Integer estimatedMinutes, LocalDate deadline) {
        super(title);
        this.estimatedMinutes = estimatedMinutes;
        this.deadline = deadline;
    }
    @Override
    public String getType() {
        return "task";
    }
    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }
}