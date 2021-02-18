@file:Suppress("unused")
package com.example.mybikeshops

import android.app.Application
import com.google.android.libraries.places.api.Places

class MyBikeShopsApp : Application() {

   override fun onCreate() {
        super.onCreate()
        // Initialize the Google Places SDK
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, BuildConfig.PLACES_API_KEY)
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        Places.deinitialize()
    }
}