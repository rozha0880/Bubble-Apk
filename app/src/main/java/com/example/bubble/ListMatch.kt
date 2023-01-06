package com.example.bubble

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import api.RetrofitHelper
import api.UserApi
import api.data.Data
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListMatch : AppCompatActivity() {
    lateinit var labelHeader : TextView
    lateinit var listTodo : ListView
    lateinit var toLogout: Button

    val apiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InVidW9ld21kZWVlZmVraXhiZGpnIiwicm9sZSI6ImFub24iLCJpYXQiOjE2NzI3MDg1ODMsImV4cCI6MTk4ODI4NDU4M30.gaAj8fT6JoUlwS-bjW1cNYqvytqHyUz5ABf6V_Ynxn8"
    val token = "Bearer $apiKey"

    val Items = ArrayList<Data>()
    val BubbleApi = RetrofitHelper.getInstance().create(UserApi::class.java)

    var x = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_list_match)

        labelHeader = findViewById(R.id.label_header)
        listTodo = findViewById(R.id.list_todo)
        toLogout = findViewById(R.id.toLogout)

        toLogout.setOnClickListener {
            val intent = Intent(this, Logout::class.java)
            startActivity(intent)
        }

        var a = intent.getStringExtra("result")
        var b = intent.getStringExtra("result2")


        if (x!=0){
            CoroutineScope(Dispatchers.Main).launch {
                val response = BubbleApi.get(token=token, apiKey=apiKey)

                response.body()?.forEach {
                    Items.add(
                        Data(
                            id=it.id,
                            name=it.name,
                            birth=it.birth
                        )
                    )
                }

                setList(Items)
            }
        }

        val Items = ArrayList<Data>()

        if (a == "0"){
            if (b == "0"){

            } else {
                Items.add(Data("1", "Claudia", "21 Januari 2003"))
            }
        } else {
            if (b == "0"){
                Items.add(Data("1", "Alexandra", "15 Februari 2003"))
            } else {
                Items.add(Data("1", "Alexandra", "15 Februari 2003"))
                Items.add(Data("2", "Claudia", "21 Januari 2003"))
            }
        }

        val adapter = Adapter(this, R.layout.match_item, Items)
        listTodo.adapter = adapter
    }

    fun setList(Items: ArrayList<Data>) {
        val adapter = Adapter(this, R.layout.match_item, Items)
        listTodo.adapter = adapter
    }
}