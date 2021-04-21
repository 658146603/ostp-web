package top.ostp.web.mock

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.isA
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
import top.ostp.web.mock.SecondHandTest.Configuration.Companion.studentA
import top.ostp.web.mock.SecondHandTest.Configuration.Companion.studentB
import top.ostp.web.mock.SecondHandTest.Configuration.Companion.studentC
import top.ostp.web.model.Book
import top.ostp.web.model.SecondHandFind
import top.ostp.web.model.SecondHandPublish
import top.ostp.web.model.Student
import top.ostp.web.service.SecondHandFindService
import top.ostp.web.service.SecondHandPublishService

@ExtendWith(MockitoExtension::class)
@SpringBootTest
class SecondHandTest {
    @Autowired
    lateinit var secondHandFindService: SecondHandFindService

    @Autowired
    lateinit var secondHandPublishService: SecondHandPublishService

    @TestConfiguration internal open class Configuration {
        companion object {
            val studentA = Student().apply { id = "201806061219";name = "wcf";balance = 20000 }
            val studentB = Student().apply { id = "201806061108";name = "hhr";balance = 20000 }
            val studentC = Student().apply { id = "201806061201";name = "cht";balance = 20000 }
            val book = Book("0001", "测试书籍", 2000, null)

            val students = hashSetOf<Student>(studentA, studentB, studentC)
            val books = hashSetOf<Book>(book)
            val publishes = hashSetOf<SecondHandPublish>()
            val finds = hashSetOf<SecondHandFind>()
        }

        @Bean
        @Primary
        open fun secondHandFindMapper() = mock<SecondHandFindMapper> {
            on { selectBuyListByStudentAndBook(isA<String>(), isA<String>()) } doAnswer { mock ->
                finds.filter { find -> find.person.id == mock.arguments[0] && find.book.isbn == mock.arguments[1] }
            }

            on { insert(isA<SecondHandFind>()) } doAnswer { mock ->
                val size = finds.size;finds.add(mock.getArgument(0));finds.size - size
            }

            on { select(isA<String>()) } doAnswer { mock ->
                finds.firstOrNull() { find ->
                    find.id == mock.getArgument<String>(0)
                }
            }

            on { update(isA<SecondHandFind>()) } doAnswer { mock ->
                val f = finds.firstOrNull { find ->
                    find.id == mock.getArgument<SecondHandFind>(0).id
                }
                f?.apply {
                    val target = mock.getArgument<SecondHandFind>(0)
                    book = target.book
                    exchange = target.exchange
                    person = target.person
                    price = target.price
                    second = target.second
                    status = target.status
                    return@doAnswer 1
                }

                return@doAnswer 0
            }

            on { selectByStudentId(isA<String>()) } doAnswer { mock -> finds.filter { find -> find.person.id == mock.arguments[0] } }
        }


        @Bean
        @Primary
        open fun secondHandPublishMapper() = mock<SecondHandPublishMapper> {
            on { select(isA<String>()) } doAnswer { mock -> publishes.firstOrNull() { publish -> publish.id == mock.arguments[0] } }
            on { insert(isA<SecondHandPublish>()) } doAnswer { mock -> val size = publishes.size;publishes.add(mock.getArgument(0));publishes.size - size }
            on { selectPublishByStudentId(isA<String>()) } doAnswer { mock -> publishes.filter { publish -> publish.person.id == mock.arguments[0] } }
            on { update(isA<SecondHandPublish>()) } doAnswer { mock ->
                val f = publishes.firstOrNull { find ->
                    find.id == mock.getArgument<SecondHandPublish>(0).id
                }
                f?.apply {
                    val target = mock.getArgument<SecondHandPublish>(0)
                    book = target.book
                    exchange = target.exchange
                    person = target.person
                    price = target.price
                    second = target.second
                    status = target.status
                    return@doAnswer 1
                }

                return@doAnswer 0
            }
        }

        @Bean
        @Primary
        open fun studentMapper() = mock<StudentMapper> {
            on { selectStudentById(isA<String>()) } doAnswer { mock -> students.firstOrNull() { student -> student.id == mock.arguments[0] } }
            on { changeMoney(isA<Student>(), isA<Int>()) } doAnswer { mock ->
                val student = mock.getArgument<Student>(0)?.let {
                    it.balance += mock.getArgument<Int>(1).toInt()
                }
                if (student != null) { 1 } else { 0 }
            }
        }

        @Bean
        @Primary
        open fun bookMapper() = mock<BookMapper> {
            on { selectByISBN(isA<String>()) } doAnswer { mock -> books.firstOrNull { book -> book.isbn == mock.arguments[0] } }
        }

    }

    @Test
    fun testAddPublish() {
        val seller = studentA
        val buyer = studentB
        val balanceA = studentA.balance
        val balanceB = studentB.balance
        val book = book
        val price = 1000
        val exchange = 0

        val result = secondHandPublishService.insert(seller.id, book.isbn, price, exchange)
        val publish = secondHandPublishService.selectPublishByStudentId(seller.id)?.data?.firstOrNull { it.person == seller && it.book == book && it.price.toInt() == price && it.status == 0L }!!
        assertEquals(200, result.code)
        assertEquals(0, publish.status)
        assertEquals(balanceA, seller.balance)
        assertEquals(balanceB, buyer.balance)
    }

