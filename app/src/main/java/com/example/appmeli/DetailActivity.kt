package com.example.appmeli

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.appmeli.util.Verify
import org.json.JSONObject
import java.text.NumberFormat
import java.util.*





class DetailActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var jsonObject: JSONObject
    private lateinit var ivImage: ImageView
    private lateinit var tvPrice: TextView
    private lateinit var tvTitle: TextView
    private lateinit var tvStock: TextView
    private lateinit var btnBuy: Button
    private var verify = Verify()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(Html.fromHtml("<font color=\"gray\">" + getString(R.string.app_name) + "</font>"));
        val detail = intent.getStringExtra("EXTRA_DATA")
        jsonObject = JSONObject(detail.toString())
        loadElements()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        setContentView(R.layout.activity_detail)
        loadElements()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setContentView(R.layout.activity_detail)
        loadElements()
    }

    private fun loadElements() {
        ivImage = findViewById<View>(R.id.ivImage) as ImageView
        tvPrice = findViewById<View>(R.id.tvPrice) as TextView
        tvTitle = findViewById<View>(R.id.tvTitle) as TextView
        tvStock = findViewById<View>(R.id.tvStock) as TextView
        btnBuy = findViewById<View>(R.id.btnBuy) as Button
        dataElements()
    }

    @SuppressLint("SetTextI18n")
    private fun dataElements() {
        Glide.with(ivImage.context).load(jsonObject.get("thumbnail")).into(ivImage)
        tvPrice.text = formatMoney(jsonObject.get("price").toString())
        tvTitle.text = jsonObject.get("title").toString()
        tvStock.text = "Stock: " + jsonObject.get("available_quantity").toString()
        btnBuy.setOnClickListener(this)

    }

    private fun formatMoney(price: String): String {
        val money: Double = price.toDouble()
        val COUNTRY = "US"
        val LANGUAGE = "en"
        return NumberFormat.getCurrencyInstance(Locale(LANGUAGE, COUNTRY)).format(money)
    }

    override fun onClick(v: View?) {
        val url = jsonObject.get("permalink").toString()
        when (v?.id) {
            R.id.btnBuy -> openWeb(url)
        }
    }

    private fun openWeb(url: String) {
        val browserIntent =
            Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }


}