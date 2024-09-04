package com.pkg_dot_zip.com.pkg_dot_zip.bookquery

import com.google.gson.Gson
import com.pkg_dot_zip.com.pkg_dot_zip.util.ISBN
import java.io.InputStreamReader
import java.io.Reader
import java.net.URI

object BookHandler {

    private const val CHARSET = "UTF-8"

    // Here store books infinitely. Would eventually be problematic.
    private val cachedBookList = ArrayList<BookDetail>()

    private fun getBookDetail(isbn: ISBN): BookDetail {
        if (cachedBookList.find { it.isbn == isbn } != null) {
            return cachedBookList.find { it.isbn == isbn }!!
        }

        // No entries found. Create one.
        val bookInfo = URI("https://www.googleapis.com/books/v1/volumes?q=isbn:$isbn").toURL()

        val input = bookInfo.openStream()
        val reader: Reader = InputStreamReader(input, CHARSET)

        cachedBookList.add(Gson().fromJson(reader, JsonResult::class.java).getBookDetail().apply { this.isbn = isbn })
        return cachedBookList.last()
    }

    fun getTitle(isbn: ISBN): String = "Title: ${getBookDetail(isbn).title}"

    fun getSubtitle(isbn: ISBN): String = "Subtitle: ${getBookDetail(isbn).subTitle}"

    fun getAuthors(isbn: ISBN): String = "Authors: ${getBookDetail(isbn).authors?.joinToString()}"
}