    @Test
    fun testBuyBook() {
        val seller = studentA
        val buyer = studentB
        val balanceA = studentA.balance
        val balanceB = studentB.balance
        val book = book
        val price = 1000
        val exchange = 0

        secondHandPublishService.insert(seller.id, book.isbn, price, exchange)
        val publish = secondHandPublishService.selectPublishByStudentId(seller.id)?.data?.firstOrNull { it.person == seller && it.book == book && it.price.toInt() == price && it.status == 0L }!!
        assertEquals(balanceA, seller.balance)
        assertEquals(balanceB, buyer.balance)
        assertEquals(0, publish.status)


        secondHandPublishService.purchase(publish.id, buyer.id)
        val find = secondHandFindService.selectByStudentId(buyer.id)?.data?.firstOrNull { it.person == buyer && it.book == book && it.price.toInt() == price && it.status == 1L }!!
        assertEquals(balanceA, seller.balance)
        assertEquals(balanceB - price, buyer.balance)
        assertEquals(1, publish.status)
        assertEquals(1, find.status)


        secondHandFindService.changeStatusOk(find.id, buyer.id)
        assertEquals(balanceA + price, seller.balance)
        assertEquals(balanceB - price, buyer.balance)
        assertEquals(2, find.status)
    }

    @Test
    fun testMoneyNotEnough() {
        val seller = studentA
        val buyer = studentB
        val balanceA = studentA.balance
        val balanceB = studentB.balance
        val book = book
        val price = 100000
        val exchange = 0
        assertTrue(price > balanceB)

        secondHandPublishService.insert(seller.id, book.isbn, price, exchange)
        val publish = secondHandPublishService.selectPublishByStudentId(seller.id)?.data?.firstOrNull { it.person == seller && it.book == book && it.price.toInt() == price && it.status == 0L }!!
        assertEquals(balanceA, seller.balance)
        assertEquals(balanceB, buyer.balance)

        val result = secondHandPublishService.purchase(publish.id, buyer.id)
        assertEquals(403, result.code)
        assertEquals(balanceA, seller.balance)
        assertEquals(balanceB, buyer.balance)
    }

    @Test
    fun testBuySelfBook() {
        val seller = studentA
        val balanceA = studentA.balance
        val book = book
        val price = 1000
        val exchange = 0

        secondHandPublishService.insert(seller.id, book.isbn, price, exchange)
        val publish = secondHandPublishService.selectPublishByStudentId(seller.id)?.data?.firstOrNull { it.person == seller && it.book == book && it.price.toInt() == price && it.status == 0L }!!
        assertEquals(balanceA, seller.balance)
        assertEquals(0, publish.status)


        val result = secondHandPublishService.purchase(publish.id, seller.id)
        assertEquals(balanceA, seller.balance)
        assertEquals(0, publish.status)
        assertEquals(403, result.code)
    }

    @Test
    fun testBuySecondTime() {
        val seller = studentA
        val buyer = studentB
        val other = studentC
        val balanceA = studentA.balance
        val balanceB = studentB.balance
        val balanceC = studentC.balance
        val book = book
        val price = 1000
        val exchange = 0

        secondHandPublishService.insert(seller.id, book.isbn, price, exchange)
        val publish = secondHandPublishService.selectPublishByStudentId(seller.id)?.data?.firstOrNull { it.person == seller && it.book == book && it.price.toInt() == price && it.status == 0L }!!
        assertEquals(balanceA, seller.balance)
        assertEquals(balanceB, buyer.balance)
        assertEquals(0, publish.status)


        secondHandPublishService.purchase(publish.id, buyer.id)
        val find = secondHandFindService.selectByStudentId(buyer.id)?.data?.firstOrNull { it.person == buyer && it.book == book && it.price.toInt() == price && it.status == 1L }!!
        assertEquals(balanceA, seller.balance)
        assertEquals(balanceB - price, buyer.balance)
        assertEquals(balanceC, other.balance)
        assertEquals(1, publish.status)
        assertEquals(1, find.status)

        val result = secondHandPublishService.purchase(publish.id, other.id)
        secondHandFindService.selectByStudentId(buyer.id)?.data?.firstOrNull { it.person == buyer && it.book == book && it.price.toInt() == price && it.status == 1L }!!
        assertEquals(balanceA, seller.balance)
        assertEquals(balanceB - price, buyer.balance)
        assertEquals(balanceC, other.balance)
        assertEquals(1, publish.status)
        assertEquals(1, find.status)
        assertEquals(403, result.code)

        secondHandFindService.changeStatusOk(find.id, buyer.id)
        assertEquals(balanceA + price, seller.balance)
        assertEquals(balanceB - price, buyer.balance)
        assertEquals(balanceC, other.balance)
        assertEquals(2, find.status)
    }
}


