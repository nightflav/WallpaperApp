package com.example.wallpaperapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.example.wallpaperapp.util.THEME
import kotlinx.coroutines.launch

@Composable
fun SettingScreen(
    dataStore: DataStore<Preferences>,
    currTheme: Boolean
) {
    val coroutine = rememberCoroutineScope()
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Settings",
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondaryContainer),
        )
        Row {
            Text(text = "Change theme", modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = currTheme,
                onCheckedChange = {
                    coroutine.launch {
                        dataStore.edit { settings ->
                            settings[booleanPreferencesKey(THEME)] = it
                        }
                    }
                }
            )
        }
    }
}
