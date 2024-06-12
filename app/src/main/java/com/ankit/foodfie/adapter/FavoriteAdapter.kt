package com.ankit.foodfie.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ankit.foodfie.R
import com.ankit.foodfie.database.FoodEntity
import com.ankit.foodfie.model.Food
import com.squareup.picasso.Picasso
import java.util.zip.Inflater

class FavoriteAdapter(val context: Context, private val foodList:List<FoodEntity>):RecyclerView.Adapter<FavoriteAdapter.FavouriteViewHolder>() {
    class FavouriteViewHolder(view: View):RecyclerView.ViewHolder(view){
        val item_name: TextView = view.findViewById<TextView>(R.id.fav_item_name)
        val item_id: TextView = view.findViewById<TextView>(R.id.fav_item_id)
        val item_rating: TextView = view.findViewById<TextView>(R.id.fav_item_rating)
        val item_price: TextView = view.findViewById<TextView>(R.id.fav_item_price)
        val item_img: ImageView = view.findViewById<ImageView>(R.id.fav_item_img)
        val item_layout = view.findViewById<LinearLayout>(R.id.fav_item_layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fav_single_item,parent,false)
        return FavouriteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        val food = foodList[position]
        holder.item_id.text = food.food_id
        holder.item_name.text = food.food_name
        holder.item_rating.text = food.food_rating
        holder.item_price.text = food.food_price
        Picasso.get().load(food.food_img).error(R.drawable.login).into(holder.item_img)

    }
}