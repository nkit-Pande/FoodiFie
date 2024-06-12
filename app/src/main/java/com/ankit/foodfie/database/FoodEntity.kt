package com.ankit.foodfie.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "food_table")
data class FoodEntity(
    @PrimaryKey val food_id: String,
    @ColumnInfo(name = "food_name")val food_name: String,
    @ColumnInfo(name = "food_price")val food_price: String,
    @ColumnInfo(name = "food_rating")val food_rating: String,
    @ColumnInfo(name = "food_img")val food_img: String
)