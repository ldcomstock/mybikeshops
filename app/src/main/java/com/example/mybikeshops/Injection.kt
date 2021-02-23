package com.example.mybikeshops

import androidx.lifecycle.ViewModelProvider
import com.example.mybikeshops.bikeshoplist.ViewModelFactory
import com.example.mybikeshops.network.BikeShopsApiService
import com.example.mybikeshops.data.BikeShopsRepository

/**
 * Class that handles object creation.
 * In this way, objects can be passed as parameters in the constructors and then replaced for
 * testing, where needed.
 */
object Injection {

    private fun provideBikeShopsRepository(): BikeShopsRepository {
        return BikeShopsRepository(BikeShopsApiService.create())
    }

    fun provideViewModelFactory(): ViewModelProvider.Factory {
        return ViewModelFactory(provideBikeShopsRepository())
    }
}