package top.ostp.web

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import top.ostp.web.mapper.BookMapper
import top.ostp.web.model.Book

@SpringBootTest
internal class WebApplicationTestsKT {
    @Test
    fun contextLoads() {
    }

    @Autowired
    lateinit var bookMapper: BookMapper

    @Test
    fun insertBook() {
        val book = Book()
        book.isbn = "97878-------312"
        book.cover = "312"
        book.name = "abc"
        book.price = 8899
        bookMapper.insert(book)
    }
}