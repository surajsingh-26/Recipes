package com.example.myrecipies

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myrecipies.databinding.PopularRvItemBinding

class PopularAdapter(private var datalist: ArrayList<Recipe>, private var context: Context) :
    RecyclerView.Adapter<PopularAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: PopularRvItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PopularRvItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return datalist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context)
            .load(datalist[position].img)
            .into(holder.binding.popularImg)

        holder.binding.popularTxt.text = datalist[position].tittle
        val time = datalist[position].ing.split("\n".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()

        holder.binding.popularTime.text = time.get(0)
        holder.itemView.setOnClickListener {
            var intent = Intent(context, RecipeActivity::class.java)
            intent.putExtra("img", datalist[position].img)
            intent.putExtra("tittle", datalist[position].tittle)
            intent.putExtra("des", datalist[position].des)
            intent.putExtra("ing", datalist[position].ing)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

            context.startActivity(intent)
        }
    }
}