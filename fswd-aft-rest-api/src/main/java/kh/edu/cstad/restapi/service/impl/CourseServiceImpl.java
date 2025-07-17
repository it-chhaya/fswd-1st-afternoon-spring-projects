package kh.edu.cstad.restapi.service.impl;

import kh.edu.cstad.restapi.domain.Course;
import kh.edu.cstad.restapi.dto.CourseResponse;
import kh.edu.cstad.restapi.dto.CreateCourseRequest;
import kh.edu.cstad.restapi.repository.CourseRepository;
import kh.edu.cstad.restapi.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;


    @Override
    public CourseResponse createCourse(CreateCourseRequest createCourseRequest) {

        // Validate course's code
        boolean isCodeExisted = courseRepository
                .getCourses()
                .stream()
                .anyMatch(course -> course.getCode().equals(createCourseRequest.code()));

        if (isCodeExisted) {
            // CONFLICT
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Course code already exists"
            );
        }

        // Map DTO to Domain Model
        Course course = Course.builder()
                .id(UUID.randomUUID().toString())
                .code(createCourseRequest.code())
                .title(createCourseRequest.title())
                .price(createCourseRequest.price())
                .status(false)
                .build();

        // Save data
        courseRepository.getCourses().add(course);

        // Map Domain Model to DTO
        return CourseResponse.builder()
                .title(course.getTitle())
                .code(course.getCode())
                .price(course.getPrice())
                .build();
    }


    @Override
    public List<CourseResponse> getCourses(Boolean status) {
        return courseRepository.getCourses()
                .stream()
                .filter(course -> course.getStatus() == status)
                .map(course -> CourseResponse.builder()
                        .title(course.getTitle())
                        .code(course.getCode())
                        .price(course.getPrice())
                        .build())
                .toList();
    }

}
