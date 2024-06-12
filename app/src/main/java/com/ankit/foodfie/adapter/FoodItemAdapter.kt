package com.ankit.foodfie.adapter

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ankit.foodfie.R
import com.ankit.foodfie.activity.CartActivity
import com.ankit.foodfie.activity.ResDetails
import com.ankit.foodfie.model.CartData
import com.ankit.foodfie.model.FoodDetailItem
import kotlin.math.acos

class FoodItemAdapter(
    private val context: Context,
    private val foodItemList: ArrayList<FoodDetailItem>,
    private val listener: OnItemClickListener,
) : RecyclerView.Adapter<FoodItemAdapter.ItemViewHolder>() {

    private val orderPref: SharedPreferences =
        context.getSharedPreferences("OrderList", Context.MODE_PRIVATE)

    interface ItemClickListener {
        fun onAddToCartClicked(item: FoodDetailItem)
        fun onRemoveFromCartClicked(item: FoodDetailItem)
    }

    private var itemClickListener: ItemClickListener? = null

    fun setItemClickListener(listener: ItemClickListener) {
        itemClickListener = listener
    }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemId: TextView = view.findViewById(R.id.tv_resDetail_id)
        val itemName: TextView = view.findViewById(R.id.tv_resDetail_food_name)
        val itemCost: TextView = view.findViewById(R.id.tv_resDetail_food_price)
        val addButton: Button = view.findViewById(R.id.bt_resDetail_add_to_cart)
        val removeButton: Button = view.findViewById(R.id.bt_resDetail_remove)
    }

    companion object{
        var isCartEmpty = true
    }

    interface OnItemClickListener{
        fun onAddItemClick(foodDetailItem: FoodDetailItem)
        fun onRemoveItemClick(foodDetailItem: FoodDetailItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_element_resdetails, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return foodItemList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = foodItemList[position]
        holder.itemId.text = item.itemID
        holder.itemName.text = item.itemName
        holder.itemCost.text = item.itemCost

        holder.addButton.setOnClickListener {
            holder.addButton.visibility = View.GONE
            holder.removeButton.visibility = View.VISIBLE
            listener.onAddItemClick(item)
        }
        holder.removeButton.setOnClickListener {
            holder.addButton.visibility = View.VISIBLE
            holder.removeButton.visibility = View.GONE
            listener.onRemoveItemClick(item)
        }
    }


}







