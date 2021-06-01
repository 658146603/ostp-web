package top.ostp.web.service

import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import top.ostp.web.mapper.BookOrderMapper
import top.ostp.web.mapper.ClazzMapper
import top.ostp.web.mapper.CollegeMapper
import top.ostp.web.mapper.CourseOpenMapper
import top.ostp.web.model.Clazz
import top.ostp.web.model.College
import java.net.URLEncoder

@Service
class BookOrderService {
    @Autowired
    lateinit var orderMapper: BookOrderMapper

    @Autowired
    lateinit var clazzMapper: ClazzMapper

    @Autowired
    lateinit var openMapper: CourseOpenMapper

    @Autowired
    lateinit var collegeMapper: CollegeMapper

    private fun export(workbook: XSSFWorkbook, page: String, header: List<String>, data: List<List<String>>): XSSFWorkbook {
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

    private fun exportEmpty(workbook: XSSFWorkbook, page: String, header: List<String>): XSSFWorkbook {
        val sheet = workbook.createSheet(page)
        sheet.createRow(0).let {
            header.forEachIndexed { index, s ->
                it.createCell(index).setCellValue(s)
            }
        }
        return workbook
    }

    fun getBookOrderListByClazz(clazzId: Long, received: Boolean): Pair<String, XSSFWorkbook> {
        val years = listOf(2016, 2017, 2018, 2019, 2020, 2021, 2022)
        val semesters = listOf(1, 2, 3)
        val header = listOf("学号", "姓名", "教材名称", "教材ISBN", "价格", "学年", "学期", "状态")
        val pairs = years * semesters

        val workbook = XSSFWorkbook()
        val clazz = clazzMapper.selectById(clazzId) ?: return getStudentXlsxFileHeader(null) to
                exportEmpty(workbook, "${years[0]}学年第${semesters[0]}学期", header)

        for (pair in pairs) {
            val year = pair.first
            val semester = pair.second
            val orders = orderMapper.selectByYearSemesterAndClazz(clazz.id, year, semester)
            if (received) {
                orderMapper.setReceivedByClazz(clazz.id, year, semester)
            }

            val data = ArrayList<ArrayList<String>>()

            orders.forEach { order ->
                val row = ArrayList<String>()
                row.add("${order.student?.id}")
                row.add("${order.student?.name}")
                row.add("${order.book?.name}")
                row.add("${order.book?.isbn}")
                row.add("${order.price / 100.0}")
                row.add("${order.year}")
                row.add("${order.semester}")
                row.add(if (order.received == 0) "未发放" else "已发放")
                data.add(row)
            }

            if (data.size == 0) continue

            val page = "${year}学年第${semester}学期"
            export(workbook, page, header, data)
        }

        if (workbook.count() == 0) {
            exportEmpty(workbook, "${years[0]}学年第${semesters[0]}学期", header)
        }

        return getStudentXlsxFileHeader(clazz) to workbook
    }

    fun getBookRequestListByCollege(collegeId: Long): Pair<String, XSSFWorkbook> {
        val years = listOf(2016, 2017, 2018, 2019, 2020, 2021, 2022)
        val semesters = listOf(1, 2, 3)
        val header = listOf("工号", "姓名", "课程编号", "课程名称", "教材名称", "教材ISBN", "价格", "学年", "学期", "状态")
        val pairs = years * semesters
        val workbook = XSSFWorkbook()
        val college = collegeMapper.selectById(collegeId) ?: return getTeacherXlsxFileHeader(null) to
                exportEmpty(workbook, "${years[0]}学年第${semesters[0]}学期", header)

        for (pair in pairs) {
            val year = pair.first
            val semester = pair.second
            val opens = openMapper.selectByCollegeAndYearSemester(college.id, year, semester)

            val data = ArrayList<ArrayList<String>>()

            opens.forEach { open ->
                val row = ArrayList<String>()
                row.add(open.teacher.id)
                row.add(open.teacher.name)
                row.add(open.course.id)
                row.add(open.course.name)
                row.add(open.book.name)
                row.add(open.book.isbn)
                row.add("${open.book.price / 100.0}")
                row.add("${open.year}")
                row.add("${open.semester}")
                row.add(if (open.received == 0) "未领取" else "已领取")
                data.add(row)
            }

            if (data.size == 0) continue

            val page = "${year}学年第${semester}学期"
            export(workbook, page, header, data)
        }

        if (workbook.count() == 0) {
            exportEmpty(workbook, "${years[0]}学年第${semesters[0]}学期", header)
        }

        return getTeacherXlsxFileHeader(college) to workbook
    }

    private fun getStudentXlsxFileHeader(clazz: Clazz?): String {
        val filename = if (clazz == null || clazz.major?.name == null) {
            "[学生订书列表]班级不存在.xlsx"
        } else {
            "[学生订书列表]${clazz.major?.name}_${clazz.name}.xlsx"
        }
        return "attachment;filename=${URLEncoder.encode(filename, "utf-8")}"
    }

    private fun getTeacherXlsxFileHeader(college: College?): String {
        val filename = if (college == null) {
            "[教师领取列表]学院不存在.xlsx"
        } else {
            "[教师领取列表]${college.name}.xlsx"
        }

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
