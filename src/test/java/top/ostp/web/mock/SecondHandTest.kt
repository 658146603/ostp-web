package top.ostp.web.mock

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import top.ostp.web.mapper.BookMapper
import top.ostp.web.mapper.SecondHandFindMapper
import top.ostp.web.mapper.SecondHandPublishMapper
import top.ostp.web.mapper.StudentMapper
import top.ostp.web.mock.SecondHandTest.Configuration.Companion.book
import top.ostp.web.mock.SecondHandTest.Configuration.Companion.secondHandFind
import top.ostp.web.mock.SecondHandTest.Configuration.Companion.secondHandPublish
import top.ostp.web.mock.SecondHandTest.Configuration.Companion.studentA
import top.ostp.web.mock.SecondHandTest.Configuration.Companion.studentB
import top.ostp.web.model.Book
import top.ostp.web.model.SecondHandFind
import top.ostp.web.model.SecondHandPublish
import top.ostp.web.model.Student
import top.ostp.web.service.SecondHandFindService
import top.ostp.web.service.SecondHandPublishService
import top.ostp.web.util.UuidProvider

@ExtendWith(MockitoExtension::class)
@SpringBootTest
class SecondHandTest {
    @Autowired
    lateinit var secondHandFindService: SecondHandFindService

    @Autowired
    lateinit var secondHandPublishService: SecondHandPublishService

    @TestConfiguration
    internal open class Configuration {
        companion object {
            val studentA = Student().apply { id = "201806061219";name = "wcf";balance = 20000 }
            val studentB = Student().apply { id = "201806061108";name = "hhr";balance = 20000 }
            val book = Book("0001", "测试书籍", 2000, null)
            val secondHandPublish =
                SecondHandPublish("55ddd10d-a99f-478e-be0b-b7197eadff1f", studentA, book, 1000.0, 0, 0)
            val secondHandFind = SecondHandFind("5812fcd6-1b48-4aa7-ab63-fc70d52b843c", studentB, book, 0.0, 0, 0)
        }

        @Bean
        @Primary
        open fun secondHandFindMapper() = mock<SecondHandFindMapper> {
            on { selectBuyListByStudentAndBook(studentB.id, book.isbn) } doReturn listOf(secondHandFind)
            on { select(secondHandFind.id) } doReturn secondHandFind
            on { update(eq(secondHandFind)) } doReturn 1
        }

        @Bean
        @Primary
        open fun secondHandPublishMapper() = mock<SecondHandPublishMapper> {
            on { select(secondHandPublish.id) } doReturn secondHandPublish
            on { insert(eq(secondHandPublish)) } doReturn 1
        }

        @Bean
        @Primary
        open fun studentMapper() = mock<StudentMapper> {
            on { selectStudentById(studentA.id) } doReturn studentA
            on { selectStudentById(studentB.id) } doReturn studentB
            on { changeMoney(studentA, +secondHandPublish.price.toInt()) } doAnswer { studentA.balance += secondHandPublish.price.toInt();1 }
            on { changeMoney(studentB, -secondHandPublish.price.toInt()) } doAnswer { studentB.balance -= secondHandPublish.price.toInt();1 }
        }

        @Bean
        @Primary
        open fun bookMapper() = mock<BookMapper> {
            on { selectByISBN(book.isbn) } doReturn book
        }

        @Bean
        @Primary
        open fun uuidProvider1() = mock<UuidProvider> {
            on { uuid } doReturn secondHandPublish.id
        }
    }

    @Test
    fun testAddPublish() {
        val result = secondHandPublishService.insert(
            studentA.id,
            book.isbn,
            secondHandPublish.price.toInt(),
            secondHandPublish.exchange.toInt()
        )
        assertEquals(200, result.code)
    }

    @Test
    fun testBuyBook() {
        secondHandPublishService.insert(
            studentA.id,
            book.isbn,
            secondHandPublish.price.toInt(),
            secondHandPublish.exchange.toInt()
        )
        assertEquals(20000, studentA.balance)
        assertEquals(20000, studentB.balance)
        assertEquals(0, secondHandPublish.status)
        assertEquals(0, secondHandFind.status)
        secondHandPublishService.purchase(secondHandPublish.id, studentB.id)
        assertEquals(20000, studentA.balance)
        assertEquals(19000, studentB.balance)
        assertEquals(1, secondHandPublish.status)
        assertEquals(1, secondHandFind.status)
        secondHandFindService.changeStatusOk(secondHandFind.id, studentB.id)
        assertEquals(21000, studentA.balance)
        assertEquals(19000, studentB.balance)
    }

//    private fun <T> T.expect(result: T) = EasyMock.expect(this).andReturn(result)
}


