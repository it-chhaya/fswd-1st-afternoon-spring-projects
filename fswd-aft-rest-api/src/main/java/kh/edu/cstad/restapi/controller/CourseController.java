package kh.edu.cstad.restapi.controller;

import kh.edu.cstad.restapi.dto.CourseResponse;
import kh.edu.cstad.restapi.dto.CreateCourseRequest;
import kh.edu.cstad.restapi.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// @Controller
// @ResponseBody
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CourseResponse createCourse(@RequestBody CreateCourseRequest createCourseRequest) {
        return courseService.createCourse(createCourseRequest);
    }


    @GetMapping
    public List<CourseResponse> getCourses(
            @RequestParam(required = false, defaultValue = "true") Boolean status) {
        return courseService.getCourses(status);
    }

}
