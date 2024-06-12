package com.ankit.foodfie.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.ankit.foodfie.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    private lateinit var tvName: TextView
    private lateinit var tvMobileNo: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvAddress:TextView


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
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        tvName = view.findViewById(R.id.tvProfileName)
        tvEmail = view.findViewById(R.id.tvProfileEmail)
        tvAddress = view.findViewById(R.id.tvProfileAddress)
        tvMobileNo = view.findViewById(R.id.tvProfileMobile)


        val defaultPref = requireContext().getSharedPreferences("DefaultPreference", Context.MODE_PRIVATE)
        val isFromLogin = defaultPref.getBoolean("fromLogin",false)

        val registerPref = requireContext().getSharedPreferences("RegistrationPreference", Context.MODE_PRIVATE)
        val isFromRegister = registerPref.getBoolean("fromRegistration",false)

        if(isFromLogin){
            tvName.text = defaultPref.getString("nameDefault","")
            tvEmail.text = defaultPref.getString("emailDefault","")
            tvAddress.text = defaultPref.getString("addressDefault","")
            tvMobileNo.text = defaultPref.getString("mobileNoDefault","")
        }else if(isFromRegister){
            tvName.text = registerPref.getString("nameRegistration","")
            tvEmail.text = registerPref.getString("emailRegistration","")
            tvAddress.text = registerPref.getString("addressRegistration","")
            tvMobileNo.text = registerPref.getString("mobileNoRegistration","")
        }else{
            Toast.makeText(activity, "Something Went Wrong!!", Toast.LENGTH_SHORT).show()
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
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}