package com.example.wallpaperapp.di.modules

import com.example.wallpaperapp.data.repository.UnsplashRepositoryImpl
import com.example.wallpaperapp.domain.repository.UnsplashRepository
import dagger.Binds
import dagger.Module

@Module
interface DataModule {

    @Binds
    fun bindUnsplashRepository(repo: UnsplashRepositoryImpl): UnsplashRepository

}