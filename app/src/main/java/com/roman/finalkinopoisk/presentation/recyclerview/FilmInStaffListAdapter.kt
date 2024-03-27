package com.roman.finalkinopoisk.presentation.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.roman.finalkinopoisk.AllButtonViewHolder
import com.roman.finalkinopoisk.FilmsListAdapter
import com.roman.finalkinopoisk.FilmsListViewHolder
import com.roman.finalkinopoisk.databinding.AllFilmsButtonBinding
import com.roman.finalkinopoisk.databinding.FilmsListBinding
import com.roman.finalkinopoisk.entity.Films
import com.roman.finalkinopoisk.entity.FilmsInStaff

class FilmInStaffListAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var data: List<FilmsInStaff> = emptyList()
    private var total=0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            0->{
                val binding = FilmsListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                FilmsListInStaffViewHolder(binding)
            }
            1->{
                val binding = AllFilmsButtonBinding.inflate(LayoutInflater.from(parent.context), parent, false)

                AllButtonInStaffViewHolder(binding)
            }
            else ->throw IllegalArgumentException("Invalid view type")
        }

    }

    override fun getItemCount(): Int {
        return if (total <= 20) data.size
        else 21
    }

    override fun getItemViewType(position: Int): Int {
        return if (position!=20){
            TYPE_ITEM_FILM
        }else {
          TYPE_ITEM_ALL
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
     when(holder){
         is FilmsListInStaffViewHolder->{

         }
         is AllButtonInStaffViewHolder->{

         }
     }
    }

    companion object{
        private const val TYPE_ITEM_FILM=0
        private const val TYPE_ITEM_ALL=1

    }
}
class FilmsListInStaffViewHolder(val binding:FilmsListBinding) : RecyclerView.ViewHolder(binding.root)
{
    fun bind()
    {

    }
}

class AllButtonInStaffViewHolder(val  binding:AllFilmsButtonBinding) : RecyclerView.ViewHolder(binding.root)