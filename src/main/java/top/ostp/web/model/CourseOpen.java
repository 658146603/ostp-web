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

    public CourseOpen(Course course, int year, int semester, Book book, Teacher teacher) {
        this.course = course;
        this.year = year;
        this.semester = semester;
        this.book = book;
        this.teacher = teacher;
    }
}
