package top.ostp.web.model;


public class CourseOpen {

  private Course course;
  private int year;
  private int semester;
  private Book book;
  private Teacher teacher;

  public CourseOpen() {

  }

  public CourseOpen(Course course, int year, int semester, Book book, Teacher teacher) {
    this.course = course;
    this.year = year;
    this.semester = semester;
    this.book = book;
    this.teacher = teacher;
  }

  public Course getCourse() {
    return course;
  }

  public void setCourse(Course course) {
    this.course = course;
  }


  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }


  public int getSemester() {
    return semester;
  }

  public void setSemester(int semester) {
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
