package com.roman.finalkinopoisk.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.roman.finalkinopoisk.App
import com.roman.finalkinopoisk.HomeListAdapter
import com.roman.finalkinopoisk.R
import com.roman.finalkinopoisk.databinding.FragmentMainBinding
import com.roman.finalkinopoisk.entity.CategoryFilms
import com.roman.finalkinopoisk.entity.Films
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val categoryList= mutableListOf<CategoryFilms>()
    private val viewModel: MainViewModel by activityViewModels()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter= HomeListAdapter { film -> clickItem(film)}
        binding.homeAdapter.adapter=adapter


        lifecycleScope.launch {
            getFilmList()

            adapter.setData(categoryList.toList())
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

    fun clickItem(item:Int){
val bundle=Bundle().apply { putInt(PUT_PARAM1,item) }
        findNavController().navigate(
            resId=R.id.action_mainFragment_to_filmPage,
            args = bundle
        )

    }
    companion object {
        const val PUT_PARAM1 = "put_param1"
    }
   private suspend fun getFilmList(){
       val listCategoryFilms= listOf<SettingGetFilms>(
           SettingGetFilms.PremiersGetSetting(getString(R.string.premiers)),
           SettingGetFilms.Top250GetSetting(getString(R.string.TOP250)),
           SettingGetFilms.TopPopularGetSetting(getString(R.string.popular))
       )
    categoryList.addAll(viewModel.getAllCategoryFilms(listCategoryFilms))


    }

}