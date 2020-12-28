package top.ostp.web.service

import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import top.ostp.web.mapper.BookOrderMapper
import top.ostp.web.mapper.ClazzMapper
import top.ostp.web.mapper.CourseOpenMapper
import top.ostp.web.model.Clazz
import java.net.URLEncoder

@Service
class BookOrderService {
    @Autowired
    lateinit var orderMapper: BookOrderMapper

    @Autowired
    lateinit var clazzMapper: ClazzMapper

    @Autowired
    lateinit var openMapper: CourseOpenMapper

    private fun export(
        workbook: XSSFWorkbook,
        page: String,
        header: List<String>,
        data: List<List<String>>,
    ): XSSFWorkbook {
        val sheet = workbook.createSheet(page)
        sheet.createRow(0).let {
            header.forEachIndexed { index, s ->
                it.createCell(index).setCellValue(s)
            }
        }

        for (index in 1..data.size) {
            val row = data[index - 1]
            sheet.createRow(index).let {
                header.forEachIndexed { index, _ ->
                    it.createCell(index).also { it.setCellValue(row[index]) }
                }
            }
        }

        sheet.defaultColumnWidth = 30

        return workbook
    }

    fun getBookOrderListByClazz(clazzId: Long, received: Boolean): Pair<Clazz, XSSFWorkbook> {
        val workbook = XSSFWorkbook()
        val clazz = clazzMapper.selectById(clazzId) ?: return Clazz() to workbook

        val years = listOf(2016, 2017, 2018, 2019, 2020, 2021, 2022)
        val semesters = listOf(1, 2, 3)

        val pairs = years * semesters

        for (pair in pairs) {
            val year = pair.first
            val semester = pair.second
            val orders = orderMapper.selectByYearSemesterAndClazz(clazz.id, year, semester)
            if (received) {
                orderMapper.setReceivedByClazz(clazz.id, year, semester)
            }
            val header = listOf("学号", "姓名", "教材名称", "教材ISBN", "价格", "学年", "学期", "状态")
            val data = ArrayList<ArrayList<String>>()

            orders.forEach { order ->
                val row = ArrayList<String>()
                row.add("${order.student?.id}")
                row.add("${order.student?.name}")
                row.add("${order.book?.name}")
                row.add("${order.book?.isbn}")
                row.add("${order.price / 100}")
                row.add("${order.year}")
                row.add("${order.semester}")
                row.add(if (order.received == 0) "未发放" else "已发放")
                data.add(row)
            }

            if (data.size == 0) continue

            val page = "${year}学年第${semester}学期"
            export(workbook, page, header, data)
        }

        return clazz to workbook
    }

    fun getFileHeader(clazz: Clazz): String {
        val filename = "订书列表[${clazz.major?.name ?: "无此班级"}_${clazz.name}].xlsx"
        return "attachment;filename=${URLEncoder.encode(filename, "utf-8")}"
    }

    private operator fun <E> List<E>.times(other: List<E>): List<Pair<E, E>> {
        val list = ArrayList<Pair<E, E>>()
        this.forEach { a ->
            other.forEach { b ->
                list.add(a to b)
            }
        }
        return list
    }
}
