package com.roman.finalkinopoisk.presentation.search

import android.location.GnssAntennaInfo.Listener
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.slider.RangeSlider
import com.roman.finalkinopoisk.R
import com.roman.finalkinopoisk.databinding.FragmentSearchSettingBinding
import com.roman.finalkinopoisk.databinding.SearchFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchSettingFragment : Fragment() {
    private val viewModel: SearchViewModel by activityViewModels()
    private var _binding: FragmentSearchSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setContent()
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

    }


    private fun setContent() {


        //startValue
        binding.ratingSlider.values = listOf(SearchViewModel.ratingFrom, SearchViewModel.ratingTo)
        val textRating =
            if (binding.ratingSlider.values[0] == 0f && binding.ratingSlider.values[1] == 10f) getText(
                R.string.any
            )
            else binding.ratingSlider.values[0].toString() + "-" + binding.ratingSlider.values[1].toString()
        binding.ratingSettingValue.text = textRating
        setValueCountryAndGenre()
        setValueYear()
        checkViewedFilms()
        setLinerButton()


        //Country
        binding.countrySettingValue.setOnClickListener {

            val bundle = Bundle().apply { putInt(PUT_TYPE_COUNTRY_OR_GENRE, TYPE_COUNTRY) }

            findNavController().navigate(
                resId = R.id.action_searchSettingFragment_to_countriesGenresFragment,
                args = bundle
            )
        }
        //YEAR

        binding.yearSettingValue.setOnClickListener {
            findNavController().navigate(R.id.action_searchSettingFragment_to_yearFromToFragment)
        }
        //GENRE
        binding.genreSettingValue.setOnClickListener {

            val bundle = Bundle().apply { putInt(PUT_TYPE_COUNTRY_OR_GENRE, TYPE_GENRE) }

            findNavController().navigate(
                resId = R.id.action_searchSettingFragment_to_countriesGenresFragment,
                args = bundle
            )
        }
        //RATING
        binding.ratingSlider.addOnChangeListener { slider, _, _ ->
            val textRating =
                if (slider.values[0] == 0f && slider.values[1] == 10f) getText(R.string.any) else slider.values[0].toString() + "-" + slider.values[1].toString()
            binding.ratingSettingValue.text = textRating
            viewModel.setRating(slider.values[0], slider.values[1])
        }
        //ViewedFilm
        val viewedFilmListener = View.OnClickListener {
            viewModel.setViewedFilms()
            checkViewedFilms()
        }
        binding.viewedFilmSettingIcon.setOnClickListener(viewedFilmListener)
        binding.viewedFilmSettingText.setOnClickListener(viewedFilmListener)
        //SEARCH


    }
private fun setLinerButton(){
    allButtNoSelected()
    binding.allType.setOnClickListener {

        viewModel.setTypeCinema(SearchViewModel.TypeCinema.ALL)
        allButtNoSelected()
    }
    binding.filmsType.setOnClickListener {

        viewModel.setTypeCinema(SearchViewModel.TypeCinema.FILMS)
        allButtNoSelected()
    }
    binding.serialType.setOnClickListener {

        viewModel.setTypeCinema(SearchViewModel.TypeCinema.SERIALS)
        allButtNoSelected()
    }

    allButtonSortedNoSelected()
    binding.dateSort.setOnClickListener {

        viewModel.setSortBy(SearchViewModel.SortBy.DATE)
        allButtonSortedNoSelected()
    }
    binding.popularSort.setOnClickListener {

        viewModel.setSortBy(SearchViewModel.SortBy.POPULAR)
        allButtonSortedNoSelected()
    }
    binding.ratingSort.setOnClickListener {

        viewModel.setSortBy(SearchViewModel.SortBy.RATING)
        allButtonSortedNoSelected()
    }
}
    private fun allButtNoSelected() {

        binding.allType.isSelected = false
        binding.filmsType.isSelected = false
        binding.serialType.isSelected = false
        when (viewModel.typeCinema) {
            SearchViewModel.TypeCinema.ALL -> {
                binding.allType.isSelected = true
            }
            SearchViewModel.TypeCinema.FILMS -> {
                binding.filmsType.isSelected = true
            }
            SearchViewModel.TypeCinema.SERIALS -> {
                binding.serialType.isSelected = true
            }
        }

    }

    private fun allButtonSortedNoSelected() {

        binding.dateSort.isSelected = false
        binding.popularSort.isSelected = false
        binding.ratingSort.isSelected = false
        when (viewModel.order) {
            SearchViewModel.SortBy.DATE -> {
                binding.dateSort.isSelected = true
            }
            SearchViewModel.SortBy.POPULAR -> {
                binding.popularSort.isSelected = true
            }
            SearchViewModel.SortBy.RATING -> {
                binding.ratingSort.isSelected = true
            }
        }

    }

    private fun setValueCountryAndGenre() {

        if (SearchViewModel.country.isNotEmpty()) {
            viewModel.genreAndCountry.countries.forEach {
                if (SearchViewModel.country[0] == it.id)
                    binding.countrySettingValue.text = it.name
            }
        } else {
            binding.countrySettingValue.text = getText(R.string.any)
        }


        if (SearchViewModel.genre.isNotEmpty()) {
            viewModel.genreAndCountry.genres.forEach {
                if (SearchViewModel.genre[0] == it.id)
                    binding.genreSettingValue.text = it.name
            }
        } else {
            binding.genreSettingValue.text = getText(R.string.any)
        }


    }


    private fun checkViewedFilms() {
        if (SearchViewModel.noViewedFilms)
            binding.viewedFilmSettingIcon.setImageResource(R.drawable.icon_invisible_checked)
        else
            binding.viewedFilmSettingIcon.setImageResource(R.drawable.icon_invisible)
    }

    private fun setValueYear() {
        val textYear =
            getString(R.string.from) + " " + SearchViewModel.yearFrom.toString() + " " + getString(R.string.to) + " " + SearchViewModel.yearTo.toString()
        binding.yearSettingValue.text = textYear
    }


    companion object {
        const val PUT_TYPE_COUNTRY_OR_GENRE = "TYPE_COUNTRY_OR_GENRE"
        const val TYPE_COUNTRY = 0
        const val TYPE_GENRE = 1
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}