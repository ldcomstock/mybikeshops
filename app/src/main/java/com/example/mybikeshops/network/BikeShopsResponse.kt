package com.example.mybikeshops.network

import com.squareup.moshi.Json

data class BikeShopsResponse(
    val status: String,
    @Json(name = "results") val bikeShops: List<BikeShopResponse>,
    @Json(name= "html_attributions") val htmlAttributions: List<Any>?,
    @Json(name = "next_page_token") val nextPageToken: String?,
)