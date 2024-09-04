package com.pkg_dot_zip.com.pkg_dot_zip.bookquery

import com.google.gson.annotations.SerializedName

class JsonResult {
    @SerializedName("kind")
    private val kind: String? = null

    @SerializedName("totalItems")
    private val totalItems = 0

    @SerializedName("items")
    private val books: List<Book>? = null

    fun getBook(): Book? = books?.first()

    fun getBookDetail(): BookDetail = books?.first()?.bookDetail!!
}