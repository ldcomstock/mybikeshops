package com.example.mybikeshops.network

import com.example.mybikeshops.BuildConfig
import com.example.mybikeshops.bikeshoplist.BikeShopItem
import com.squareup.moshi.Json

data class BikeShopResponse(
    @Json(name = "name") val name: String,
    @Json(name = "photos") val photos: List<PhotoResponse> = listOf(),
    @Json(name = "vicinity") val address: String,
)

// Extension functions used to map response objects from the server (DTOs)
// to domain objects (BikeShopItems) to be used in views

// converts a list of BikeShopResponses to list of BikeShopItems to be used in the list adapter
fun List<BikeShopResponse>.toBikeShopItems(): List<BikeShopItem> {
    val bikeShops: MutableList<BikeShopItem> = mutableListOf()
    for (bikeShopResponse in this) {
        bikeShops.add(bikeShopResponse.toBikeShopItem())
    }
    return bikeShops
}

// converts a single BikeShopResponse to a BikeShopItem
fun BikeShopResponse.toBikeShopItem(): BikeShopItem {

    val photoUrl = this.photos.generatePhotoUrl()
    return BikeShopItem(
        this.name,
        photoUrl,
        this.address)
}

// returns the photo url using the properties from the first photo in the response list\
fun List<PhotoResponse>?.generatePhotoUrl(): String {
    var photoUrl = ""

    if (!this.isNullOrEmpty()) {
        val photoResponse = this[0]
        val photoReference = photoResponse.photoReference
        val photoMaxHeight = photoResponse.height.toString()
        photoUrl = BuildConfig.BASE_URL + BuildConfig.PHOTOS_ENDPOINT +
                "?maxheight=" +
                photoMaxHeight +
                "&photoreference=" +
                photoReference + "&key=" +
                BuildConfig.PLACES_API_KEY
    }
    return photoUrl
}