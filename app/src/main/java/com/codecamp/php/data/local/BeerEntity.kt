package com.codecamp.php.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "beer_table")
data class BeerEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val tagline: String,
    val description: String,
    val firstBrewed: String,
    val imageUrl: String
)
