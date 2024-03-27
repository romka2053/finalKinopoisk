package com.roman.finalkinopoisk.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.roman.finalkinopoisk.R
import com.roman.finalkinopoisk.databinding.FragmentFilmographyBinding
import com.roman.finalkinopoisk.entity.FilmsInStaff
import com.roman.finalkinopoisk.entity.StaffWithFilms
import com.roman.finalkinopoisk.presentation.recyclerview.FilmographyListAllAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class FilmographyFragment : Fragment() {
    private var _binding: FragmentFilmographyBinding? = null
    private val binding get() = _binding!!
    private val adapter= FilmographyListAllAdapter{int->clickItem(int)}

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var  staff :StaffWithFilms

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilmographyBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        staff = viewModel.staffById
        binding.filmsInFilmography.adapter=adapter
        lifecycleScope.launch {
            setContent()
        }


    }


    private suspend fun setContent() {

        binding.nameStaffFilmography.text = staff.staff.nameRu

        staff.mapKeyProfessionFilm.forEach { map ->
            val chip = Chip(context)
            chip.id = ViewCompat.generateViewId()

            chip.setOnClickListener {
                lifecycleScope.launch {
                    setListFilmography(map.value)
                }

            }
            chip.text = map.key.description + " " + map.value.size
            binding.chipGroup.addView(chip)
        }
        staff.mapKeyProfessionFilm.firstNotNullOfOrNull { it->it }?.let{
            setListFilmography(it.value)
        }

    }

    private suspend fun setListFilmography(mapElement: List<FilmsInStaff>) {
        adapter.setData(mapElement)

    }

    fun clickItem(item:Int){
        val bundle=Bundle().apply { putInt(MainFragment.PUT_PARAM1,item) }
        findNavController().navigate(
            resId= R.id.action_filmographyFragment_to_filmPage,
            args = bundle
        )

    }
}