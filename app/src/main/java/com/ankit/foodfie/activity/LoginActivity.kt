package com.ankit.foodfie.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.ankit.foodfie.R

class LoginActivity : AppCompatActivity() {
    lateinit var etMobileNumber: EditText
    lateinit var etPassword: EditText
    lateinit var btLogin: Button
    lateinit var tvForgetPass: TextView
    lateinit var tvSignUp: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_login)
        etMobileNumber = findViewById(R.id.etLoginMobileNumber)
        etPassword = findViewById(R.id.etLoginPassword)
        btLogin = findViewById(R.id.btLogin)
        tvForgetPass = findViewById(R.id.tvForgetPass)
        tvSignUp = findViewById(R.id.tvSignUp)
        val myPreferences = getSharedPreferences("DefaultPreference", Context.MODE_PRIVATE)
        val editor = myPreferences.edit()
        editor.putString("mobileNoDefault", "9876543210")
        editor.putString("passDefault", "abc12345")
        editor.putString("nameDefault", "Alex")
        editor.putString("addressDefault","\n" +
                "123 Main Street, Anytown, USA")
        editor.putString("emailDefault", "alex66@gmail.com")
        editor.putBoolean("fromLogin", true)
        editor.apply()




        tvSignUp.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterationActivity::class.java))
        }

        tvForgetPass.setOnClickListener {
            startActivity(Intent(this@LoginActivity, ForgetPass::class.java))
        }


        btLogin.setOnClickListener {
            val sharedPreferences = getSharedPreferences("DefaultPreference", Context.MODE_PRIVATE)
            val storedMobileNo = sharedPreferences.getString("mobileNoDefault", "")
            val storedPassword = sharedPreferences.getString("passDefault", "")

            val regpref = getSharedPreferences("RegistrationPreference", Context.MODE_PRIVATE)
            val regMobile = regpref.getString("mobileNoRegistration","")
            val regPass = regpref.getString("passRegistration","")
            Log.d("Regvalue","$regMobile")

            if (!(etMobileNumber.text.toString().equals("")) && !(etPassword.text.toString()
                    .equals(""))
            ) {
                if (etMobileNumber.text.toString() == storedMobileNo && etPassword.text.toString() == storedPassword) {
                    val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                    val sharedPreferencesR = getSharedPreferences("RegistrationPreference", Context.MODE_PRIVATE)
                    val editorR = sharedPreferences.edit()
                    editorR.putBoolean("fromRegistration", false)
                    editorR.apply()
                    startActivity(intent)
                    finish()
                }else if(etMobileNumber.text.toString() == regMobile && etPassword.text.toString() == regPass){
                    val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                    editor.putBoolean("fromLogin", false)
                    editor.apply()
                    val sharedPreferencesR = getSharedPreferences("RegistrationPreference", Context.MODE_PRIVATE)
                    val editorR = sharedPreferences.edit()
                    editorR.putBoolean("fromRegistration", true)
                    editorR.apply()
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Invalid Format!!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Input Fields Cannot be Empty", Toast.LENGTH_SHORT).show()
            }

        }
    }


}
