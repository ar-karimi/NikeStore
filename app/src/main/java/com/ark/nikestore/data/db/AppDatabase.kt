package com.ark.nikestore.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ark.nikestore.data.Product
import com.ark.nikestore.data.repo.source.ProductLocalDataSource

@Database(entities = [Product::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductLocalDataSource
}