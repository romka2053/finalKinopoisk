package com.roman.finalkinopoisk.presentation.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.roman.finalkinopoisk.databinding.FilmsListAllBinding
import com.roman.finalkinopoisk.entity.Films

class FilmsListSearchAdapter() :
    PagingDataAdapter<Films, MyFilmsSearchViewHolder>(DiffUtilItemCallback()) {
    override fun onBindViewHolder(holder: MyFilmsSearchViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyFilmsSearchViewHolder {
        val binding = FilmsListAllBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return MyFilmsSearchViewHolder(binding)
    }

}


class MyFilmsSearchViewHolder(val binding: FilmsListAllBinding) : ViewHolder(binding.root) {
    fun bind(film: Films) {
        with(binding) {
            nameFilmAll.text = film.nameRu


            Glide.with(imagePosterAll.context)
                .load(film.posterUrlPreview)
                .centerCrop()
                .into(imagePosterAll)

        }
    }
}

class DiffUtilItemCallback : DiffUtil.ItemCallback<Films>() {
    override fun areItemsTheSame(oldItem: Films, newItem: Films): Boolean =
        oldItem.id == newItem.id


    override fun areContentsTheSame(oldItem: Films, newItem: Films): Boolean =
        oldItem.nameRu == newItem.nameRu &&
        oldItem.posterUrlPreview == newItem.posterUrlPreview

}
