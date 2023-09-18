// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories { mavenCentral() }

    dependencies {
        val kotlinVersion = "1.9.10"
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion")
    }
}

plugins {
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.google.devtools.ksp") version "1.9.10-1.0.13" apply false
}