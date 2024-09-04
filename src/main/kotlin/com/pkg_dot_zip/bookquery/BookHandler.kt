package com.pkg_dot_zip.com.pkg_dot_zip.bookquery

import com.google.gson.Gson
import java.io.InputStreamReader
import java.io.Reader
import java.net.URL

object BookHandler {

    private const val CHARSET = "UTF-8"

    // Here store books infinitely. Would eventually be problematic.
    private val cachedBookList = ArrayList<BookDetail>()

    private fun getBookDetail(isbn: String) : BookDetail {
        if (cachedBookList.find { it.isbn == isbn } != null) {
            return cachedBookList.find { it.isbn == isbn }!!
        }

        // No entries found. Create one.
        val bookInfo = URL("https://www.googleapis.com/books/v1/volumes?q=isbn:$isbn")

        val input = bookInfo.openStream()
        val reader: Reader = InputStreamReader(input, CHARSET)

        cachedBookList.add(Gson().fromJson(reader, JsonResult::class.java).getBookDetail().apply { this.isbn = isbn })
        return cachedBookList.last
    }

    fun printAll(isbn: String) {
        val result = getBookDetail(isbn)

        //Output
        println("ISBN: $isbn")
        println("Title: ${result.title}")
        println("Subtitle: ${result.subTitle}")
        result.authors?.forEach { println("Author: $it") }
        println("Description: ${result.description}")
        println("Pages: ${result.pageCount}")
        println("Language: ${result.language}")
    }

    fun getTitle(isbn: String) : String = "Title: ${getBookDetail(isbn).title}"

    fun getSubtitle(isbn: String) : String = "Subtitle: ${getBookDetail(isbn).subTitle}"

    fun getAuthors(isbn: String) : String = "Authors: ${getBookDetail(isbn).authors?.joinToString()}"
}