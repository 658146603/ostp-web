package top.ostp.web

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import top.ostp.web.mapper.BookOrderMapper
import top.ostp.web.service.BookOrderService
import top.ostp.web.service.BookService

@SpringBootTest
class BookOrderTests {
    @Autowired
    lateinit var bookOrderMapper: BookOrderMapper

    @Test
    fun list() {
        println("all:")
        bookOrderMapper.list().forEach(::println)

        println("2019, 3:")
        bookOrderMapper.selectByYearSemester(2019, 3).forEach(::println)

        println("2019, 9:")
        bookOrderMapper.selectByYearSemester(2019, 9).forEach(::println)

        println("201806061219, 2019, 9:")
        bookOrderMapper.selectByYearSemesterAndStudent("201806061219", 2019, 9).forEach(::println)

        println("id 6:")
        println(bookOrderMapper.selectById(6))

        println("select by book and student")
        println(bookOrderMapper.selectByBookAndStudent("9787-213-243-432", "201806061201"))
    }

    @Test
    fun received() {
        bookOrderMapper.received(1)
    }

    @Test
    fun addBookOrder() {
        bookOrderMapper.addBookOrder("201806061219", "9787-212-222-338", 2099, 2020, 3)
    }

    @Test
    fun delete() {
        bookOrderMapper.deleteBookOrder(7)
    }

    @Test
    fun selectByBookAndStudent() {
        val orders = bookOrderMapper.selectByBookAndStudent("9787-212-222-333", "201806061201")
        println(orders)
    }


    @Autowired
    lateinit var bookOrderService: BookOrderService

    @Test
    fun test() {
        bookOrderService.getBookOrderListByClazz(5, false)
    }

}