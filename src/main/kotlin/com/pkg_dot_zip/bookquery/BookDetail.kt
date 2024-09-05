package com.pkg_dot_zip.bookquery

import com.google.gson.annotations.SerializedName
import com.pkg_dot_zip.util.ISBN

class BookDetail {
    @SerializedName("title")
    val title: String? = null

    @SerializedName("subtitle")
    val subTitle: String? = null

    @SerializedName("publishedDate")
    val publishedDate: String? = null

    @SerializedName("pageCount")
    val pageCount: Int = 0

    @SerializedName("printType")
    val printType: String? = null

    @SerializedName("language")
    val language: String? = null

    @SerializedName("authors")
    val authors: List<String>? = null

    @SerializedName("description")
    val description: String? = null

    // NOTE: We declare this one by hand.
    var isbn: ISBN? = null
}
