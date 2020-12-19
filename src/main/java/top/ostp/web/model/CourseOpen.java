package top.ostp.web.model;

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

    public CourseOpen(long id, Course course, int year, int semester, Book book, Teacher teacher) {
        this.id = id;
        this.course = course;
        this.year = year;
        this.semester = semester;
        this.book = book;
        this.teacher = teacher;
    }

    public CourseOpen() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Course getCourse() {
        return this.course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getSemester() {
        return this.semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public Book getBook() {
        return this.book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Teacher getTeacher() {
        return this.teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }


    public String toString() {
        return "CourseOpen(id=" + this.getId() + ", course=" + this.getCourse() + ", year=" + this.getYear() + ", semester=" + this.getSemester() + ", book=" + this.getBook() + ", teacher=" + this.getTeacher() + ")";
    }
}
