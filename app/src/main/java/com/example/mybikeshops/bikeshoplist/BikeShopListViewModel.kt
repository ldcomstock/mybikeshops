package com.example.mybikeshops.bikeshoplist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.mybikeshops.data.BikeShopsRepository
import com.example.mybikeshops.network.toBikeShopItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BikeShopListViewModel(private val repository: BikeShopsRepository) : ViewModel() {

    companion object {
        const val LOCATION_NONE = "0,0"
    }

    private val _location = MutableLiveData<String>()
    private val _selectedBikeShop = MutableLiveData<BikeShopItem?>()
    private var _currentLocation: String? = null

    val selectedBikeShop: LiveData<BikeShopItem?>
        get() = _selectedBikeShop

    val currentLocation: String?
        get() = _currentLocation

    init {
        _location.value = LOCATION_NONE
    }

    fun fetchNearbyBikeShops(): Flow<PagingData<BikeShopItem>> {
        val locationQuery = _currentLocation ?: LOCATION_NONE
        return repository.letBikeShopsFlow(locationQuery = locationQuery)
            .map { bikeShopResponse -> bikeShopResponse.map {
                it.toBikeShopItem()
            }
        }.cachedIn(viewModelScope)  // ensures the paged data survives configuration changes
    }

    fun displayBikeShopDetails(bikeShop: BikeShopItem) {
        // updates LiveData field being observed by the ListFragment
        // which then handles navigation to the detail view
        _selectedBikeShop.value = bikeShop
    }

    // clear selected bike shop so it doesn't retrigger navigation to detail view
    fun displayBikeShopDetailsComplete() {
        _selectedBikeShop.value = null
    }

    fun setLocation(newLocation: String) {
        _currentLocation = newLocation
    }
}