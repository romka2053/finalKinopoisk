package com.roman.finalkinopoisk.presentation.recyclerview

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.roman.finalkinopoisk.R
import com.roman.finalkinopoisk.databinding.CollectionListItemBinding
import com.roman.finalkinopoisk.entity.Films
import com.roman.finalkinopoisk.entity.room.CollectionRoom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

class CollectionAdapter(private val onClick: suspend (Int, String) -> Unit,private val clickAdd:()->Unit) :
    Adapter<CollectionViewHolder>() {
    private var collection: List<CollectionRoom> = emptyList()
    private var collectionFilm: List<CollectionRoom> = emptyList()
    fun setData(listCollection: List<CollectionRoom>, collectionFilmActive: List<CollectionRoom>) {
        collection = listCollection
        collectionFilm = collectionFilmActive
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder {
        val binding =
            CollectionListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CollectionViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return collection.size+1
    }

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {
        if(position==collection.size){
            holder.binding.textView6.text = "Добавить коллекцию"
            holder.binding.checkBox2.visibility=View.INVISIBLE
            holder.binding.textView8.visibility=View.VISIBLE
            holder.binding.root.setOnClickListener {
              clickAdd()
            }
        }else {
            holder.binding.textView8.visibility=View.INVISIBLE
            holder.binding.checkBox2.visibility=View.VISIBLE
            holder.binding.textView6.text = collection[position].name
            holder.binding.checkBox2.isChecked = false
            collectionFilm.forEach {
                if (it.id == collection[position].id) {
                    holder.binding.checkBox2.isChecked = true

                }
            }
            holder.binding.root.setOnClickListener {
                holder.binding.checkBox2.isChecked = !holder.binding.checkBox2.isChecked
                val b = holder.binding.checkBox2.isChecked
                checkedItem(b, position)
            }
            holder.binding.checkBox2.setOnClickListener {

                val b = holder.binding.checkBox2.isChecked
                checkedItem(b, position)
            }
        }
    }
    private fun checkedItem(b:Boolean,position:Int){
        var sql: String
        CoroutineScope(Dispatchers.IO).launch {
            sql = if (b) {
                "INSERT"
            } else {
                "DELETE"
            }
            onClick(collection[position].id, sql)
        }
    }
}


class CollectionViewHolder(val binding: CollectionListItemBinding) : ViewHolder(binding.root) {

}