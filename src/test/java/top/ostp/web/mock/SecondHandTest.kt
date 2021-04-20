package top.ostp.web.mock

import org.easymock.EasyMock
import org.easymock.IMocksControl
import org.junit.jupiter.api.Test
import org.mockito.MockedStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import top.ostp.web.mapper.BookMapper
import top.ostp.web.mapper.SecondHandFindMapper
import top.ostp.web.mapper.SecondHandPublishMapper
import top.ostp.web.mapper.StudentMapper
import top.ostp.web.model.Book
import top.ostp.web.model.SecondHandPublish
import top.ostp.web.model.Student
import top.ostp.web.service.SecondHandFindService
import top.ostp.web.service.SecondHandPublishService
import java.util.*
import org.mockito.Mockito.*


@SpringBootTest
class SecondHandTest {
//    @Autowired
//    lateinit var secondHandFindMapper: SecondHandFindMapper
//
//    @Autowired
//    lateinit var secondHandPublishMapper: SecondHandPublishMapper
//
//    @Autowired
//    lateinit var studentMapper: StudentMapper
//
//    @Autowired
//    lateinit var bookMapper: BookMapper

    @Autowired
    lateinit var secondHandFindService: SecondHandFindService

    @Autowired
    lateinit var secondHandPublishService: SecondHandPublishService

    companion object {
        val control: IMocksControl = EasyMock.createControl()
    }


    @TestConfiguration
    internal open class Configuration {

        @Bean
        @Primary
        open fun secondHandFindMapper(): SecondHandFindMapper {
            return control.createMock(SecondHandFindMapper::class.java)
        }

        @Bean
        @Primary
        open fun secondHandPublishMapper(): SecondHandPublishMapper {
            return control.createMock(SecondHandPublishMapper::class.java)
        }

        @Bean
        @Primary
        open fun studentMapper(): StudentMapper {
            return control.createMock(StudentMapper::class.java)
        }

        @Bean
        @Primary
        open fun bookMapper(): BookMapper {
            return control.createMock(BookMapper::class.java)
        }
    }

    @Test
    fun testBuyBook() = mockStatic(UUID::class.java).use { mocked: MockedStatic<UUID> ->
        val studentA = Student().apply { id = "201806061219";name = "wcf";balance = 20000 }
        val studentB = Student().apply { id = "201806061108";name = "hhr";balance = 20000 }
        val book = Book("0001", "测试书籍", 2000, null)
        val secondHandPublish = SecondHandPublish("55ddd10d-a99f-478e-be0b-b7197eadff1f", studentA, book, 1000.0, 1, 0)

        mocked.`when`<UUID>(UUID::randomUUID).thenReturn(UUID.fromString(secondHandPublish.id))
        secondHandPublishService.studentMapper.selectStudentById(studentA.id).expect(studentA)
        secondHandPublishService.bookMapper.selectByISBN(book.isbn).expect(book)

        control.replay()
        secondHandPublishService.insert(
            studentA.id,
            book.isbn,
            secondHandPublish.price.toInt(),
            secondHandPublish.exchange.toInt()
        )
        control.verify()

        mocked.verify(UUID::randomUUID)

    }

    private fun <T> T.expect(result: T) = EasyMock.expect(this).andReturn(result)
}


