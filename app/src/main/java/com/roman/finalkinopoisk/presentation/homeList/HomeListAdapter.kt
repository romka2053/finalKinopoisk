package com.roman.finalkinopoisk
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.roman.finalkinopoisk.databinding.HomeListBinding
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.roman.finalkinopoisk.entity.CategoryFilms
import com.roman.finalkinopoisk.entity.Films
import com.roman.finalkinopoisk.presentation.SettingGetFilms

class HomeListAdapter(private val onClick: (Int) -> Unit,private val onClickAll:(SettingGetFilms)->Unit): Adapter<HomeListViewHolder>() {
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

        val adapter=FilmsListAdapter(onClick, onClickAll)
        holder.binding.filmList.adapter=adapter
        holder.binding.nameCategory.text=data[position].category.nameCategory
        holder.binding.allInCategory.setOnClickListener{
            onClickAll(data[position].category)
        }
        adapter.setData(data[position])



    }

    override fun getItemCount(): Int {
       return data.size
    }
}
class HomeListViewHolder(val binding: HomeListBinding):ViewHolder(binding.root)