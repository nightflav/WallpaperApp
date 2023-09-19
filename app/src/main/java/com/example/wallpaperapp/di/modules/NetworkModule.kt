package com.example.wallpaperapp.di.modules

import com.example.wallpaperapp.BuildConfig
import com.example.wallpaperapp.data.network.UnsplashApi
import com.example.wallpaperapp.di.ApplicationScope
import com.example.wallpaperapp.di.qualifiers.AccessKeyQualifier
import com.example.wallpaperapp.di.qualifiers.BaseUrlQualifier
import com.example.wallpaperapp.di.subcomponents.categories.CategoriesSubcomponent
import com.example.wallpaperapp.di.subcomponents.images.BigImageSubcomponent
import com.example.wallpaperapp.di.subcomponents.images.ImagesSubcomponent
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit

@Module(
    subcomponents =
    [BigImageSubcomponent::class, ImagesSubcomponent::class, CategoriesSubcomponent::class]
)
class NetworkModule {

    @BaseUrlQualifier
    @Provides
    fun provideBaseUrl(): String = BuildConfig.BASE_URL

    @AccessKeyQualifier
    @Provides
    fun provideAccessKey(): String = BuildConfig.ACCESS_KEY

    @ApplicationScope
    @Provides
    fun provideOkHttpClient(
        @AccessKeyQualifier accessKey: String
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Client-ID $accessKey")
                .build()
            chain.proceed(request)
        }
        .build()

    @Provides
    fun provideConverterFactory(): Converter.Factory {
        val contentType = "application/json".toMediaType()
        val json = Json {
            ignoreUnknownKeys = true
        }
        return json.asConverterFactory(contentType)
    }

    @ApplicationScope
    @Provides
    fun provideRetrofit(
        converterFactory: Converter.Factory,
        @BaseUrlQualifier baseUrl: String,
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(converterFactory)
        .client(okHttpClient)
        .build()

    @ApplicationScope
    @Provides
    fun provideUnsplashApi(
        retrofit: Retrofit
    ): UnsplashApi = retrofit.create(UnsplashApi::class.java)

}