package com.example.wallpaperapp.data.network

import com.example.wallpaperapp.data.network.model.photo.NetworkPhotoItem
import com.example.wallpaperapp.data.network.model.topic.TopicNetworkItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UnsplashApi {

    @GET("topics")
    suspend fun getCategories(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 14
    ): Response<List<TopicNetworkItem>>

    @GET("topics/{id}/photos")
    suspend fun getPhotosByCategory(
        @Path("id") id: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 14,
        @Query("orientation") orientation: String = "portrait"
    ): Response<List<NetworkPhotoItem>>

    @GET("/photos/{id}")
    suspend fun getPhotoById(
        @Path("id") id: String,
    ): Response<NetworkPhotoItem>

}