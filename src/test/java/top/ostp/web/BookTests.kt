package top.ostp.web

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.util.Assert
import top.ostp.web.mapper.BookMapper
import top.ostp.web.model.Book
import top.ostp.web.service.BookService

@SpringBootTest
class BookTests {
    @Autowired
    lateinit var bookMapper: BookMapper
    @Autowired
    lateinit var bookService: BookService

    @Test
    fun insertBook() {
        val book = Book()
        book.isbn = "97878-323-322-312"
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

    @Test
    fun selectXBook() {
        val bookAdvice = bookService.selectXByISBN("9787-212-222-331")
        Assert.notNull(bookAdvice, "bookAdvice为null")
    }

    @Test
    fun selectByQueryParameters() {
        val result = bookService.selectByQueryParameters("", "")
        print(result)
        assert(result.isNotEmpty())
    }

    @Test
    fun fuzzyQuery(){
        val books = bookMapper.fuzzyQuery("法")
        books.forEach(::println)
    }
}