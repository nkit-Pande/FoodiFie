package com.ankit.foodfie.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.ankit.foodfie.R

class ForgetPass : AppCompatActivity() {

    lateinit var etfpMobileNo:EditText
    lateinit var etfpEmail:EditText
    lateinit var btNext:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_pass)

        etfpMobileNo = findViewById(R.id.etfpMobileNumber)
        etfpEmail = findViewById(R.id.etfpEmail)
        btNext = findViewById(R.id.btfpButton)


        btNext.setOnClickListener{

            if(etfpMobileNo.text.toString().isNotBlank() && etfpEmail.text.toString().isNotBlank())
            {
                val mobileNo = etfpMobileNo.text.toString()
                val email = etfpEmail.text.toString()

                val loginSharedPreferences = getSharedPreferences("DefaultPreference", Context.MODE_PRIVATE)
                val registerSharedPreferences = getSharedPreferences("RegistrationPreference", Context.MODE_PRIVATE)

                val storedMobileNoLogin = loginSharedPreferences.getString("mobileNoDefault", "")
                val storedEmailLogin = loginSharedPreferences.getString("emailDefault", "")
                val storedPasswordLogin = loginSharedPreferences.getString("passDefault", "")

                val storedMobileNoRegister = registerSharedPreferences.getString("mobileNoRegistration", "")
                val storedEmailRegister = registerSharedPreferences.getString("emailRegistration", "")
                val storedPasswordRegister = registerSharedPreferences.getString("passRegistration", "")

                var passwordMatched = false

                if (mobileNo == storedMobileNoLogin && email == storedEmailLogin) {
                    passwordMatched = true
                    showToast("Your password (from login) is: $storedPasswordLogin")
                } else if (mobileNo == storedMobileNoRegister && email == storedEmailRegister) {
                    passwordMatched = true
                    showToast("Your password (from register) is: $storedPasswordRegister")
                }

                if (!passwordMatched) {
                    showToast("Invalid mobile number or email")
                }
            }
            }

        }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}