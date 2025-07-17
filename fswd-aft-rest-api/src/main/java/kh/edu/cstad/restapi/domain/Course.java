package kh.edu.cstad.restapi.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {

    private String id;
    private String code;
    private String title;
    private Double price;
    private Boolean status;

}
