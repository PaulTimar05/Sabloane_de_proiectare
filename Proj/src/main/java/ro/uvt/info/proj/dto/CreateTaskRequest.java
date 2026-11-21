package ro.uvt.info.proj.dto;

import java.time.LocalDate;

public record CreateTaskRequest(String title, Integer estimatedMinutes, LocalDate deadline, Integer parentId) {
}