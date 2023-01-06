package com.example.bubble

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import api.data.Data

class Adapter (var ctx: Context, var resource: Int, var item: ArrayList<Data>): ArrayAdapter<Data>(ctx, resource, item) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater = LayoutInflater.from(ctx)
        val view = layoutInflater.inflate(resource, null)

        val name = view.findViewById<TextView>(R.id.txt_nama)
        val birth = view.findViewById<TextView>(R.id.txt_description)


        name.text = item[position].name
        birth.text = item[position].birth

        return view
    }
}