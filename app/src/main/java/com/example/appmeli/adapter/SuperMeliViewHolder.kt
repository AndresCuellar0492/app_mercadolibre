package com.example.appmeli.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appmeli.R
import com.example.appmeli.model.DataResult
import java.text.NumberFormat
import java.util.*

class SuperMeliViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val tvPrice = view.findViewById<View>(R.id.tvPrice) as TextView
    var tvTitle = view.findViewById<View>(R.id.tvTitle) as TextView
    val tvStock = view.findViewById<View>(R.id.tvStock) as TextView
    val ivThumbnail = view.findViewById<View>(R.id.ivThumbnail) as ImageView

    fun render(superMeliModel: DataResult, onClickListener: (DataResult) -> Unit) {
        tvTitle.text = superMeliModel.title
        tvStock.text = " Cant: " + superMeliModel.available_quantity
        tvPrice.text = formatMoney(superMeliModel.price)
        Glide.with(ivThumbnail.context).load(superMeliModel.thumbnail)
            .into(ivThumbnail)

        itemView.setOnClickListener { onClickListener(superMeliModel) }

        /*itemView.setOnClickListener {
            Toast.makeText(
                binding.ivSuperHero.context,
                superHeroModel.superHero,
                Toast.LENGTH_SHORT
            ).show()
        }*/
    }

    private fun formatMoney(price: String): String {
        val money: Double = price.toDouble()
        val COUNTRY = "US"
        val LANGUAGE = "en"
        return NumberFormat.getCurrencyInstance(Locale(LANGUAGE, COUNTRY)).format(money)
    }
}