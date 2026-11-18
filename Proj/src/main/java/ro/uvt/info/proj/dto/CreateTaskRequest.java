package ro.uvt.info.proj.dto;

import java.time.LocalDate;

public record CreateTaskRequest(String title, int estimatedMinutes, LocalDate deadline, Integer parentId) {
}