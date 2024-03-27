package com.roman.finalkinopoisk.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.roman.finalkinopoisk.R
import com.roman.finalkinopoisk.databinding.SearchFragmentBinding
import com.roman.finalkinopoisk.presentation.recyclerview.FilmsListSearchAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SearchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val viewModel: SearchViewModel by viewModels()
    private var _binding: SearchFragmentBinding?=null
    private val binding get() = _binding!!
    private val adapter= FilmsListSearchAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = SearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchRecycler.adapter=adapter
        startSearch()
        binding.imageView3.setOnClickListener {
            findNavController().navigate(resId=R.id.action_searchItem_to_searchSettingFragment)
        }
        binding.searchEdit.addTextChangedListener {

            SearchViewModel.keyword=binding.searchEdit.text.toString()
            viewModel.newKey()
            startSearch()


        }


    }

    private fun startSearch(){
        viewModel.searchFilms.onEach {
            adapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

    }
}