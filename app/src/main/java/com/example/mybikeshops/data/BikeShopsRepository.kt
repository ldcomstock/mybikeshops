package com.example.mybikeshops.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.mybikeshops.network.BikeShopResponse
import com.example.mybikeshops.network.BikeShopsApiService
import kotlinx.coroutines.flow.Flow

class BikeShopsRepository(private val service: BikeShopsApiService) {

    fun letBikeShopsFlow(locationQuery: String,
                         pagingConfig: PagingConfig = getDefaultPageConfig())
    : Flow<PagingData<BikeShopResponse>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { BikeShopsPagingSource(service, locationQuery) }
        ).flow
    }

    /**
     * page size is the only required param, rest is optional - set page size to what API returns by default
     */
    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(initialLoadSize = 20, pageSize = 20, enablePlaceholders = false)
    }
}