package com.roman.finalkinopoisk.presentation.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.roman.finalkinopoisk.databinding.FilmographyListElementBinding
import com.roman.finalkinopoisk.databinding.FilmsListBinding
import com.roman.finalkinopoisk.entity.Films
import com.roman.finalkinopoisk.entity.FilmsInStaff

class FilmographyListAllAdapter(private val onClick: (Int) -> Unit): RecyclerView.Adapter<FilmographyListAllViewHolder>()  {
    private var films: List<FilmsInStaff> = emptyList()

    fun setData(filmsList:List<FilmsInStaff>){
        films=filmsList
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmographyListAllViewHolder {
      val binding=FilmographyListElementBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FilmographyListAllViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return films.size
    }

    override fun onBindViewHolder(holder: FilmographyListAllViewHolder, position: Int) {

with(holder){
    binding.root.setOnClickListener {
        onClick(films[position].filmId)
    }
    films[position].nameRu
        ?.let {binding.textView9.text=it}
        ?:let { binding.textView9.text=films[position].nameEn }
    val rating= films[position].rating?.toString() ?:let{"Неизвестно"}

    binding.textView11.text="Рейтинг :"+rating

}

    }
}

class FilmographyListAllViewHolder(val binding: FilmographyListElementBinding):ViewHolder(binding.root)