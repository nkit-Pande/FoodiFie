package com.ankit.foodfie.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.ankit.foodfie.R
import com.ankit.foodfie.adapter.FoodItemAdapter
import com.ankit.foodfie.model.CartData
import com.ankit.foodfie.model.FoodDetailItem
import com.ankit.foodfie.util.ConnectionManager

@Suppress("DEPRECATION")
class ResDetails : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var imageView: ImageView
    private lateinit var layoutManager: LinearLayoutManager
    private var itemFoodList = arrayListOf<FoodDetailItem>()
    private lateinit var itemAdapter: FoodItemAdapter
    private lateinit var toolBar: Toolbar
    private lateinit var bt_addToCart: Button
    private var foodId: String? = "1"
    val bundle = Bundle()
    val orderList: ArrayList<FoodDetailItem> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_res_details)
        recyclerView = findViewById(R.id.resDetail_recyclerView)
        bt_addToCart = findViewById<Button?>(R.id.bt_go_cart)
        bt_addToCart.visibility = View.GONE
        layoutManager = LinearLayoutManager(applicationContext)
        itemFoodList = ArrayList<FoodDetailItem>()
        toolBar = findViewById(R.id.toolbar)
        setSupportActionBar(toolBar)
        supportActionBar?.title = "Food List"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (intent != null) {
            foodId = intent.getStringExtra("food_id")
        } else {
            Toast.makeText(this, "Some unexpected error occurred!!", Toast.LENGTH_SHORT).show()
        }

        val queue = Volley.newRequestQueue(this@ResDetails)
        val url = getString(R.string.URL_RESTAURANT) + foodId


        if (ConnectionManager().connectionStatus(applicationContext)) {

            val jsonObjectRequest =
                object : JsonObjectRequest(Method.GET, url, null,
                    Response.Listener {
                        try {
                            val intentThis = Intent(applicationContext, CartActivity::class.java)
                            val dataMain = it.getJSONObject("data")
                            val success = dataMain.getBoolean("success")
                            if (success) {
                                val data = dataMain.getJSONArray("data")
                                for (i in 0 until data.length()) {

                                    val item = data.getJSONObject(i)
                                    val itemID = item.getString("id")
                                    val itemName = item.getString("name")
                                    val itemCost = item.getString("cost_for_one")
                                    var listOfFood = FoodDetailItem(itemID, itemName, itemCost)

                                    itemFoodList.add(FoodDetailItem(itemID, itemName, itemCost))
                                    itemAdapter = FoodItemAdapter(
                                        this,
                                        itemFoodList,
                                        object : FoodItemAdapter.OnItemClickListener {
                                            override fun onAddItemClick(foodDetailItem: FoodDetailItem) {
                                                addToCart(foodDetailItem, intentThis)
                                                Log.d("myValue", orderList.toString())

                                                FoodItemAdapter.isCartEmpty = false
                                                Toast.makeText(
                                                    applicationContext,
                                                    "added",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                if (orderList.isEmpty()) {
                                                    bt_addToCart.visibility = View.GONE
                                                } else {
                                                    bt_addToCart.visibility = View.VISIBLE
                                                }
                                            }

                                            override fun onRemoveItemClick(foodDetailItem: FoodDetailItem) {
                                                removeToCart(foodDetailItem, intentThis)
                                                FoodItemAdapter.isCartEmpty = true
                                                if (orderList.isEmpty()) {
                                                    bt_addToCart.visibility = View.GONE
                                                } else {
                                                    bt_addToCart.visibility = View.VISIBLE
                                                }
                                            }
                                        })

                                    recyclerView.adapter = itemAdapter
                                    recyclerView.layoutManager = layoutManager
                                    recyclerView.addItemDecoration(
                                        DividerItemDecoration(
                                            recyclerView.context,
                                            layoutManager.orientation
                                        )
                                    )

                                }

                                bt_addToCart.setOnClickListener {
                                    startActivity(intentThis)
                                }


                            }
                        } catch (e: java.lang.Exception) {
                            Toast.makeText(this, "Something went wrong!!", Toast.LENGTH_SHORT)
                                .show()
                        }

                    }, Response.ErrorListener {
                        if (applicationContext != null) {
                            Toast.makeText(
                                this,
                                "Something went wrong,Please try again later.",
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

            queue.add(jsonObjectRequest)
        } else {
            this.runOnUiThread {
                val alertDialog = AlertDialog.Builder(applicationContext as Context)
                alertDialog.setTitle("Error")
                alertDialog.setMessage("No Internet Connection Found!!")
                alertDialog.setPositiveButton("Open Settings") { text, listener ->
                    val settingIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                    startActivity(settingIntent)
                    this.finish()
                }
                alertDialog.setNegativeButton("Exit") { text, listener ->
                    ActivityCompat.finishAffinity(applicationContext as Activity)
                }
                alertDialog.create()
                alertDialog.show()
            }
        }

    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    fun addToCart(foodDetailItem: FoodDetailItem, intent: Intent) {
        orderList.add(foodDetailItem)
        bundle.putString(foodDetailItem.itemName,foodDetailItem.itemCost)

        intent.putExtra("cartList",bundle)
        Log.d("BundleList001",bundle.toString())
    }

    fun removeToCart(foodDetailItem: FoodDetailItem, intent: Intent) {
        orderList.remove(foodDetailItem)
        bundle.remove(foodDetailItem.itemName)
        intent.putExtra("cartList",bundle)
        Log.d("BundleList0",bundle.toString())
    }
}

