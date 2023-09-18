package com.example.wallpaperapp.di.qualifiers

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class BaseUrlQualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class AccessKeyQualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class SecretKeyQualifier