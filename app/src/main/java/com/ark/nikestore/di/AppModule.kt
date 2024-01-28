package com.ark.nikestore.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.ark.nikestore.data.db.AppDatabase
import com.ark.nikestore.data.repo.source.ProductDataSource
import com.ark.nikestore.di.repositories.productRepository.ProductLocalDataSourceQualifier
import com.ark.nikestore.services.FrescoImageLoadingService
import com.ark.nikestore.services.ImageLoadingService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideImageLoadingService(): ImageLoadingService = FrescoImageLoadingService()

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("app_sharedPref", Application.MODE_PRIVATE)
    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "db_app").build()

    @ProductLocalDataSourceQualifier
    @Singleton
    @Provides
    fun provideRoomDao(appDatabase: AppDatabase): ProductDataSource = appDatabase.productDao()

}