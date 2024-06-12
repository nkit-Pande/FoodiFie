package com.ankit.foodfie.database

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [FoodEntity::class,OrderEntity::class], version = 1)

abstract class FoodDatabase : RoomDatabase(){


    abstract fun foodDao():FoodDao

    abstract fun itemDao():ItemDao
}