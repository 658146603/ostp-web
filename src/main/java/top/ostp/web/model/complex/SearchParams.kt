package top.ostp.web.model.complex

import top.ostp.web.model.Book

data class SearchParams(var personId: String, var name: String, var course: String, var year: Int, var semester: Int) {
    fun toSearchParams2(book: Book): SearchParams2 {
        return SearchParams2(personId, book.isbn, year, semester)
    }
}

data class SearchParams2(var personId: String, var isbn: String, var year: Int, var semester: Int)