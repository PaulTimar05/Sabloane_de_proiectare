package ro.uvt.info.proj;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Task extends Node {
    private int estimatedMinutes;
    private LocalDate deadline;
    private boolean done;

    public Task(String title, int estimatedMinutes, LocalDate deadline) {
        super(title);
        this.estimatedMinutes = estimatedMinutes;
        this.deadline = deadline;
    }
    @Override
    public String getType() {
        return "task";
    }
}