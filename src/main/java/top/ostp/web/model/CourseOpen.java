package top.ostp.web.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseOpen {
    private long id;
    private Course course;
    private int year;
    private int semester;
    private Book book;
    private Teacher teacher;
}
