package com.ankit.foodfie.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.ankit.foodfie.R
import com.ankit.foodfie.adapter.DashBoardRecyclerAdapter
import com.ankit.foodfie.model.Food
import com.ankit.foodfie.util.ConnectionManager
import org.json.JSONException

class DashBoardFragment : Fragment() {
    private lateinit var relativeLayout: RelativeLayout
    private var openWirelessSettings: Boolean = false
    private lateinit var vcLoadScreen: RelativeLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var foodInfoList: ArrayList<Food>
    private lateinit var recyclerDashboard: DashBoardRecyclerAdapter
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        progressBar = view.findViewById(R.id.progressBar)
        vcLoadScreen = view.findViewById(R.id.rvLoadScreen)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar.visibility = View.VISIBLE
        recyclerView = view.findViewById(R.id.dashBoard_recyclerView)
        layoutManager = LinearLayoutManager(requireContext())
        foodInfoList = ArrayList<Food>()
        recyclerDashboard = DashBoardRecyclerAdapter(requireContext(), foodInfoList)
        recyclerView.adapter = recyclerDashboard
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (layoutManager as LinearLayoutManager).orientation
            )
        )

        fetchDataFromServer()
    }

    private fun fetchDataFromServer() {
        progressBar.visibility = View.VISIBLE

        val queue = Volley.newRequestQueue(requireContext())
        val url = getString(R.string.URL_RESTAURANT)

        if (ConnectionManager().connectionStatus(requireContext())) {
            val jsonbObjectRequest = object : JsonObjectRequest(Method.GET, url, null,
                Response.Listener {
                    try {
                        val dataMain = it.getJSONObject("data")
                        val success = dataMain.getBoolean("success")

                        vcLoadScreen.visibility = ViewGroup.GONE
                        progressBar.visibility = ViewGroup.GONE

                        if (success) {
                            val data = dataMain.getJSONArray("data")
                            for (i in 0 until data.length()) {
                                val item = data.getJSONObject(i)
                                val id = item.getString("id")
                                val name = item.getString("name")
                                val rating = item.getString("rating")
                                val costForOne = item.getString("cost_for_one")
                                val imageUrl = item.getString("image_url")
                                val food = Food(id, name, rating, costForOne, imageUrl)
                                foodInfoList.add(food)
                            }
                            recyclerDashboard.notifyDataSetChanged()
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Something Went Wrong!!! $it",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (e: JSONException) {
                        Toast.makeText(
                            requireContext(),
                            "Some unexpected error occurred. Please try again later",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                Response.ErrorListener {
                    Toast.makeText(
                        requireContext(),
                        "Some unexpected error occurred. Please try again later",
                        Toast.LENGTH_SHORT
                    ).show()
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
            showNoInternetDialog()
        }
    }

    private fun showNoInternetDialog() {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Error")
        alertDialog.setMessage("No Internet Connection Found!!")
        alertDialog.setPositiveButton("Open Settings") { _, _ ->
            val settingIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
            startActivity(settingIntent)
            requireActivity().finish()
        }
        alertDialog.setNegativeButton("Exit") { _, _ ->
            ActivityCompat.finishAffinity(requireActivity())
        }
        alertDialog.create()
        alertDialog.show()
    }
}