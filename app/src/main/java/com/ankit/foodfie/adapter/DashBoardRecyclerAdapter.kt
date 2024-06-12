package com.ankit.foodfie.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ankit.foodfie.R
import com.ankit.foodfie.activity.DescriptionActivity
import com.ankit.foodfie.activity.ResDetails
import com.ankit.foodfie.model.Food
import com.squareup.picasso.Picasso

class DashBoardRecyclerAdapter(val context: Context, private val itemList: ArrayList<Food>) :
    RecyclerView.Adapter<DashBoardRecyclerAdapter.DashBoardViewHandler>() {

    class DashBoardViewHandler(view: View) : RecyclerView.ViewHolder(view) {
        val textViewName: TextView = view.findViewById(R.id.txtItemName)
        val textViewDescription: TextView = view.findViewById(R.id.txtDescription)
        val textViewPrice: TextView = view.findViewById(R.id.txtPrice)
        val textViewRating: TextView = view.findViewById(R.id.txtRating)
        val imageView: ImageView = view.findViewById(R.id.imgItem)
        var linearLayout: LinearLayout = view.findViewById(R.id.db_recyclerview_single_row)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashBoardViewHandler {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.dashboard_recyclerview_single_row, parent, false)
        return DashBoardViewHandler(view)
    }

    override fun onBindViewHolder(holder: DashBoardViewHandler, position: Int) {
        val food = itemList[position]
        holder.textViewDescription.text = food.id
        holder.textViewName.text = food.name
        holder.textViewPrice.text = food.cost_for_one
        holder.textViewRating.text = food.rating
        //holder.imageView.setImageResource(food.img_url)
        Picasso.get().load(food.img_url).error(R.drawable.login).into(holder.imageView)
        holder.linearLayout.setOnClickListener {
            val intent = Intent(context,DescriptionActivity::class.java)
            intent.putExtra("food_id",food.id)
            context.startActivity(intent)
        }
    }
    override fun getItemCount(): Int {
        return itemList.size
    }
}

