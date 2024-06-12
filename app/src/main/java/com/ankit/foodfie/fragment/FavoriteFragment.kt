package com.ankit.foodfie.fragment

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsSeekBar
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.ankit.foodfie.R
import com.ankit.foodfie.adapter.FavoriteAdapter
import com.ankit.foodfie.database.FoodDatabase
import com.ankit.foodfie.database.FoodEntity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FavoriteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavoriteFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    private lateinit var recyclerView: RecyclerView
    private lateinit var relativeLayout: RelativeLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var favoriteAdapter: FavoriteAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager
    var foodList = listOf<FoodEntity>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)
        recyclerView = view.findViewById(R.id.db_recyclerview_fav)
//        relativeLayout = view.findViewById(R.id.hide_layout)
//        progressBar = view.findViewById(R.id.hide_progressBar)
        layoutManager = GridLayoutManager(activity as Context,2)
        foodList = RetrieveFavoriteData(activity as Context).execute().get()
        if(activity != null){
//            progressBar.visibility = View.GONE
//            relativeLayout.visibility = View.GONE
            favoriteAdapter = FavoriteAdapter(activity as Context,foodList)
            recyclerView.adapter = favoriteAdapter
            recyclerView.layoutManager = layoutManager
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FavoriteFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FavoriteFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


    class RetrieveFavoriteData(val context:Context):AsyncTask<Void,Void,List<FoodEntity>>(){


        override fun doInBackground(vararg params: Void?): List<FoodEntity> {
            val db = Room.databaseBuilder(context, FoodDatabase::class.java, "food-db").build()
            val foodList = db.foodDao().getAllFood()
            Log.d("RetrieveFavoriteData", "Retrieved food list size: ${foodList.size}")
            return foodList
        }
    }


    }
