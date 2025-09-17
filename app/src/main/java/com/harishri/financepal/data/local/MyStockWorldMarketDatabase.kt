package com.harishri.financepal.data.local



import androidx.room.Database
import androidx.room.RoomDatabase

import com.harishri.financepal.data.local.dao.UserDao
import com.harishri.financepal.data.local.dao.WatchlistDao
import com.harishri.financepal.data.local.entity.UserEntity
import com.harishri.financepal.data.local.entity.WatchlistEntity


/**
 * The Room database for the application.
 * This abstract class serves as the main access point for the underlying connection
 * to the app's persisted data. It defines the entities and provides the DAOs.
 */

@Database(
    entities = [
        WatchlistEntity::class,
        UserEntity::class
    ],
    version = 1, // Start with version 1
    exportSchema = false // We are not exporting the schema
)
//@TypeConverters(Converters::class)
abstract class MyStockWorldMarketDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun watchlistDao(): WatchlistDao
}
