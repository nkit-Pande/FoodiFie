package com.ankit.foodfie.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.room.Room
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.ankit.foodfie.R
import com.ankit.foodfie.adapter.DashBoardRecyclerAdapter
import com.ankit.foodfie.database.FoodDatabase
import com.ankit.foodfie.database.FoodEntity
import com.ankit.foodfie.model.Food
import com.ankit.foodfie.util.ConnectionManager
import com.squareup.picasso.Picasso
import org.json.JSONException

class DescriptionActivity : AppCompatActivity() {

    private lateinit var name: TextView
    private lateinit var id: TextView
    private lateinit var price: TextView
    private lateinit var rating: TextView
    private lateinit var img: ImageView
    private lateinit var linearLayout: LinearLayout
    private lateinit var addToFav: ImageButton
    private lateinit var relativeLayout: RelativeLayout
    private lateinit var order:Button
    private lateinit var toolbar:androidx.appcompat.widget.Toolbar
    private lateinit var progressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)
        img = findViewById(R.id.imgDescription)
        name = findViewById(R.id.txtDescriptionName)
        id = findViewById(R.id.txtDescriptionID)
        rating = findViewById(R.id.txtDescriptionRating)
        price = findViewById(R.id.txtDescriptionPrice)
        addToFav = findViewById(R.id.addFav)
        order = findViewById(R.id.bt_to_resDetails)
        order.visibility = View.GONE
        relativeLayout = findViewById(R.id.coverLayout)
        progressBar = findViewById(R.id.progressDescription)
        linearLayout = findViewById(R.id.llContent)
        toolbar = findViewById(R.id.toolbar)
        setToolBar()

        relativeLayout.visibility = View.VISIBLE
        progressBar.visibility = View.VISIBLE

        val queue = Volley.newRequestQueue(this@DescriptionActivity)
        val url = getString(R.string.URL_RESTAURANT)

        if (ConnectionManager().connectionStatus(this as Context)) {
            val jsonbObjectRequest =
                @SuppressLint("SetTextI18n")
                object : JsonObjectRequest(
                    Method.GET,
                    url, null,
                    Response.Listener {
                        try {
                            val dataMain = it.getJSONObject("data")
                            val success = dataMain.getBoolean("success")
                            relativeLayout.visibility = ViewGroup.GONE
                            progressBar.visibility = ViewGroup.GONE
                            order.visibility = View.VISIBLE
                            if (success) {
                                val data = dataMain.getJSONArray("data")
                                var img_url: String
                                for (i in 0 until data.length()) {
                                    val item = data.getJSONObject(i)

                                    if (intent.getStringExtra("food_id") == item.getString("id")) {

                                        id.text = item.getString("id")
                                        name.text = item.getString("name")
                                        rating.text = item.getString("rating")
                                        price.text = item.getString("cost_for_one")
                                        Picasso.get().load(item.getString("image_url"))
                                            .error(R.drawable.login).into(img)
                                        img_url = item.getString("image_url")
                                        val foodEntity = FoodEntity(
                                            id.text.toString(),
                                            name.text.toString(),
                                            price.text.toString(),
                                            rating.text.toString(),
                                            img_url
                                        )
                                        val checkFav =
                                            DBAsynTask(applicationContext, foodEntity, 1).execute()
                                        val isFav = checkFav.get()
                                        if (isFav) {
                                            addToFav.setBackgroundResource(R.drawable.baseline_isfavorite_24)
                                        } else {
                                            addToFav.setBackgroundResource(R.drawable.baseline_favorite_border_24)
                                        }
                                        addToFav.setOnClickListener {
                                            if (!DBAsynTask(
                                                    applicationContext,
                                                    foodEntity,
                                                    1
                                                ).execute().get()
                                            ) {
                                                val async = DBAsynTask(
                                                    applicationContext,
                                                    foodEntity,
                                                    2
                                                ).execute()
                                                if (async.get()) {
                                                    Toast.makeText(this@DescriptionActivity, "Added to Favorites!", Toast.LENGTH_SHORT)
                                                       .show()
                                                    addToFav.setBackgroundResource(R.drawable.baseline_isfavorite_24)
                                                }else{
                                                    Toast.makeText(this@DescriptionActivity, "Some error occurred!!", Toast.LENGTH_SHORT)
                                                        .show()
                                                }

                                            }else{
                                                val async = DBAsynTask(
                                                    applicationContext,
                                                    foodEntity,
                                                    3
                                                ).execute()
                                                if (async.get()){
                                                    Toast.makeText(this@DescriptionActivity, "Book Removed from Favorites!", Toast.LENGTH_SHORT)
                                                        .show()
                                                    addToFav.setBackgroundResource(R.drawable.baseline_favorite_border_24)
                                                }
                                                else{
                                                    Toast.makeText(this@DescriptionActivity, "Some error occurred!!", Toast.LENGTH_SHORT)
                                                        .show()
                                                }
                                            }
                                        }
                                        order.setOnClickListener {
                                            val intentThis = Intent(this@DescriptionActivity,ResDetails::class.java)
                                            intentThis.putExtra("food_id",foodEntity.food_id)
                                            startActivity(intentThis)
                                        }
                                    }
                                }

                            } else {
                                Toast.makeText(
                                    this,
                                    "Something Went Wrong!!! $it",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } catch (e: JSONException) {
                            Toast.makeText(
                                this,
                                "Some unexpected error occurred,Please Try again later",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    Response.ErrorListener {
                        if(applicationContext != null){
                            Toast.makeText(
                                this,
                                "Some unexpected error occurred,Please Try again later",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }) {
                    override fun getHeaders(): MutableMap<String, String> {
                        val header = HashMap<String, String>()
                        header["Content-type"] = "application/json"
                        header["token"] = "4e1d0c3cd41e0b"
                        return header
                    }
                }
            queue.add(jsonbObjectRequest)
        } else {
            this.runOnUiThread {
                val alertDialog = AlertDialog.Builder(this as Context)
                alertDialog.setTitle("Error")
                alertDialog.setMessage("No Internet Connection Found!!")
                alertDialog.setPositiveButton("Open Settings") { text, listener ->
                    val settingIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                    startActivity(settingIntent)
                    this.finish()
                }
                alertDialog.setNegativeButton("Exit") { text, listener ->
                    ActivityCompat.finishAffinity(this as Activity)
                }
                alertDialog.create()
                alertDialog.show()
            }
        }


    }


    class DBAsynTask(val context: Context, val foodEntity: FoodEntity, val mode: Int) :
        AsyncTask<Void, Void, Boolean>() {
        val db = Room.databaseBuilder(context, FoodDatabase::class.java, "food-db").build()
        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg params: Void?): Boolean {

            when (mode) {
                1 -> {
                    val food: FoodEntity? = db.foodDao().getFoodByID(foodEntity.food_id)
                    db.close()
                    return food!=null
                }
                2 -> {
                    db.foodDao().insertFood(foodEntity)
                    db.close()
                    return true
                }
                3 -> {
                    db.foodDao().deleteFood(foodEntity)
                    db.close()
                    return true
                }
            }
            return false
        }

    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    private fun setToolBar() {
        setSupportActionBar(toolbar)

        val button = toolbar.findViewById<ImageButton>(R.id.btCheckInternet)
        button.setOnClickListener {
            if (ConnectionManager().connectionStatus(this as Context)) {
                val alertDialog = AlertDialog.Builder(this as Context)
                alertDialog.setTitle("Internet Status")
                alertDialog.setMessage("Connection Found")
                alertDialog.setNegativeButton("Close") { text, context ->

                }
                alertDialog.create()
                alertDialog.show()
            } else {
                val alertDialog = AlertDialog.Builder(this as Context)
                alertDialog.setTitle("Internet Status")
                alertDialog.setMessage("Connection Not Found")
                alertDialog.setNegativeButton("Close") { text, context ->

                }
                alertDialog.create()
                alertDialog.show()
            }

        }
    }
}

