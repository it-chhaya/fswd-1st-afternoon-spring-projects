package kh.edu.cstad.restapi.dto;

public record CreateCourseRequest(
        String code,
        String title,
        Double price
) {
}
