package ro.uvt.info.proj.visitor;

public record Summary(int categories, int tasks, int doneTasks,
                      int totalMinutes, int remainingMinutes, int overdueTasks) {
}