package com.example.mybikeshops.bikeshoplist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mybikeshops.data.BikeShopsRepository

/**
 * Factory for ViewModels
 */
class ViewModelFactory(private val repository: BikeShopsRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BikeShopListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BikeShopListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}