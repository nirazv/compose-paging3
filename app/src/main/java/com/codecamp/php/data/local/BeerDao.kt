package com.codecamp.php.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface BeerDao {

    @Query("DELETE FROM beer_table")
    suspend fun clearAll()

    @Upsert
    suspend fun addAll(beers: List<BeerEntity>)

    @Query("SELECT * FROM beer_table")
    fun getAll(): PagingSource<Int, BeerEntity>

}