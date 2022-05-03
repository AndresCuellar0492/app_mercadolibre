package com.example.appmeli.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appmeli.R
import com.example.appmeli.model.DataResult

class SuperMeliAdapter(
    private val superMeliList: List<DataResult>,
    private val onClickListener: (DataResult) -> Unit
) : RecyclerView.Adapter<SuperMeliViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperMeliViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SuperMeliViewHolder(layoutInflater.inflate(R.layout.item_product, parent, false))
    }

    override fun onBindViewHolder(holder: SuperMeliViewHolder, position: Int) {
        val item = superMeliList[position]
        holder.render(item, onClickListener)
    }

    override fun getItemCount(): Int = superMeliList.size

}