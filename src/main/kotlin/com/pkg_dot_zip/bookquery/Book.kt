package com.pkg_dot_zip.com.pkg_dot_zip.bookquery

import com.google.gson.annotations.SerializedName

class Book {
    @SerializedName("id")
    private val id: String? = null

    @SerializedName("volumeInfo")
    val bookDetail: BookDetail? = null
}
