package com.example.mybikeshops.network

data class BikeShopListDomainObject(
    val status: String,
    val results: List<BikeShopDomainObject>,
    val html_attributions: List<Any>?,
    val next_page_token: String?,
)