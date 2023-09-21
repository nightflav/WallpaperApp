package com.example.wallpaperapp.core

import android.app.Application
import com.example.wallpaperapp.di.AppComponent
import com.example.wallpaperapp.di.DaggerAppComponent

class BaseApp : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
    }

}