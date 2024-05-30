package com.roman.finalkinopoisk.presentation.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.roman.finalkinopoisk.HomeListViewHolder
import com.roman.finalkinopoisk.databinding.FragmentFilmPageBinding
import com.roman.finalkinopoisk.databinding.HomeListBinding
import com.roman.finalkinopoisk.databinding.StaffListBinding
import com.roman.finalkinopoisk.entity.CategoryFilms
import com.roman.finalkinopoisk.entity.Staff

class StaffListAdapter(private val clickStaff:(Int)->Unit): RecyclerView.Adapter<StaffListViewHolder>() {
    private var data: List<Staff> = emptyList()
    fun setData(data: List<Staff>) {
        this.data = data
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaffListViewHolder {
        val binding = StaffListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StaffListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        if (data.isEmpty())return 0
        return if (data?.first()?.professionKey=="ACTOR"){
            if (data.size>20) {
                20
            }else {
                data.size
            }
        }else{
            if (data.size>6) {
                6
            }else {
                data.size
            }
        }
    }

    override fun onBindViewHolder(holder: StaffListViewHolder, position: Int) {
        with(holder.binding) {
            root.setOnClickListener {
                clickStaff(data[position].staffId)
            }
            Glide.with(staffName.context)
            .load(data[position].posterUrl)
            .centerCrop()
            .into(staffUrlPoster)
          staffName.text=data[position].nameRu
            textView4.text=data[position].nameEn
        }

    }
}

class StaffListViewHolder (val binding: StaffListBinding):ViewHolder(binding.root)


