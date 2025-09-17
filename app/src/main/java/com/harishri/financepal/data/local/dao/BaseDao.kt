package com.harishri.financepal.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Transaction
import androidx.room.Update

@Dao

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: T): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<T>): List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertIgnore(entity: T): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllIgnore(entities: List<T>): List<Long>


    // UPDATE OPERATIONS
    @Update
    suspend fun update(entity: T): Int

    @Update
    suspend fun updateAll(entities: List<T>): Int

    @Delete
    suspend fun delete(entity: T): Int

    @Delete
    suspend fun deleteAll(entities: List<T>): Int

    // UPSERT OPERATION (Insert or Update)
    @Transaction
    suspend fun upsert(entity: T) {
        val result = insertIgnore(entity)
        if (result == -1L) {
            update(entity)
        }
    }

    @Transaction
    suspend fun upsertAll(entities: List<T>) {
        entities.forEach { upsert(it) }
    }
}