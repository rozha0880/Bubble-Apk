package com.example.bubble

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import java.util.Timer
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity() {
    lateinit var txtMessageLoading: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)

        txtMessageLoading = findViewById(R.id.textView1)
        txtMessageLoading.text = "Initializing your app for the first time"

        Timer().schedule(3000){
            goToLogin()
        }
    }

    private fun goToLogin(){
        val intent = Intent(this, iconLogin::class.java)
        startActivity(intent)
        finish()
    }
}