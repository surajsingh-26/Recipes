package com.example.myrecipies

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.myrecipies.databinding.ActivitySearchBinding
import java.util.Locale.filter

class SearchActivity : AppCompatActivity() {

    lateinit var rvAdapter: SearchAdapter
    lateinit var dataList: ArrayList<Recipe>
    private lateinit var binding: ActivitySearchBinding
    private lateinit var recipes: List<Recipe?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.search.requestFocus()
        val db = Room.databaseBuilder(this@SearchActivity, AppDatabase::class.java, "db_name")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .createFromAsset("recipe.db")
            .build()

        val daoObject = db.getDao()
        recipes = daoObject.getAll()!!

        setUpRecyclerView()
        binding.goBackHome.setOnClickListener {
            finish()
        }

        binding.search.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0.toString()!=""){
                    filterData(p0.toString())
                }else{
                    setUpRecyclerView()
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

    }

    private fun filterData(filterText: String) {

        var filterData=ArrayList<Recipe>()
        for(i in recipes.indices){
            if(recipes[i]!!.tittle.lowercase().contains(filterText.lowercase())){
                filterData.add(recipes[i]!!)
            }
            rvAdapter.filterList(filterList = filterData)
        }
    }

    private fun setUpRecyclerView() {
        dataList = ArrayList()
        binding.rvSearch.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        for (i in recipes.indices) {
            if (recipes[i]!!.category.contains("Popular"))
                dataList.add(recipes[i]!!)
        }
        rvAdapter = SearchAdapter(dataList, this)
        binding.rvSearch.adapter = rvAdapter
    }
}