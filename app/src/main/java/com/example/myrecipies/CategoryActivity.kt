package com.example.myrecipies

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.myrecipies.databinding.ActivityCategoryBinding

class CategoryActivity : AppCompatActivity() {

    lateinit var binding: ActivityCategoryBinding
    lateinit var rvAdapter: CategoryAdapter
    lateinit var dataList: ArrayList<Recipe>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = intent.getStringExtra("TITTLE")
        setUpRecyclerView()
        binding.goBackHome.setOnClickListener {
            startActivity(Intent(this,HomeActivity::class.java))

        }
    }

    private fun setUpRecyclerView() {
        dataList = ArrayList()
        binding.categoryRv.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val db = Room.databaseBuilder(this@CategoryActivity, AppDatabase::class.java, "db_name")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .createFromAsset("recipe.db")
            .build()

        val daoObject = db.getDao()
        val recipes = daoObject.getAll()
        for (i in recipes!!.indices) {
            if (recipes[i]!!.category.contains(intent.getStringExtra("CATEGORY")!!))
                dataList.add(recipes[i]!!)
        }
        rvAdapter = CategoryAdapter(dataList, this)
        binding.categoryRv.adapter = rvAdapter

    }
}