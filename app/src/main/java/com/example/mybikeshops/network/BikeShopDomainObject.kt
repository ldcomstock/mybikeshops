package com.example.mybikeshops.network

import com.example.mybikeshops.BuildConfig
import com.example.mybikeshops.bikeshoplist.BikeShopItem
import com.squareup.moshi.Json

data class BikeShopDomainObject(
    @Json(name = "name") val name: String,
    @Json(name = "photos") val photos: List<PhotoDomainObject> = listOf(),
    @Json(name = "vicinity") val address: String,
)

// Extension functions relevant to the domain object

// converts a list of BikeShopDomainObject to list of BikeShopItems to be used in the list adapter
fun List<BikeShopDomainObject>.toBikeShopItems(): List<BikeShopItem> {
    lateinit var bikeShopItem: BikeShopItem
    val bikeShops: MutableList<BikeShopItem> = mutableListOf()
    for (bikeShopDomainObject in this) {
        val photoUrl = bikeShopDomainObject.photos.generatePhotoUrl()
        bikeShopItem = BikeShopItem(
            bikeShopDomainObject.name,
            photoUrl,
            bikeShopDomainObject.address,
        )
        bikeShops.add(bikeShopItem)
    }
    return bikeShops
}

fun List<PhotoDomainObject>?.generatePhotoUrl(): String {
    var photoUrl = ""

    if (!this.isNullOrEmpty()) {
        // grab properties for first photo in the list and create url
        val photoDomainObject = this[0]
        val photoReference = photoDomainObject.photoReference
        val photoMaxHeight = photoDomainObject.height.toString()
        photoUrl = BuildConfig.BASE_URL + BuildConfig.PHOTOS_ENDPOINT + "?maxheight=" + photoMaxHeight + "&photoreference=" + photoReference + "&key=" + BuildConfig.PLACES_API_KEY
    }
    return photoUrl

}