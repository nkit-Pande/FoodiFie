package com.ankit.foodfie.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.ankit.foodfie.R

@Suppress("DEPRECATION")
class RegisterationActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    lateinit var etRegisterName: EditText
    lateinit var etRegisterEmail: EditText
    lateinit var etRegisterMobileNo: EditText
    lateinit var etRegisterAddress: EditText
    lateinit var etRegisterPassword: EditText
    lateinit var etRegisterCPassword: EditText
    lateinit var btRegister: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registeration)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Register yourself"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        etRegisterName = findViewById(R.id.etRegisterName)
        etRegisterEmail = findViewById(R.id.etRegisterEmail)
        etRegisterMobileNo = findViewById(R.id.etRegisterMobileNumber)
        etRegisterAddress = findViewById(R.id.etRegisterAddress)
        etRegisterPassword = findViewById(R.id.etRegisterPassword)
        etRegisterCPassword = findViewById(R.id.etRegisterCPassword)
        btRegister = findViewById(R.id.btRegistrationSignup)

        btRegister.setOnClickListener {
            if (etRegisterName.text.toString().isNotBlank() && etRegisterEmail.text.toString()
                    .isNotBlank() && etRegisterMobileNo.text.toString()
                    .isNotBlank() && etRegisterAddress.text.toString()
                    .isNotBlank() && etRegisterPassword.text.toString()
                    .isNotBlank() && etRegisterCPassword.text.toString()
                    .isNotBlank()
            ) {
                if (etRegisterPassword.text.toString() == etRegisterCPassword.text.toString()) {
                    val intent = Intent(this, HomeActivity::class.java)
                    val myPreferences = getSharedPreferences("RegistrationPreference", Context.MODE_PRIVATE)
                    val editor = myPreferences.edit()
                    editor.putString("mobileNoRegistration",etRegisterMobileNo.text.toString())
                    editor.putString("emailRegistration",etRegisterEmail.text.toString())
                    editor.putString("nameRegistration",etRegisterName.text.toString())
                    editor.putString("addressRegistration",etRegisterAddress.text.toString())
                    editor.putString("passRegistration",etRegisterPassword.text.toString())
                    editor.putBoolean("fromRegistration",true)
                    editor.apply()
                    val loginPref = getSharedPreferences("DefaultPreference", Context.MODE_PRIVATE)
                    val change = loginPref.edit()
                    change.putBoolean("fromLogin",false).apply()
                    startActivity(intent)
                    finish()
                }
            }else{
                Toast.makeText(this, "Fill the fields!!!", Toast.LENGTH_SHORT).show()
            }
        }


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}