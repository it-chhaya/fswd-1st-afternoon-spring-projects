package kh.edu.cstad.restapi.repository;

import kh.edu.cstad.restapi.domain.Course;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Repository
public class CourseRepository {

    private final List<Course> courses;

    public CourseRepository() {
        courses = new ArrayList<>();
        courses.add(Course.builder()
                .id(UUID.randomUUID().toString())
                .code("ISTAD-C001")
                .title("Spring Framework")
                .price(100.00)
                .status(true)
                .build());
        courses.add(Course.builder()
                .id(UUID.randomUUID().toString())
                .code("ISTAD-C002")
                .title("NextJS")
                .price(90.00)
                .status(false)
                .build());
    }

}
