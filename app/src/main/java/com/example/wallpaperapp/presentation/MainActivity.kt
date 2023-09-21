package com.example.wallpaperapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.wallpaperapp.core.BaseApp
import com.example.wallpaperapp.core.util.THEME
import com.example.wallpaperapp.core.util.dataStore
import com.example.wallpaperapp.presentation.screens.MainNavHost
import com.example.wallpaperapp.presentation.ui.theme.AppTheme
import kotlinx.coroutines.flow.map

class MainActivity : ComponentActivity() {

    private lateinit var navHostController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appComponent = (this.application as BaseApp).appComponent
        val dataStore = dataStore
        val useDarkTheme = dataStore.data.map {
            it[booleanPreferencesKey(THEME)] ?: false
        }
        setContent {
            AppTheme(
                useDarkTheme = useDarkTheme.collectAsState(initial = false).value
            ) {
                navHostController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    MainNavHost(
                        navController = navHostController,
                        modifier = Modifier.fillMaxSize(),
                        appComponent = appComponent,
                        application = this.application,
                        dataStore = dataStore,
                        currTheme = useDarkTheme.collectAsState(initial = false).value
                    )
                }
            }
        }
    }
}