package com.roman.finalkinopoisk.presentation.recyclerview

import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.content.ContextCompat.getString
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding
import com.roman.finalkinopoisk.R
import com.roman.finalkinopoisk.databinding.CollectionEndItemBinding
import com.roman.finalkinopoisk.databinding.CollectionListItemBinding
import com.roman.finalkinopoisk.entity.Films
import com.roman.finalkinopoisk.entity.room.CollectionRoom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

class CollectionAdapter(
    private val onClick: suspend (String, String) -> Unit,
    private val clickAdd: () -> Unit
) :
    Adapter<ViewHolder>() {
    private var collection: List<CollectionRoom> = emptyList()
    private var collectionFilm: List<CollectionRoom> = emptyList()
    fun setData(listCollection: List<CollectionRoom>, collectionFilmActive: List<CollectionRoom>) {
        collection = listCollection
        collectionFilm = collectionFilmActive
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding :ViewBinding
        return if (viewType == ITEM_COLLECTION){
            binding= CollectionListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            CollectionViewHolder(binding)
        } else {

            binding=CollectionEndItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            CollectionAddViewHolder(binding)
        }

    }

    override fun getItemCount(): Int {
        return collection.size + 1
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is CollectionViewHolder) {
            holder.binding.textView6.text = collection[position].name_collection
            holder.binding.checkBox2.isChecked = false
            collectionFilm.forEach {
                if (it.name_collection == collection[position].name_collection) {
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


        if (holder is CollectionAddViewHolder)
        {
            holder.binding.root.setOnClickListener {
                clickAdd()
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (position == collection.size) ITEM_ADD else ITEM_COLLECTION
    }

    private fun checkedItem(b: Boolean, position: Int) {
        var sql: String
        CoroutineScope(Dispatchers.IO).launch {
            sql = if (b) {
                "INSERT"
            } else {
                "DELETE"
            }
            onClick(collection[position].name_collection, sql)
        }
    }

    private companion object {
        private const val ITEM_COLLECTION = 1
        private const val ITEM_ADD = 2
    }
}

class CollectionAddViewHolder(val binding: CollectionEndItemBinding) : ViewHolder(binding.root)
class CollectionViewHolder(val binding: CollectionListItemBinding) : ViewHolder(binding.root)