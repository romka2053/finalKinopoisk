package com.roman.finalkinopoisk
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.roman.finalkinopoisk.databinding.HomeListBinding
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.roman.finalkinopoisk.entity.CategoryFilms
import com.roman.finalkinopoisk.entity.Films

class HomeListAdapter(private val onClick: (Int) -> Unit): Adapter<HomeListViewHolder>() {
    private var data: List<CategoryFilms> = emptyList()
    fun setData(data: List<CategoryFilms>) {
        this.data = data.shuffled()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeListViewHolder {
        val binding = HomeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeListViewHolder(binding)
    }


    override fun onBindViewHolder(holder: HomeListViewHolder, position: Int) {
        val adapter=FilmsListAdapter(onClick)
        holder.binding.filmList.adapter=adapter
        holder.binding.nameCategory.text=data[position].category
        adapter.setData(data[position].filmsList)



    }

    override fun getItemCount(): Int {
       return data.size
    }
}
class HomeListViewHolder(val binding: HomeListBinding):ViewHolder(binding.root)