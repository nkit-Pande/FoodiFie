package com.ankit.foodfie.activity

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ankit.foodfie.R
import com.ankit.foodfie.adapter.KeyValueAdapter
import com.ankit.foodfie.model.KeyValuePair
import com.ankit.foodfie.util.ConnectionManager

class CartActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var order: Button
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var adapter: KeyValueAdapter
    private lateinit var history: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        toolbar = findViewById(R.id.toolbar)
        order = findViewById(R.id.bt_order)
        recyclerView = findViewById(R.id.recyclerViewCart)
        layoutManager = LinearLayoutManager(this)
        setToolBar()

        val receiveList = intent.getBundleExtra("cartList")
        val data = receiveList?.keySet()?.map { key ->
            KeyValuePair("$key", "${receiveList[key]}")
        } ?: emptyList()
        adapter = KeyValueAdapter(data)
        recyclerView.layoutManager = this.layoutManager
        recyclerView.adapter = this.adapter
        order.setOnClickListener {
            startActivity(Intent(this@CartActivity, OrderPlaced::class.java))
            finish()
        }
        val keysStringBuilder = StringBuilder()
        val valuesStringBuilder = StringBuilder()

        for (item in data) {
            keysStringBuilder.append(item.key).append("\n\n")
            valuesStringBuilder.append(item.value).append("\n\n")
        }

        val keysString = keysStringBuilder.removeSuffix("\n ").toString()
        val valuesString = valuesStringBuilder.removeSuffix("\n ").toString()

        history = this.getSharedPreferences("History", Context.MODE_PRIVATE)
        val editor = history.edit()
        editor.putString("key", keysString)
        editor.putString("value", valuesString)
        editor.apply()


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