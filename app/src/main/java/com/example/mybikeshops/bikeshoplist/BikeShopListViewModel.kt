package com.example.mybikeshops.bikeshoplist

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.mybikeshops.network.BikeShopsApi
import com.example.mybikeshops.network.toBikeShopItems
import kotlinx.coroutines.launch

class BikeShopListViewModel(application: Application) : AndroidViewModel(application) {

    private val LOCATION_NONE = "0,0"

    // internal mutable LiveData fields
    private val _bikeShops = MutableLiveData<List<BikeShopItem>>()
    private val _response = MutableLiveData<String>()
    private val _location = MutableLiveData<String>()
    private val _selectedBikeShop = MutableLiveData<BikeShopItem?>()
    private var _currentBikeShop = MutableLiveData<BikeShopItem>()

    // corresponding external immutable LiveData fields
    private val location: LiveData<String>
        get() = _location

    val bikeShops: LiveData<List<BikeShopItem>>
        get() = _bikeShops

    val selectedBikeShop: LiveData<BikeShopItem?>
        get() = _selectedBikeShop

    fun setCurrentBikeShop(bikeShopItem: BikeShopItem) {
        _currentBikeShop.value = bikeShopItem
    }

    init {
        _location.value = LOCATION_NONE
        clearResults()
    }

    fun getBikeShops() {
        viewModelScope.launch {
            try {
                val locationToQuery = location.value
                val responseObject = locationToQuery?.let { BikeShopsApi.RETROFIT_SERVICE.getBikeShops(location = it) }
                // convert result domain objects to view friendly objects
                _bikeShops.value = responseObject?.results?.toBikeShopItems()
            } catch (e: Exception) {
                _bikeShops.value = listOf()
                _response.value = "Failure: ${e.message}"
                Log.e("ListViewModel", "Failure: ${e.message}")
            }
        }
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

    fun clearResults() {
        _bikeShops.value = listOf()
    }

    fun setLocation(newLocation: String) {
        _location.value = newLocation
    }

    fun locationChanged(currentSelection: String): Boolean {
        return location.value != currentSelection
    }
}