package com.ankit.foodfie.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "orderList")
data class OrderEntity(
    @PrimaryKey val item_id:String,
    @ColumnInfo(name = "Price")val item_price:String,
    @ColumnInfo(name = "Name")val item_name:String

)