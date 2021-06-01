package top.ostp.web.mock

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.isA
import org.mockito.kotlin.mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import top.ostp.web.mapper.BookMapper
import top.ostp.web.mapper.BookOrderMapper
import top.ostp.web.mapper.CourseOpenMapper
import top.ostp.web.mapper.StudentMapper
import top.ostp.web.mock.OrderBookTest.Configuration.Companion.bookA
import top.ostp.web.mock.OrderBookTest.Configuration.Companion.bookB
import top.ostp.web.mock.OrderBookTest.Configuration.Companion.bookC
import top.ostp.web.mock.OrderBookTest.Configuration.Companion.orders
import top.ostp.web.mock.OrderBookTest.Configuration.Companion.studentA
import top.ostp.web.model.*
import top.ostp.web.model.complex.SearchParams2
import top.ostp.web.service.BookOrderService
import top.ostp.web.service.BookService
import top.ostp.web.service.StudentService

@SpringBootTest
class OrderBookTest {
    @Autowired
    lateinit var studentService: StudentService

    @Autowired
    lateinit var bookOrderService: BookOrderService

    @Autowired
    lateinit var bookService: BookService

    @TestConfiguration
    internal open class Configuration {
        companion object {
            val collegeA = College().apply { id = 1;name = "计算机学院" }
            val collegeB = College().apply { id = 2;name = "信息学院" }
            val collegeC = College().apply { id = 3;name = "机械学院" }
            val majorA = Major().apply { id = 1;name = "软件工程";college = collegeA }
            val majorB = Major().apply { id = 2;name = "计算机科学与技术";college = collegeA }
            val majorC = Major().apply { id = 3;name = "通信工程";college = collegeC }
            val clazzA = Clazz().apply { id = 1;name = "移动应用开发方向1805";major = majorA }
            val clazzB = Clazz().apply { id = 2;name = "移动应用开发方向1806";major = majorA }
            val clazzC = Clazz().apply { id = 3;name = "移动应用开发方向1803";major = majorA }
            val studentA = Student().apply { id = "201806061219";name = "wcf";balance = 20000;clazz = clazzA }
            val studentB = Student().apply { id = "201806061108";name = "hhr";balance = 20000;clazz = clazzA }
            val studentC = Student().apply { id = "201806061201";name = "cht";balance = 20000;clazz = clazzC }
            val bookA = Book("0001", "测试书籍A", 2000, null)
            val bookB = Book("0002", "测试书籍B", 200000, null)
            val bookC = Book("0003", "测试书籍C", 2000, null)
            val courseA = Course().apply { id = "1";name = "A";major = majorA }
            val courseB = Course().apply { id = "2";name = "B";major = majorA }
            val courseOpenA = CourseOpen().apply { id = 1;year = 2020;semester = 1;book = bookA;course = courseA }
            val courseOpenB = CourseOpen().apply { id = 2;year = 2020;semester = 1;book = bookB;course = courseB }
            val students = hashSetOf<Student>(studentA, studentB, studentC)
            val clazzs = hashSetOf<Clazz>(clazzA, clazzB, clazzC)
            val majors = hashSetOf<Major>(majorA, majorB, majorC)
            val colleges = hashSetOf<College>(collegeA, collegeB, collegeC)
            val books = hashSetOf<Book>(bookA, bookB, bookC)
            val orders = hashSetOf<StudentBookOrder>()
            val courseOpens = hashSetOf<CourseOpen>(courseOpenA, courseOpenB)
            val courses = hashSetOf<Course>(courseA, courseB)
        }

        @Bean
        @Primary
        open fun studentMapper() = mock<StudentMapper> {
            on { selectStudentById(isA<String>()) } doAnswer { mock -> SecondHandTest.Configuration.students.firstOrNull { student -> student.id == mock.arguments[0] } }
            on { changeMoney(isA<Student>(), isA<Int>()) } doAnswer { mock ->
                val student = mock.getArgument<Student>(0)
                if (student != null) {
                    if (student.balance + mock.getArgument<Int>(1).toInt() >= 0) {
                        student.balance += mock.getArgument<Int>(1).toInt()
                        1
                    } else {
                        0
                    }
                } else {
                    0
                }
            }
            on { queryMoney(isA<String>()) } doAnswer { mock -> students.first { student -> student.id == mock.arguments[0] } }
        }

