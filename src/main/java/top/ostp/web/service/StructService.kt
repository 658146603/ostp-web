package top.ostp.web.service

import top.ostp.web.model.complex.*

/**
 * a service to manage structure, includes
 * college, major, class, student, teacher
 */
class StructService {
    /**
     * get colleges, only show the data of the college
     * @param isTeacherGet whether to get the teacher data
     * @param endPoint determine the depth of the data to fetch
     */
    fun getColleges(isTeacherGet: Boolean, endPoint: StructEndPoint): List<CollegeAware> {
        return findColleges()
            .map { college -> getCollege(college.id, isTeacherGet, endPoint) };
    }

    @SuppressWarnings
    fun getCollege(collegeId: Int, isTeacherGet: Boolean, endPoint: StructEndPoint) : CollegeAware {
        val collegeAware = findCollegeById(collegeId)
        if (isTeacherGet) {
            collegeAware.teachers = findTeachersByCollegeId(collegeId)
        }
        if (endPoint == StructEndPoint.Major || endPoint == StructEndPoint.Class || endPoint == StructEndPoint.Students) {
            collegeAware.majors = findMajorsByCollegeId(collegeId)
                .map { major -> getMajor(major.id, endPoint) }
        }
        return collegeAware
    }

    @SuppressWarnings
    fun getMajor(majorId: Int, endPoint: StructEndPoint) : MajorAware {
        val majorAware = findMajorById(majorId)
        if (endPoint == StructEndPoint.Class || endPoint == StructEndPoint.Students) {
            majorAware.classes = findClassesByMajorId(majorId)
                .map { clazz -> getClass(clazz.id, endPoint) }
        }
        return majorAware
    }

    @SuppressWarnings()
    fun getClass(classId: Int, endPoint: StructEndPoint) : ClassAware {
        val classAware = findClassById(classId)
        if (endPoint == StructEndPoint.Students) {
            classAware.students = findStudentsByClassId(classId)
        }
        return classAware
    }

    private fun findCollegeById(collegeId: Int) : CollegeAware {
        // TODO: 通过collegeId获取学院
        throw NotImplementedError()
    }

    private fun findTeachersByCollegeId(collegeId: Int): List<Teacher> {
        // TODO: 获取某个学院下教师的数据
        throw NotImplementedError()
    }
    
    private fun findColleges(): List<CollegeAware> {
        // TODO: 获取所有学院的数据，仅学院
        throw NotImplementedError()
    }

    private fun findMajorById(majorId: Int) : MajorAware {
        // TODO: 通过majorId获取专业
        throw NotImplementedError()
    }

    private fun findMajorsByCollegeId(collegeId: Int): List<MajorAware> {
        // TODO: 通过collegeId获取所有
        throw NotImplementedError()
    }

    private fun findClassById(classId: Int) : ClassAware {
        // TODO: 通过classId获取班级
        throw NotImplementedError()
    }

    private fun findClassesByMajorId(majorId: Int) : List<ClassAware> {
        // TODO: 通过majorId获取所有班级
        throw NotImplementedError()
    }

    private fun findStudentsByClassId(classId: Int): List<Student> {
        // TODO: 通过classId获取学生
        throw NotImplementedError()
    }
}