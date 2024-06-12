package com.ankit.foodfie.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert


@Dao
interface ItemDao {

    @Insert
    fun insertOrder(orderEntity: OrderEntity)

    @Delete
    fun deleteOrder(orderEntity: OrderEntity)

}