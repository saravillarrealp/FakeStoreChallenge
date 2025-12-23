package com.svillarreal.fakestorechallenge.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.svillarreal.fakestorechallenge.data.local.entity.FavoriteEntity
import com.svillarreal.fakestorechallenge.data.local.dao.FavoriteDao
import com.svillarreal.fakestorechallenge.data.local.dao.ProductDao
import com.svillarreal.fakestorechallenge.data.local.entity.ProductEntity

@Database(
    entities = [FavoriteEntity::class, ProductEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoritesDao(): FavoriteDao
    abstract fun productsDao(): ProductDao
}