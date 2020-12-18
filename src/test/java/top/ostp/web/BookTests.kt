package top.ostp.web

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import top.ostp.web.mapper.BookMapper
import top.ostp.web.model.Book

@SpringBootTest
class BookTests {
    @Autowired
    lateinit var bookMapper: BookMapper

    @Test
    fun insertBook() {
        val book = Book()
        book.isbn = "97878-------312"
        book.cover = "312"
        book.name = "abc"
        book.price = 8899
        println(bookMapper.insert(book))
    }

    @Test
    fun selectBook() {
        val book = bookMapper.selectByISBN("97878-------312")
        println(book)
    }

    @Test
    fun deleteBook() {
        val books = bookMapper.selectAll()
        books.forEach(bookMapper::delete)
    }

    @Test
    fun updateBook() {
        val book = bookMapper.selectByISBN("97878-------312")
        book?.price = 21
        if (book != null) {
            bookMapper.updateByISBN(book)
        }
    }
}