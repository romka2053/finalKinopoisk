package com.roman.finalkinopoisk.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.roman.finalkinopoisk.FilmsListAdapter
import com.roman.finalkinopoisk.R
import com.roman.finalkinopoisk.databinding.FragmentStaffPageBinding
import com.roman.finalkinopoisk.entity.StaffWithFilms
import com.roman.finalkinopoisk.presentation.MainFragment.Companion.PUT_PARAM1
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StaffPageFragment : Fragment() {

    private var idStaff = 0
    private var _binding: FragmentStaffPageBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var staff: StaffWithFilms
    private val adapter=FilmsListAdapter{id->onclickFilms(id)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idStaff = it.getInt(FilmPageFragment.PUT_STAFF_ID)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentStaffPageBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.getStaffById(idStaff)


            viewModel.state.collect {
                when (it) {
                    StateLoading.GetInfoFilm -> {}
                    StateLoading.Success -> {
                    staff=viewModel.staffById
                    setPoster()
                        adapter.setData(staff.filmsPopular)
                        binding.filmListInActor.adapter=adapter

                        binding.filmographyCount.text=resources.getQuantityString(R.plurals.filmographyCount,staff.staff.films.size,staff.staff.films.size)

                        binding.toTheList.setOnClickListener {
                            findNavController().navigate(R.id.action_staffPageFragment_to_filmographyFragment)
                        }
                    }
                    is StateLoading.Error -> {

                    }
                }

            }
        }


    }

    private fun setPoster() {

        with(binding) {
            Glide.with(staffPagePoster.context)
                .load(staff.staff.posterUrl)
                .centerCrop()
                .into(staffPagePoster)
            binding.staffPageName.text = staff.staff.nameRu
            binding.staffPagePersoneJob.text = staff.staff.profession

        }
    }

    fun onclickFilms(item:Int){
        val bundle=Bundle().apply { putInt(PUT_PARAM1,item) }
        findNavController().navigate(
            resId=R.id.action_staffPageFragment_to_filmPage,
            args = bundle
        )
    }

}