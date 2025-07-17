package kh.edu.cstad.restapi.service;

import kh.edu.cstad.restapi.dto.CourseResponse;
import kh.edu.cstad.restapi.dto.CreateCourseRequest;

import java.util.List;

public interface CourseService {

    // response nothing
    // data process with code
    // use for លុបវគ្គសិក្សាតាមរយៈ code
    // if code doesn't exist, show message code doesn't exist (404)
    //void deleteCourseByCode(String code);

    // response data type
    // data processing (parameter)
    // use for បង្កើតវគ្គសិក្សាថ្មី
    /**
     * Create a new course
     * @param createCourseRequest data from client
     * @return CourseResponse
     * @author CHAN CHHAYA
     */
    CourseResponse createCourse(CreateCourseRequest createCourseRequest);



    /**
     * ទាញយកព័ត៌មានវគ្គសិក្សាទាំងអស់
     *
     * @author Chan Chhaya
     */
    List<CourseResponse> getCourses(Boolean status);

}