        @Bean
        @Primary
        open fun bookMapper() = mock<BookMapper> {
            on { selectByISBN(isA<String>()) } doAnswer { mock ->
                books.firstOrNull { book ->
                    book.isbn == mock.arguments[0]
                }
            }
        }

        @Bean
        @Primary
        open fun courseOpenMapper() = mock<CourseOpenMapper> {
            on { selectByBookAndStudent(isA<String>(), isA<String>()) } doAnswer { mock ->
                val isbn = mock.getArgument<String>(0)
                val studentId = mock.getArgument<String>(1)
                val student = students.first { it.id == studentId }
                courseOpens.filter { it.book.isbn == isbn && it.course in courses.filter { course -> course.major?.id == student.clazz.major?.id } }
            }
        }

        @Bean
        @Primary
        open fun bookOrderMapper() = mock<BookOrderMapper> {
            on {
                addBookOrder(
                    student = isA<String>(),
                    book = isA<String>(),
                    price = isA<Int>(),
                    year = isA<Int>(),
                    semester = isA<Int>()
                )
            } doAnswer { mock ->
                val student = students.first { student -> student.id == mock.arguments[0] }
                val book = books.first { book -> book.isbn == mock.arguments[1] }
                val id = orders.maxOfOrNull { order -> order.id }?.plus(1) ?: 1
                val price = mock.getArgument<Int>(2)
                val year = mock.getArgument<Int>(3)
                val semester = mock.getArgument<Int>(4)
                val bookOrder = StudentBookOrder(id, student, book, price, year, semester, 0)
                val size = orders.size
                orders.add(bookOrder)
                orders.size - size
            }

            on { searchOfStudent(isA<SearchParams2>()) } doAnswer { mock ->
                val params2 = mock.getArgument<SearchParams2>(0)
                orders.filter { order -> order.student?.id == params2.personId && order.book?.isbn == params2.isbn && order.year == params2.year && order.semester == params2.semester }
            }

            on { deleteBookOrder(isA<Int>()) } doAnswer { mock ->
                val size = orders.size
                orders.removeIf { it.id == mock.getArgument<Int>(0).toLong() }
                orders.size - size
            }
        }


    }

    @Test
    fun testOrderBook() {
        val order = studentA
        val book = bookA
        val balance = order.balance.toLong()
        val response0 = bookService.orderBookStu(SearchParams2(order.id, book.isbn, 2021, 1))
        assertEquals(200, response0.code)
        println(response0.message)
        assertEquals(1, orders.size)
        assertEquals(balance - book.price, order.balance.toLong())
    }

    @Test
    fun testOrderBookCancel() {
        val order = studentA
        val book = bookA
        val balance = order.balance.toLong()
        val size = orders.size
        val response0 = bookService.orderBookStu(SearchParams2(order.id, book.isbn, 2021, 1))
        assertEquals(200, response0.code)
        println(response0.message)
        assertEquals(size + 1, orders.size)
        assertEquals(balance - book.price, order.balance.toLong())

        val response1 = bookService.orderBookStu(SearchParams2(order.id, book.isbn, 2021, 1))
        assertEquals(200, response1.code)
        println(response1.message)
        assertEquals(size, orders.size)
        assertEquals(balance, order.balance.toLong())
    }

    @Test
    fun testOrderBookMoneyNotEnough() {
        val order = studentA
        val book = bookB
        val balance = order.balance.toLong()
        val size = orders.size
        val response0 = bookService.orderBookStu(SearchParams2(order.id, book.isbn, 2021, 1))
        assertEquals(403, response0.code)
        println(response0.message)
        assertEquals(size, orders.size)
        assertEquals(balance, order.balance.toLong())
    }

    @Test
    fun testOrderBookNotExist() {
        val order = studentA
        val book = Book().apply { isbn = "1234" }
        val balance = order.balance.toLong()
        val size = orders.size
        val response0 = bookService.orderBookStu(SearchParams2(order.id, book.isbn, 2021, 1))
        assertEquals(403, response0.code)
        println(response0.message)
        assertEquals(size, orders.size)
        assertEquals(balance, order.balance.toLong())
    }

    @Test
    fun testOrderBookCourseNotOpen() {
        val order = studentA
        val book = bookC
        val balance = order.balance.toLong()
        val size = orders.size
        val response0 = bookService.orderBookStu(SearchParams2(order.id, book.isbn, 2021, 1))
        assertEquals(403, response0.code)
        println(response0.message)
        assertEquals(size, orders.size)
        assertEquals(balance, order.balance.toLong())
    }

}