package com.example.bubble

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import api.RetrofitHelper
import api.UserApi
import api.data.Users
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class login : AppCompatActivity() {
    lateinit var btnSignIn:Button
    lateinit var btnMoveToSignUp:Button
    lateinit var etEmail:EditText
    lateinit var etPassword:EditText
    val apiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InVidW9ld21kZWVlZmVraXhiZGpnIiwicm9sZSI6ImFub24iLCJpYXQiOjE2NzI3MDg1ODMsImV4cCI6MTk4ODI4NDU4M30.gaAj8fT6JoUlwS-bjW1cNYqvytqHyUz5ABf6V_Ynxn8"
    val token = "Bearer $apiKey"
    val BubbleApi = RetrofitHelper.getInstance().create(UserApi::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_login)

        btnSignIn = findViewById(R.id.btn_sign_in)
        btnMoveToSignUp = findViewById(R.id.btn_sign_up)
        etEmail = findViewById(R.id.email)
        etPassword = findViewById(R.id.et_password)

        btnSignIn.setOnClickListener {
            signIn(etEmail.text.toString(), etPassword.text.toString())

            val intent = Intent(this, Match::class.java)
            startActivity(intent)
            finish()
        }

        btnMoveToSignUp.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun signIn(email:String, password:String){
        CoroutineScope(Dispatchers.IO).launch{
            val data = Users(email = email, password = password)
            val response = BubbleApi.signIn(token = token, apiKey = apiKey, data = data)

            val bodyResponse = if (response.code() == 200){
                response.body()?.string()
            } else{
                response.errorBody()?.string().toString()
            }

            var failed = false
            val jsonResponse = JSONObject(bodyResponse)
            if (jsonResponse.keys().asSequence().toList().contains("erron")){
                failed = true
            }

            var msg = ""
            if(!failed) {
                var email = jsonResponse.getJSONObject("user").get("email")
                msg = "Succesfully login! Welcome back: $email"
            } else{
                msg += jsonResponse.get("error_description").toString()
            }
            CoroutineScope(Dispatchers.Main).launch{
                Toast.makeText(
                    applicationContext,
                    msg,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}