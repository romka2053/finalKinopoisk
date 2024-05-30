package com.roman.finalkinopoisk.presentation.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.roman.finalkinopoisk.R
import com.roman.finalkinopoisk.databinding.SearchFragmentBinding
import com.roman.finalkinopoisk.presentation.homeList.MainFragment
import com.roman.finalkinopoisk.presentation.recyclerview.FilmsListSearchAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by activityViewModels()
    private var _binding: SearchFragmentBinding? = null
    private val binding get() = _binding!!
    private val adapter = FilmsListSearchAdapter(){int->clickItem(int)}
    private var edit=false
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

        binding.searchRecycler.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
            adapter.loadStateFlow.collectLatest {

                when (it.refresh) {
                    LoadState.Loading -> {
                        binding.searchRecycler.visibility=View.INVISIBLE
                        binding.loadSearch.visibility=View.VISIBLE
                    }
                    is LoadState.NotLoading -> {
                        binding.searchRecycler.visibility=View.VISIBLE
                        binding.loadSearch.visibility=View.INVISIBLE
                    }
                    is LoadState.Error -> {}
                }


            }
        }
        }

        startSearch()

        binding.imageView3.setOnClickListener {
            findNavController().navigate(resId = R.id.action_searchItem_to_searchSettingFragment)
        }




    }

    override fun onStart() {
        super.onStart()

        binding.searchEdit.addTextChangedListener {
            lifecycleScope.launch {
                val text=it.toString()
                delay(1000)
                if (binding.searchEdit.text.toString()==text)
                {
                    viewModel.setKeyword(it.toString())
                    startSearch()
                }


            }

        }
    }
    private fun startSearch() {

        viewModel.newKey()


        viewModel.searchFilms.onEach {
            Log.d("1111","cttt")
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                adapter.submitData(it)
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)


    }

private fun clickItem(item:Int){
    val bundle=Bundle().apply { putInt(MainFragment.ID_FILM,item) }
    findNavController().navigate(
        resId=R.id.action_searchItem_to_filmPage,
        args = bundle
    )
}
    override fun onDestroy() {
        _binding = null

        super.onDestroy()
    }

}