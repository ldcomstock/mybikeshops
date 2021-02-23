package com.example.mybikeshops.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mybikeshops.network.BikeShopResponse
import com.example.mybikeshops.network.BikeShopsApiService
import retrofit2.HttpException
import java.io.IOException

class BikeShopsPagingSource(
    private val service: BikeShopsApiService,
    private val locationQuery: String
) : PagingSource<String, BikeShopResponse>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, BikeShopResponse> {
        return try {
            // params.key will be null on initial page load - subsequent loads will hold the
            // page token corresponding to the current page which will be used as prev key
            // i.e. the last response.nextPageToken which was used to get this current page
            val currentPageToken = params.key
            val response = service.searchBikeShops(location = locationQuery, pagetoken = currentPageToken)
            val bikeShopList = response.bikeShops
            val nextPageToken = if (bikeShopList.isEmpty()) null else response.nextPageToken
            LoadResult.Page(
                bikeShopList,
                prevKey = currentPageToken,
                nextKey = nextPageToken
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<String, BikeShopResponse>): String? {
        return state.anchorPosition?.let { state.closestPageToPosition(it)?.nextKey }
    }
}