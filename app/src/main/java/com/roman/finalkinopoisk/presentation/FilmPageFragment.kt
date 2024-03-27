package com.roman.finalkinopoisk.presentation

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.roman.finalkinopoisk.R
import com.roman.finalkinopoisk.databinding.AlertDialogCollectionBinding
import com.roman.finalkinopoisk.databinding.BottomSheetDialogLayoutBinding
import com.roman.finalkinopoisk.databinding.FragmentFilmPageBinding
import com.roman.finalkinopoisk.entity.FullInformationFilm
import com.roman.finalkinopoisk.entity.room.CollectionRoom
import com.roman.finalkinopoisk.entity.room.FilmCollection
import com.roman.finalkinopoisk.presentation.recyclerview.CollectionAdapter
import com.roman.finalkinopoisk.presentation.recyclerview.GalleryListAdapter
import com.roman.finalkinopoisk.presentation.recyclerview.StaffListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow

@AndroidEntryPoint
class FilmPageFragment : Fragment() {

    private var idFilm: Int = 0
    private var _binding: FragmentFilmPageBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()
    private val adapterActor = StaffListAdapter{ film -> clickStaff(film) }
    private val collectionAdapter = CollectionAdapter ({ id, sql -> clickCollection(id, sql)},{clickAddCollection() })
    private val adapterPersonJob = StaffListAdapter { film -> clickStaff(film) }
    private val adapterGallery = GalleryListAdapter()
    private lateinit var data: FullInformationFilm
    private val stateLoadingCollection = MutableStateFlow<StateLoading>(StateLoading.Success)
    private var idCollectionEdit = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idFilm = it.getInt(MainFragment.PUT_PARAM1)

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        lifecycleScope.launch {

            viewModel.getFilmById(idFilm)
            viewModel.state.collect { state ->
                when (state) {
                    is StateLoading.GetInfoFilm -> {}
                    is StateLoading.Success -> {
                        data = viewModel.filmById
                        binding.showSetting.setOnClickListener {
                            showBottomSheetDialog(view.context)
                        }

                        setPoster()
                        setStaffRecyclers()
                        galleryRecyclers()
                    }
                    is StateLoading.Error -> {
                        binding.textView.text = state.massage
                    }
                }
            }


        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilmPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setPoster() {
        val dataFilm = data.film.filmRoom
        with(binding) {
                Glide.with(bigPoster.context)
                    .load(dataFilm.posterUrl)
                    .centerCrop()
                    .into(bigPoster)
                textView.text = dataFilm.shortDescription
                textView2.text = dataFilm.description
                val textRatingName = "${dataFilm.rating}, ${dataFilm.nameRu}"
                ratingName.text = textRatingName



        }
    }


    private fun showBottomSheetDialog(context: Context) {
        val bottomSheetDialog = BottomSheetDialog(context)
        val bindingBottomSheetDialog = BottomSheetDialogLayoutBinding.inflate(layoutInflater)
        bottomSheetDialog.setContentView(bindingBottomSheetDialog.root)
        Glide.with(bindingBottomSheetDialog.imagePosterAll2)
            .load(data.film.filmRoom.posterUrlPreview)
            .centerCrop()
            .into(bindingBottomSheetDialog.imagePosterAll2)
        bindingBottomSheetDialog.nameFilmAll2.text = data.film.filmRoom.nameRu

        lifecycleScope.launch {

            collectionAdapter.setData(viewModel.getAllCollection(), data.film.collection)
            bindingBottomSheetDialog.listCollection.adapter = collectionAdapter


        }




        bottomSheetDialog.show()
    }


    private fun setStaffRecyclers() {
        with(binding) {
                adapterActor.setData(data.staffListActor)
                staffActorListAdapter.adapter = adapterActor

                adapterPersonJob.setData(data.staffListPersonJob)
                staffPersonJobListAdapter.adapter = adapterPersonJob
        }
    }

    private fun galleryRecyclers() {

            adapterGallery.setData(data.galleryList)
            binding.galleryListAdapter.adapter = adapterGallery

    }


    private fun clickStaff(item: Int) {

        val bundle=Bundle().apply { putInt(PUT_STAFF_ID,item) }
        findNavController().navigate(
            resId=R.id.action_filmPage_to_staffPageFragment,
            args=bundle
        )
    }

    private suspend fun clickCollection(id: Int, sql: String) {
        idCollectionEdit = id
        when (sql) {
            "INSERT" -> {
                viewModel.addFilmCollection(FilmCollection(data.film.filmRoom.id,id))
                viewModel.getFilmById(idFilm)
            }
            "DELETE" -> {
                viewModel.deleteFilmCollection(FilmCollection(data.film.filmRoom.id,id))
                viewModel.getFilmById(idFilm)
            }

        }
        stateLoadingCollection.tryEmit(StateLoading.GetInfoFilm)
    }

private fun showDialog(){
    val dialog = Dialog(requireContext())
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setCancelable(false)
    val binding=AlertDialogCollectionBinding.inflate(layoutInflater)
    dialog.setContentView(binding.root)

    binding.button2.setOnClickListener {
        dialog.cancel()
        lifecycleScope.launch {
            val idNewCollection=viewModel.addCollectionToRoom(CollectionRoom(name = binding.editTextTextPersonName.text.toString())).toInt()
            viewModel.addFilmCollection(FilmCollection(data.film.filmRoom.id,idNewCollection))
            viewModel.getFilmById(idFilm)
            collectionAdapter.setData(viewModel.getAllCollection(), data.film.collection)
        }
    }

    dialog.show()
}
    private fun clickAddCollection(){
        showDialog()
    }

    companion object {
        const val PUT_STAFF_ID = "put_staff_id"
    }
}

