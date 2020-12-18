package top.ostp.web.model;


public class CourseOpen {

  private Course course;
  private long year;
  private long semester;
  private Book book;
  private Teacher teacher;


  public Course getCourse() {
    return course;
  }

  public void setCourse(Course course) {
    this.course = course;
  }


  public long getYear() {
    return year;
  }

  public void setYear(long year) {
    this.year = year;
  }


  public long getSemester() {
    return semester;
  }

  public void setSemester(long semester) {
    this.semester = semester;
  }


  public Book getBook() {
    return book;
  }

  public void setBook(Book book) {
    this.book = book;
  }


  public Teacher getTeacher() {
    return teacher;
  }

  public void setTeacher(Teacher teacher) {
    this.teacher = teacher;
  }


  @Override
  public String toString() {
    return "CourseOpen{" +
            "course=" + course +
            ", year=" + year +
            ", semester=" + semester +
            ", book=" + book +
            ", teacher=" + teacher +
            '}';
  }
}
