package com.roman.finalkinopoisk.presentation.homeList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.roman.finalkinopoisk.HomeListAdapter
import com.roman.finalkinopoisk.R
import com.roman.finalkinopoisk.databinding.FragmentMainBinding
import com.roman.finalkinopoisk.entity.CategoryFilms
import com.roman.finalkinopoisk.presentation.pagingFilms.FilmsListInNetworkFragment
import com.roman.finalkinopoisk.presentation.SettingGetFilms
import com.roman.finalkinopoisk.presentation.StateLoading
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val categoryList = mutableListOf<CategoryFilms>()
    private val viewModel: HomeListViewModel by viewModels()
   private var listCategoryFilms:List<SettingGetFilms> = emptyList()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listCategoryFilms=listOf(
            SettingGetFilms.PremiersGetSetting(getString(R.string.premiers)),
            SettingGetFilms.Top250GetSetting(getString(R.string.TOP250)),
            SettingGetFilms.TopPopularGetSetting(getString(R.string.popular))
        )

        val adapter = HomeListAdapter({ film -> clickItem(film) }, { all -> clickCategory(all) })
        binding.homeAdapter.adapter = adapter
        lifecycleScope.launch{
            viewModel.getAllCategoryFilms(listCategoryFilms)
        }
        lifecycleScope.launch {

            viewModel.state.collect {
                when (it) {
                    StateLoading.Loading -> {
                        loading()

                    }
                    StateLoading.Success -> {
                        categoryList.clear()
                        categoryList.addAll(viewModel.categoryFilms)
                        adapter.setData(categoryList.toList())
                        success()
                    }
                    is StateLoading.Error -> {
                        Log.d("1111",it.massage)
                        error_mess()
                    }

                }


            }


        }


    }

    private fun loading() {
        binding.errorMassage.root.visibility=View.GONE
        binding.content.visibility = View.GONE
        binding.loader.visibility = View.VISIBLE

    }

    private fun success() {
        binding.loader.visibility = View.GONE
        binding.errorMassage.root.visibility=View.GONE
        binding.content.visibility = View.VISIBLE


    }

    private fun error_mess(){
        binding.loader.visibility=View.GONE
        binding.content.visibility=View.GONE
        binding.errorMassage.root.visibility=View.VISIBLE
        binding.errorMassage.button.setOnClickListener {
            lifecycleScope.launch {
                viewModel.getAllCategoryFilms(listCategoryFilms)
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun clickItem(item: Int) {
        val bundle = Bundle().apply { putInt(ID_FILM, item) }
        findNavController().navigate(
            resId = R.id.action_mainFragment_to_filmPage,
            args = bundle
        )

    }

    private fun clickCategory(films: SettingGetFilms) {

        val bundle = Bundle().apply {
            putParcelable(FilmsListInNetworkFragment.PUT_PARCELABLE, films)
            putString(FilmsListInNetworkFragment.NAME_COLLECTION, films.nameCategory)
        }
        findNavController().navigate(
            resId = R.id.action_mainFragment_to_filmsListInNetworkFragment,
            args = bundle
        )

    }

    companion object {
        const val ID_FILM = "put_param1"
    }


    override fun onPause() {

        super.onPause()
        categoryList.clear()
    }

    override fun onDestroy() {

        _binding = null

        super.onDestroy()

    }

}