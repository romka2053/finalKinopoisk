package com.roman.finalkinopoisk.presentation.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.roman.finalkinopoisk.FilmsListViewHolder
import com.roman.finalkinopoisk.databinding.FilmsListBinding
import com.roman.finalkinopoisk.databinding.GalleryListPreviewBinding
import com.roman.finalkinopoisk.entity.Films
import com.roman.finalkinopoisk.entity.FilmsList
import com.roman.finalkinopoisk.entity.Gallery
import com.roman.finalkinopoisk.entity.GalleryItems

class GalleryListAdapter:Adapter<GalleryListViewHolder> (){

    private var data: List<GalleryItems> = emptyList()
    private var total=0



    fun setData(data: Gallery) {
        this.data = data.items
         total=data.total
        notifyDataSetChanged()


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryListViewHolder {
        val binding = GalleryListPreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GalleryListViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return if (total <= 20) data.size
        else 20
    }

    override fun onBindViewHolder(holder: GalleryListViewHolder, position: Int) {
        holder.bind(data[position])
    }
}

class GalleryListViewHolder(private val binding: GalleryListPreviewBinding ):ViewHolder(binding.root)
{

    fun bind(data: GalleryItems){
        with(binding){
            Glide.with(galleryItem)
                .load(data.previewUrl)
                .centerCrop()
                .into(galleryItem)

        }

    }
}