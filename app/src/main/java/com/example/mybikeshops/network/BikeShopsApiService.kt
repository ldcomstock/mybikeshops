package com.example.mybikeshops.network

import com.example.mybikeshops.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val PLACE_TYPE_TO_FETCH = "bicycle_store"
private const val RANKBY_DISTANCE = "distance"

/**
 * Note: looking at the Places API, it doesn't look like there is any way to specify the
 * number of results to be returned and 20 items is the default page size. I wasn't sure
 * how to reconcile this with the acceptance criteria item: fetch 10 location results at a time.
 */
interface BikeShopsApiService {
    @GET("place/nearbysearch/json")
    suspend fun searchBikeShops(
        @Query("key") key: String = BuildConfig.PLACES_API_KEY,
        @Query("type", encoded = true) query: String = PLACE_TYPE_TO_FETCH,
        @Query("location", encoded = true) location: String,
        @Query("rankby") rankby: String = RANKBY_DISTANCE,
        @Query("pagetoken") pagetoken: String? = null,
    ): BikeShopsResponse

    companion object {

        fun create(): BikeShopsApiService {
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

            // only use logging for debug builds
            val logLevel = when {
                BuildConfig.DEBUG -> HttpLoggingInterceptor.Level.BODY
                else -> HttpLoggingInterceptor.Level.NONE
            }
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(logLevel)

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

           return Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .client(okHttpClient)
                .baseUrl(BuildConfig.BASE_URL)
                .build()
                .create(BikeShopsApiService::class.java)
        }
    }
}