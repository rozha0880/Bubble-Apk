package com.example.bubble

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import api.RetrofitHelper
import api.UserApi
import api.data.Data
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Match : AppCompatActivity() {
    lateinit var next: Button
    lateinit var date: Button
    lateinit var name: TextView
    lateinit var birth: TextView

    val apiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InVidW9ld21kZWVlZmVraXhiZGpnIiwicm9sZSI6ImFub24iLCJpYXQiOjE2NzI3MDg1ODMsImV4cCI6MTk4ODI4NDU4M30.gaAj8fT6JoUlwS-bjW1cNYqvytqHyUz5ABf6V_Ynxn8"
    val token = "Bearer $apiKey"
    val BubbleApi = RetrofitHelper.getInstance().create(UserApi::class.java)
    var x = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_match)

        next = findViewById(R.id.next)
        date = findViewById(R.id.date)
        name = findViewById(R.id.name)
        birth = findViewById(R.id.birth)

        next.setOnClickListener {
            var a = "0"

            val sharedPreference =  getSharedPreferences(
                "app_preference", Context.MODE_PRIVATE
            )

            var editor = sharedPreference.edit()
            editor.putString("a", a)
            editor.commit()

            val intent = Intent(this, Match2::class.java)
            intent.putExtra("result", a)
            startActivity(intent)
        }

        date.setOnClickListener {
            if (x != 0){
                CoroutineScope(Dispatchers.Main).launch {

                    val data = Data(name = name.text.toString(), birth = birth.text.toString())
                    val response = BubbleApi.create(token = token, apiKey = apiKey, data = data)

                    Toast.makeText(
                        applicationContext,
                        "Match!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            var a = "1"

            val sharedPreference =  getSharedPreferences(
                "app_preference", Context.MODE_PRIVATE
            )

            var editor = sharedPreference.edit()
            editor.putString("a", a)
            editor.commit()

            val intent = Intent(this, Match2::class.java)
            intent.putExtra("result", a)
            startActivity(intent)
            finish()
        }
    }
}