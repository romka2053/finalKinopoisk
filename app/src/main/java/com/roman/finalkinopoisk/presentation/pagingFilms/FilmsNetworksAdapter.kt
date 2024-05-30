package com.roman.finalkinopoisk.presentation.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.roman.finalkinopoisk.databinding.AllFilmsButtonBinding
import com.roman.finalkinopoisk.databinding.FilmsListAllBinding
import com.roman.finalkinopoisk.databinding.FilmsListBinding
import com.roman.finalkinopoisk.databinding.FilmsListPagingBinding
import com.roman.finalkinopoisk.entity.Films
import com.roman.finalkinopoisk.entity.FilmsInStaff

class FilmsNetworksAdapter(private val onClick: (Int) -> Unit) :
    PagingDataAdapter<Films, MyFilmsNetworkViewHolder>(DiffUtilItemCallback()) {
    override fun onBindViewHolder(holder: MyFilmsNetworkViewHolder, position: Int) {
        getItem(position)?.let { item ->

            holder.binding.root.setOnClickListener {
                onClick(item.id)
            }
            holder.bind(item) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyFilmsNetworkViewHolder {
        val binding = FilmsListPagingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyFilmsNetworkViewHolder(binding)
    }

}


class MyFilmsNetworkViewHolder(val binding: FilmsListPagingBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(film: Films) {
        with(binding) {
            film.rating?.let{
                ratingFilmPrev.text=it.toString()
            }?:let{ratingFilmPrev.visibility=View.GONE}
            film.nameRu?.let {
                nameFilm.text =it
            }?:let { nameFilm.text="unknown"}



            Glide.with(imageView.context)
                .load(film.posterUrlPreview)
                .centerCrop()
                .into(imageView)

        }
    }
}

