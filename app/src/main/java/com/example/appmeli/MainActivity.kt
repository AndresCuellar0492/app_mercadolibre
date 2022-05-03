package com.example.appmeli

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Html
import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appmeli.adapter.SuperMeliAdapter
import com.example.appmeli.model.DataResult
import com.example.appmeli.repository.Repository
import com.example.appmeli.util.Constants.Companion.MESSAGE_ERROR_NETWORK
import com.example.appmeli.util.Constants.Companion.MESSAGE_ERROR_REQUEST
import com.example.appmeli.util.Constants.Companion.SEARCH_URL
import com.example.appmeli.util.Verify
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONObject


class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var viewModel: MainViewModel
    private lateinit var results: List<DataResult>
    private var gson = Gson()
    private lateinit var ivImageMeli: ImageView
    private lateinit var recyclerProductsMeli: RecyclerView
    private var isBack: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this,R.color.black)))
        supportActionBar?.setTitle(Html.fromHtml("<font color=\"gray\">" + getString(R.string.app_name) + "</font>"));
        loadElements()
        sharedPreferences = getPreferences(MODE_PRIVATE)
        editor = sharedPreferences.edit()
        if (Verify.ENABLE) {
            editor.clear()
        }
    }

    private fun loadElements() {
        ivImageMeli = findViewById<View>(R.id.ivMeli) as ImageView
        recyclerProductsMeli = findViewById(R.id.recyclerProductsMeli) as RecyclerView
    }

    override fun onResume() {
        super.onResume()
        if (!Verify.ENABLE) {
            loadPreferences()
        }
    }

    private fun savePreferences() {
        Verify.ENABLE = false
        editor.putBoolean("isBack", true)
        val json = gson.toJson(results)
        editor.putString("results", json.toString())
        editor.commit()
    }

    private fun loadPreferences() {
        isBack = sharedPreferences.getBoolean("isBack", false)
        if (isBack) {
            val data = sharedPreferences.getString("results", null)
            if (data != null) {
                val jsonArray = JSONArray(data.toString())
                val dataRecycler: List<DataResult> = Gson().fromJson(
                    jsonArray.toString(),
                    object : TypeToken<List<DataResult?>?>() {}.type
                )
                results = dataRecycler
                initRecyclerView(results)
            }
        }
    }


    private fun initViewModelPost(query: String) {
        if (!ValidateNetwork.isNetworkAvailable(this)) {
            ToastMessage.show(this, MESSAGE_ERROR_NETWORK, Toast.LENGTH_SHORT)
            return
        }
        try {
            val repository = Repository()
            val viewModelFactory = MainViewModelFactory(repository)
            viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
            viewModel.getPost(SEARCH_URL, query)
            viewModel.myResponse.observe(this, Observer { response ->
                if (response.isSuccessful) {
                    results = response.body()?.results!!
                    initRecyclerView(results!!)
                } else {
                    ToastMessage.show(this, MESSAGE_ERROR_REQUEST, Toast.LENGTH_SHORT)
                }

            })
        } catch (e: Exception) {
            e.printStackTrace();
            ToastMessage.show(this, MESSAGE_ERROR_REQUEST, Toast.LENGTH_SHORT)
        }

    }

    private fun showRecycler() {
        ivImageMeli.visibility = View.GONE
        recyclerProductsMeli.visibility = View.VISIBLE
    }

    private fun initRecyclerView(response: List<DataResult>) {
        showRecycler()
        val layoutManager = LinearLayoutManager(this)
        recyclerProductsMeli.layoutManager = layoutManager
        recyclerProductsMeli.adapter =
            SuperMeliAdapter(response) { onItemSelected(it) }
    }


    private fun onItemSelected(dataMeli: DataResult) {
        savePreferences()
        val jsonData = JSONObject()

        jsonData.put("title", dataMeli.title)
        jsonData.put("price", dataMeli.price)
        jsonData.put("thumbnail", dataMeli.thumbnail)
        jsonData.put("available_quantity", dataMeli.available_quantity)
        jsonData.put("permalink", dataMeli.permalink)

        val intent = Intent(this, DetailActivity::class.java).also {
            it.putExtra("EXTRA_DATA", jsonData.toString())
            startActivity(it)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)
        val search = menu?.findItem(R.id.search)
        val searchView = search?.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.clearFocus()
        search?.expandActionView()
        searchView?.setOnQueryTextListener(this)

        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null && query != "") {
            initViewModelPost(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null && query != "") {
            initViewModelPost(query)
        }
        return true
    }


}
