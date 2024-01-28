package com.ark.nikestore

import android.app.Application
import com.ark.nikestore.data.repo.UserRepository
import com.facebook.drawee.backends.pipeline.Fresco
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var userRepository: UserRepository
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        Fresco.initialize(this)

        userRepository.loadToken()  //Initialize TokenContainer from SharedPref

    }
}