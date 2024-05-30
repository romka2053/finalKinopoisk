package com.roman.finalkinopoisk.presentation.staff

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.roman.finalkinopoisk.FilmsListAdapter
import com.roman.finalkinopoisk.R
import com.roman.finalkinopoisk.databinding.FragmentStaffPageBinding
import com.roman.finalkinopoisk.entity.CategoryFilms
import com.roman.finalkinopoisk.entity.StaffWithFilms
import com.roman.finalkinopoisk.presentation.SettingGetFilms
import com.roman.finalkinopoisk.presentation.StateLoading
import com.roman.finalkinopoisk.presentation.filmPage.FilmPageFragment
import com.roman.finalkinopoisk.presentation.homeList.MainFragment.Companion.ID_FILM
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StaffPageFragment : Fragment() {

    private var idStaff = 0
    private var _binding: FragmentStaffPageBinding? = null
    private val binding get() = _binding!!
    private val viewModel: StaffViewModel by viewModels()
    private lateinit var staff: StaffWithFilms
    private val adapter =
        FilmsListAdapter({ film -> onclickFilms(film) }, { film -> onClickAll(film) })

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
        }
        lifecycleScope.launch {


            viewModel.state.collect {
                when (it) {
                    StateLoading.Loading -> {
loading()
                    }
                    StateLoading.Success -> {
                        staff = viewModel.staffById
                        setPoster()

                        adapter.setData(
                            CategoryFilms(
                                staff.filmsPopular,
                                SettingGetFilms.FilmsInStaff(getString(R.string.the_best))
                            )
                        )
                        binding.filmListInActor.adapter = adapter

                        binding.filmographyCount.text = resources.getQuantityString(
                            R.plurals.filmographyCount,
                            staff.staff.films.size,
                            staff.staff.films.size
                        )

                        binding.toTheList.setOnClickListener {
                            val bundle=Bundle().apply {
                                putInt(ID_STAFF_PUT,idStaff)
                            }
                            findNavController().navigate(
                                args=bundle,
                                resId = R.id.action_staffPageFragment_to_filmographyFragment)
                        }

                        binding.backButton.setOnClickListener {
                            findNavController().popBackStack()
                        }
                        success()
                    }
                    is StateLoading.Error -> {
                        error_mess()
                    }
                }

            }
        }


    }
private fun loading(){
    binding.errorMassage.root.visibility=View.GONE
    binding.content.visibility=View.GONE
    binding.loader.visibility=View.VISIBLE
}
    private fun success(){
        binding.errorMassage.root.visibility=View.GONE
        binding.loader.visibility=View.GONE
        binding.content.visibility=View.VISIBLE

    }
    private fun error_mess(){
        binding.loader.visibility=View.GONE
        binding.content.visibility=View.GONE
        binding.errorMassage.root.visibility=View.VISIBLE
        binding.errorMassage.button.setOnClickListener {
            lifecycleScope.launch {
                viewModel.getStaffById(idStaff)
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

    fun onclickFilms(item: Int) {
        val bundle = Bundle().apply { putInt(ID_FILM, item) }
        findNavController().navigate(
            resId = R.id.action_staffPageFragment_to_filmPage,
            args = bundle
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun onClickAll(filmSetting: SettingGetFilms) {

    }

    companion object{
        const val ID_STAFF_PUT="id_staff_put"
    }
}