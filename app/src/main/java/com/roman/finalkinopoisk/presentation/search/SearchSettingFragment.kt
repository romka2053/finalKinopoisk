package com.roman.finalkinopoisk.presentation.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.slider.RangeSlider
import com.roman.finalkinopoisk.R
import com.roman.finalkinopoisk.databinding.FragmentSearchSettingBinding
import com.roman.finalkinopoisk.databinding.SearchFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchSettingFragment : Fragment() {
    private var _binding: FragmentSearchSettingBinding?=null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentSearchSettingBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    binding.ratingSlider.values= listOf(0.0f,10.0f)
binding.ratingSetting.setOnClickListener{
    Log.d("1111",binding.ratingSlider.values[1].toString())
}


    }


}