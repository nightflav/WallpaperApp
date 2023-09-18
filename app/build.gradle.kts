import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("kotlinx-serialization")
}

android {
    namespace = "com.example.wallpaperapp"
    compileSdk = 33

    defaultConfig {
        compileSdkPreview = "UpsideDownCake"
        applicationId = "com.example.wallpaperapp"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    val properties = Properties()
    properties.load(project.rootProject.file("secrets.properties").inputStream())

    buildTypes {
        debug {
            buildConfigField("String", "BASE_URL", "\"https://api.unsplash.com/\"")
            buildConfigField("String", "ACCESS_KEY", properties.getProperty("ACCESS_KEY"))
            buildConfigField("String", "SECURITY_KEY", properties.getProperty("SECRET_KEY"))
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.10.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.navigation:navigation-compose:2.7.2")
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    val daggerVersion = "2.48"
    implementation("com.google.dagger:dagger:$daggerVersion")
    ksp("com.google.dagger:dagger-compiler:$daggerVersion")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
    implementation("com.github.bumptech.glide:compose:1.0.0-alpha.5")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
}