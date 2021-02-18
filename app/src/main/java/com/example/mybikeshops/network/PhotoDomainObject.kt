package com.example.mybikeshops.network

import com.squareup.moshi.Json

data class PhotoDomainObject(
    val height: Int,
    val width: Int,
    @Json(name = "html_attributions") val attributions: List<String>,
    @Json(name = "photo_reference") val photoReference: String
) {
    constructor(): this(
        0,
        0,
        listOf(),
        ""
    )
}