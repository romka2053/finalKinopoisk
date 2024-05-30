package com.roman.finalkinopoisk.presentation.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.roman.finalkinopoisk.databinding.GalleryListPreviewBinding
import com.roman.finalkinopoisk.entity.Gallery
import com.roman.finalkinopoisk.entity.GalleryItems

class GalleryListAdapterPrev:Adapter<GalleryListViewHolderPrev> (){

    private var data: List<GalleryItems> = emptyList()
    private var total=0



    fun setData(data: Gallery) {
        this.data = data.items
         total=data.total
        notifyDataSetChanged()


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryListViewHolderPrev {
        val binding = GalleryListPreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GalleryListViewHolderPrev(binding)

    }

    override fun getItemCount(): Int {
        return if (total <= 20) data.size
        else 20
    }

    override fun onBindViewHolder(holder: GalleryListViewHolderPrev, position: Int) {
        holder.bind(data[position])
    }
}

class GalleryListViewHolderPrev(private val binding: GalleryListPreviewBinding ):ViewHolder(binding.root)
{

    fun bind(data: GalleryItems){
        with(binding){
            Glide.with(galleryItemPrev)
                .load(data.previewUrl)
                .centerCrop()
                .into(galleryItemPrev)

        }

    }
}