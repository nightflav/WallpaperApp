package com.example.wallpaperapp.util

import android.content.Context
import android.graphics.Bitmap
import android.view.WindowManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

const val THEME = "theme"

fun Bitmap.cropBitmapFromCenterAndScreenSize(context: Context): Bitmap {
    var bitmap = this
    val screenWidth: Float
    val screenHeight: Float
    val bitmapWidth = bitmap.width.toFloat()
    val bitmapHeight = bitmap
        .height.toFloat()
    val display = context.getSystemService(WindowManager::class.java).defaultDisplay
    screenWidth = display.width.toFloat()
    screenHeight = display.height.toFloat()
    val bitmapRatio = (bitmapWidth / bitmapHeight)
    val screenRatio = (screenWidth / screenHeight)
    val bitmapNewWidth: Int
    val bitmapNewHeight: Int
    if (screenRatio > bitmapRatio) {
        bitmapNewWidth = screenWidth.toInt()
        bitmapNewHeight = (bitmapNewWidth / bitmapRatio).toInt()
    } else {
        bitmapNewHeight = screenHeight.toInt()
        bitmapNewWidth = (bitmapNewHeight * bitmapRatio).toInt()
    }
    bitmap = Bitmap.createScaledBitmap(
        bitmap, bitmapNewWidth,
        bitmapNewHeight, true
    )
    val bitmapGapX: Int = ((bitmapNewWidth - screenWidth) / 2.0f).toInt()
    val bitmapGapY: Int = ((bitmapNewHeight - screenHeight) / 2.0f).toInt()
    bitmap = Bitmap.createBitmap(
        bitmap,
        bitmapGapX,
        bitmapGapY,
        screenWidth.toInt(),
        screenHeight.toInt()
    )
    return bitmap
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Suppress("UNCHECKED_CAST")
@Composable
internal inline fun <reified T : ViewModel> NavHostController.viewModelFromHolder(
    holderRoute: String,
    crossinline viewModelInstanceCreator: () -> T
) = viewModel(
    modelClass = T::class.java,
    viewModelStoreOwner = remember { this.getBackStackEntry(holderRoute) },
    factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return viewModelInstanceCreator() as T
        }
    }
)