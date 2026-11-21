package ro.uvt.info.proj.visitor;

import ro.uvt.info.proj.Category;
import ro.uvt.info.proj.Task;

import java.time.LocalDate;

public class SummaryVisitor implements NodeVisitor {
    private int categories;
    private int tasks;
    private int doneTasks;
    private int totalMinutes;
    private int remainingMinutes;
    private int overdueTasks;

    @Override
    public void visit(Category category) {
        categories++;
    }

    @Override
    public void visit(Task task) {
        tasks++;
        int minutes = task.getEstimatedMinutes() == null ? 0 : task.getEstimatedMinutes();   // durata e opțională
        totalMinutes += minutes;
        if (task.isDone()) {
            doneTasks++;
        } else {
            remainingMinutes += minutes;
            if (task.getDeadline() != null && task.getDeadline().isBefore(LocalDate.now())) {
                overdueTasks++;   // termen depășit și încă nefăcută
            }
        }
    }

    public Summary getSummary() {
        return new Summary(categories, tasks, doneTasks, totalMinutes, remainingMinutes, overdueTasks);
    }
}