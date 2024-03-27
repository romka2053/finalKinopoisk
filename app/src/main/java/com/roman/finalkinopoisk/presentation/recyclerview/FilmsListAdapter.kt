package com.roman.finalkinopoisk


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.roman.finalkinopoisk.databinding.AllFilmsButtonBinding
import com.roman.finalkinopoisk.databinding.FilmsListBinding
import com.roman.finalkinopoisk.entity.Films
import com.roman.finalkinopoisk.entity.FilmsList
import com.roman.finalkinopoisk.presentation.MainFragment

class FilmsListAdapter(private val onClick: (Int) -> Unit) : Adapter<ViewHolder>() {
    private var data: List<Films> = emptyList()
    private var total=0


    fun setData(data: FilmsList) {
        this.data = data.filmsList.shuffled()
        total=data.total
        notifyDataSetChanged()


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return when(viewType){
            0->{
                val binding = FilmsListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                FilmsListViewHolder(binding)
            }
            1->{
                val binding = AllFilmsButtonBinding.inflate(LayoutInflater.from(parent.context), parent, false)

                AllButtonViewHolder(binding)
            }
            else ->throw IllegalArgumentException("Invalid view type")
        }


    }

    override fun getItemViewType(position: Int): Int {
        return if (position!=20){
            TYPE_ITEM_FILM
        }else {
            TYPE_ITEM_ALL
        }
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(holder){
            is FilmsListViewHolder->{
                holder.bind(data[position])
                holder.binding.root.setOnClickListener {
                    data[position]?.id?.let(onClick)

                }
            }
            is AllButtonViewHolder->{}
        }



    }


    override fun getItemCount(): Int {
        return if (total <= 20) data.size
        else 21
    }


    companion object{
        private const val TYPE_ITEM_FILM=0
        private const val TYPE_ITEM_ALL=1

    }
}

class FilmsListViewHolder(val binding:FilmsListBinding) : ViewHolder(binding.root)
{
    fun bind(data:Films){
        with(binding) {
            nameFilm.text = data.nameRu.toString()
            data.rating?.let {
                ratingFilmPrev.text=it.toString()
            }?:let{
                ratingFilmPrev.visibility=View.INVISIBLE
            }
            Glide.with(imageView.context)
                .load(data.posterUrlPreview)
                .centerCrop()
                .into(imageView)
        }
    }
}

class AllButtonViewHolder( binding:AllFilmsButtonBinding) : ViewHolder(binding.root)
