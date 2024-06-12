package com.ankit.foodfie.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface FoodDao {

    @Insert
    fun insertFood(foodEntity: FoodEntity)
    @Delete
    fun deleteFood(foodEntity: FoodEntity)

    @Query("SELECT * FROM food_table")
    fun getAllFood():List<FoodEntity>

    @Query("SELECT * FROM food_table WHERE food_id = :food_id")
    fun getFoodByID(food_id:String) : FoodEntity
}