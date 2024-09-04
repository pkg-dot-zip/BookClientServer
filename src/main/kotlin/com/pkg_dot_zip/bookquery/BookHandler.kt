package com.pkg_dot_zip.com.pkg_dot_zip.bookquery

import com.google.gson.Gson
import java.io.InputStreamReader
import java.io.Reader
import java.net.URL

object BookHandler {

    private const val CHARSET = "UTF-8"

    fun printAll(isbn: String) {
        val bookInfo = URL("https://www.googleapis.com/books/v1/volumes?q=isbn:$isbn")

        val input = bookInfo.openStream()
        val reader: Reader = InputStreamReader(input, CHARSET)
        val result = Gson().fromJson(reader, JsonResult::class.java)

        //Output
        println("ISBN: $isbn")
        println("Title: " + result.getBookDetail().title)
        println("Subtitle: " + result.getBookDetail().subTitle)
        result.getBookDetail().authors?.forEach { println("Author: $it") }
        println("Description: " + result.getBookDetail().description)
        println("Pages: " + result.getBookDetail().pageCount)
        println("Language: " + result.getBookDetail().language)
    }
}