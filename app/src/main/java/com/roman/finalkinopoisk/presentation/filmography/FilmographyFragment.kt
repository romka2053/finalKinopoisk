package com.roman.finalkinopoisk.presentation.filmography

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.roman.finalkinopoisk.R
import com.roman.finalkinopoisk.databinding.FragmentFilmographyBinding
import com.roman.finalkinopoisk.entity.FilmographyStaff
import com.roman.finalkinopoisk.entity.FilmsInStaff
import com.roman.finalkinopoisk.entity.StaffFull
import com.roman.finalkinopoisk.entity.StaffWithFilms
import com.roman.finalkinopoisk.presentation.StateLoading
import com.roman.finalkinopoisk.presentation.homeList.MainFragment
import com.roman.finalkinopoisk.presentation.recyclerview.FilmographyListAllAdapter
import com.roman.finalkinopoisk.presentation.staff.StaffPageFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class FilmographyFragment : Fragment() {
    private var _binding: FragmentFilmographyBinding? = null
    private val binding get() = _binding!!
    private val adapter= FilmographyListAllAdapter{int->clickItem(int)}
    private var idStaff=0
    private val viewModel: FilmographyViewModel by viewModels()
    private lateinit  var  staff :FilmographyStaff

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idStaff = it.getInt(StaffPageFragment.ID_STAFF_PUT)

        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilmographyBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.getStaffById(idStaff)
        }
        lifecycleScope.launch {

            viewModel.state.collect{
                when(it){
                    StateLoading.Loading->{

                    }
                    StateLoading.Success->{

                        staff=viewModel.staffById
                        Log.d("1111",staff.nameRu.toString())
                        binding.filmsInFilmography.adapter=adapter
                        setContent()
                        binding.backButton.setOnClickListener {
                            findNavController().popBackStack()
                        }
                    }
                    is StateLoading.Error->{}
                }
            }
        }





    }


    private fun setContent() {

        binding.nameStaffFilmography.text = staff.nameRu

        staff.mapKeyProfessionFilm.forEach { map ->
            val chip = LayoutInflater.from(context).inflate(R.layout.chips,null,false) as Chip
            chip.id = ViewCompat.generateViewId()

            chip.setOnClickListener {
                lifecycleScope.launch {
                    setListFilmography(map.value)
                }

            }
            chip.text = map.key.description + " " + map.value.size
            binding.chipGroup.addView(chip)
        }
        binding.chipGroup.check(binding.chipGroup. children.first().id)

        staff.mapKeyProfessionFilm.firstNotNullOfOrNull { it->it }?.let{
            setListFilmography(it.value)
        }

    }

    private  fun setListFilmography(mapElement: List<FilmsInStaff>) {
        adapter.setData(mapElement)

    }

    private fun clickItem(item:Int){
        val bundle=Bundle().apply { putInt(MainFragment.ID_FILM,item) }
        findNavController().navigate(
            resId= R.id.action_filmographyFragment_to_filmPage,
            args = bundle
        )

    }
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}