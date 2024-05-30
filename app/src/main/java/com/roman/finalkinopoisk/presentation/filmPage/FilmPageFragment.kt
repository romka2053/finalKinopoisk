package com.roman.finalkinopoisk.presentation.filmPage

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.roman.finalkinopoisk.FilmsListAdapter
import com.roman.finalkinopoisk.R
import com.roman.finalkinopoisk.databinding.AlertDialogCollectionBinding
import com.roman.finalkinopoisk.databinding.BottomSheetDialogLayoutBinding
import com.roman.finalkinopoisk.databinding.FragmentFilmPageBinding
import com.roman.finalkinopoisk.entity.*
import com.roman.finalkinopoisk.entity.room.CollectionRoom
import com.roman.finalkinopoisk.entity.room.FilmCollection
import com.roman.finalkinopoisk.presentation.*
import com.roman.finalkinopoisk.presentation.homeList.MainFragment
import com.roman.finalkinopoisk.presentation.homeList.MainFragment.Companion.ID_FILM
import com.roman.finalkinopoisk.presentation.pagingFilms.FilmsListInNetworkFragment
import com.roman.finalkinopoisk.presentation.recyclerview.CollectionAdapter
import com.roman.finalkinopoisk.presentation.recyclerview.GalleryListAdapterPrev
import com.roman.finalkinopoisk.presentation.recyclerview.StaffListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class FilmPageFragment : Fragment() {

    private var idFilm: Int = 0
    private var seasonsAndSeries: SeasonsAndSeriesList? = null
    private var similar: FilmsList? = null
    private var _binding: FragmentFilmPageBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FilmPageViewModel by viewModels()
    private val adapterActor = StaffListAdapter { film -> clickStaff(film) }
    private val adapterPersonJob = StaffListAdapter { film -> clickStaff(film) }
    private val adapterGallery = GalleryListAdapterPrev()
    private val similarAdapter =
        FilmsListAdapter({ film -> clickItem(film) }, { all -> clickAll(all) })


    private lateinit var data: FullInformationFilm
    private lateinit var collectionAdapter: CollectionAdapter
    private var idCollectionEdit = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idFilm = it.getInt(MainFragment.ID_FILM)

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.getFilmById(idFilm)
        }


        lifecycleScope.launch {

            viewModel.state.collect { state ->
                when (state) {
                    is StateLoading.Loading -> {
                        loading()
                    }
                    is StateLoading.Success -> {


                        data = viewModel.filmById
                        seasonsAndSeries = viewModel.seasonsAndSeries
                        Log.d("1111", seasonsAndSeries.toString())
                        similar = viewModel.similarFilms
                        binding.showSetting.setOnClickListener {
                            showBottomSheetDialog(view.context)
                        }
                        setPoster()
                        setSeasonsAndSeries()
                        setStaffRecyclers()
                        galleryRecyclers()
                        similarRecyclers()
                        success()

                    }
                    is StateLoading.Error -> {
                        error_mess()
                    }
                }
            }


        }


    }

    private fun loading() {
        binding.errorMassage.root.visibility=View.GONE
        binding.fragmentPageMain.visibility = View.GONE
        binding.fragmentPageLoad.visibility = View.VISIBLE
    }

    private fun success() {
        binding.errorMassage.root.visibility=View.GONE
        binding.fragmentPageLoad.visibility = View.GONE
        binding.fragmentPageMain.visibility = View.VISIBLE
    }

    private fun error_mess(){
        binding.fragmentPageMain.visibility=View.GONE
        binding.fragmentPageLoad.visibility=View.GONE
        binding.errorMassage.root.visibility=View.VISIBLE
        binding.errorMassage.button.setOnClickListener {
            lifecycleScope.launch {
                viewModel.getFilmById(idFilm)
            }
        }
    }

    private fun setSeasonsAndSeries() {
        if (seasonsAndSeries == null) {
            binding.seasonAndSeriesName.visibility = View.GONE
            binding.seasonAndSeriesAll.visibility = View.GONE
            binding.seasonAndSeriesValue.visibility = View.GONE
        } else {

            seasonsAndSeries?.let {
                if (it.items.isNotEmpty()) {
                    val textSeasons =
                        resources.getQuantityString(R.plurals.seasonsCount, it.total, it.total)
                    val textSeries =
                        resources.getQuantityString(R.plurals.seriesCount, it.count(), it.count())
                    val text = textSeasons + ", " + textSeries
                    binding.seasonAndSeriesValue.text = text
                    val bundle = Bundle().apply { putInt(ID_FILM, data.film.filmRoom.id) }
                    binding.seasonAndSeriesAll.setOnClickListener {
                        findNavController().navigate(
                            resId = R.id.action_filmPage_to_seasonsAndSeriesFragment,
                            args = bundle
                        )
                    }
                } else {
                    val text = "Информация отсутствует"
                    binding.seasonAndSeriesValue.text = text
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
            val textRatingName =
                "${dataFilm.rating?.let { it.toString() + " ," } ?: ""} ${dataFilm.nameRu ?: ""}"
            val year = dataFilm.year?.let { it.toString() + ", " } ?: ""
            val genre = dataFilm.genres?.getOrNull(0)?.let { it.genre + ", " } ?: ""
            val genre2 = dataFilm.genres?.getOrNull(1)?.genre ?: ""
            val country = dataFilm.countries?.getOrNull(0)?.country ?: ""
            binding.countryDurationAge.text = country
            yearGenres.text = year + genre + genre2
            ratingName.text = textRatingName
            setIconView(dataFilm.filmViewed)
            filmViedIcon.setOnClickListener {
                lifecycleScope.launch {
                    val isViewFilm = viewModel.updateViewFilm(dataFilm.id)
                    setIconView(isViewFilm)
                }
            }

            setImageIcons()
            // Иконка ЛЮБИМЫЕ

            filmLikeIcon.setOnClickListener {
                it as ImageView
                lifecycleScope.launch {
                    it.setImageResource(clickLikeOrFavorites(FirstStartActivity.LIST_COLLECTION_STANDARD[0]))
                }
            }

            //Иконка ИЗБРАННОЕ

            filmFavoritesIcon.setOnClickListener {
                it as ImageView
                lifecycleScope.launch {
                    it.setImageResource(clickLikeOrFavorites(FirstStartActivity.LIST_COLLECTION_STANDARD[1]))
                }
            }


        }
    }


    private fun setIconView(isViewFilm: Boolean) {
        val resource = if (isViewFilm) {
            R.drawable.icon_invisible_checked
        } else {
            R.drawable.icon_invisible
        }
        binding.filmViedIcon.setImageResource(resource)
    }

    private fun showBottomSheetDialog(context: Context) {
        collectionAdapter = CollectionAdapter(
            { name, sql -> clickCollection(name, sql) },
            { clickAddCollection() })
        val bottomSheetDialog = BottomSheetDialog(context)
        val bindingBottomSheetDialog = BottomSheetDialogLayoutBinding.inflate(layoutInflater)
        bottomSheetDialog.setContentView(bindingBottomSheetDialog.root)
        Glide.with(bindingBottomSheetDialog.imagePosterAll2)
            .load(data.film.filmRoom.posterUrlPreview)
            .centerCrop()
            .into(bindingBottomSheetDialog.imagePosterAll2)
        bindingBottomSheetDialog.nameFilmAll2.text = data.film.filmRoom.nameRu
        val genre =
            data.film.filmRoom.genres.getOrNull(0)?.let { it.genre.toString() } ?: let { "" }
        val year = data.film.filmRoom.year?.let { it.toString() + " " } ?: let { "" }
        val textYearGenre = year + "," + genre
        bindingBottomSheetDialog.yearGenreFilm2.text = textYearGenre
        bindingBottomSheetDialog.closeIcon.setOnClickListener {
            bottomSheetDialog.cancel()
        }
        lifecycleScope.launch {

            collectionAdapter.setData(
                viewModel.getAllCollectionAndCountFilms(),
                data.film.collection
            )
            bindingBottomSheetDialog.listCollection.adapter = collectionAdapter


        }




        bottomSheetDialog.show()
    }


    private fun setStaffRecyclers() {
        with(binding) {
            allActor.text = data.staffListActor.size.toString()
            adapterActor.setData(data.staffListActor)
            staffActorListAdapter.adapter = adapterActor

            allPersonJob.text = data.staffListPersonJob.size.toString()
            adapterPersonJob.setData(data.staffListPersonJob)
            staffPersonJobListAdapter.adapter = adapterPersonJob
        }
    }

    private fun galleryRecyclers() {
        binding.allGallery.setOnClickListener {
            val bundle = Bundle().apply { putInt(ID_FILM, idFilm) }
            findNavController().navigate(
                resId = R.id.action_filmPage_to_galleryFragment,
                args = bundle
            )
        }
        adapterGallery.setData(data.galleryList)
        binding.galleryListAdapter.adapter = adapterGallery

    }

    private fun similarRecyclers() {

        val filmSimilar = SettingGetFilms.FilmsSimilar(getString(R.string.similar), idFilm)
        similarAdapter.setData(CategoryFilms(similar!!, filmSimilar))
        binding.similarFilmsAdapter.adapter = similarAdapter
        binding.allSimilar.setOnClickListener {
            val bundle = Bundle().apply {
                putParcelable(FilmsListInNetworkFragment.PUT_PARCELABLE, filmSimilar)
                putString(FilmsListInNetworkFragment.NAME_COLLECTION, filmSimilar.nameCategory)
            }
            findNavController().navigate(
                resId = R.id.action_filmPage_to_filmsListInNetworkFragment,
                args = bundle
            )
        }

    }

    private suspend fun clickLikeOrFavorites(collection: CollectionRoom): Int {

        val checkCollectionInFilm =
            viewModel.getCollectionInFilm(collection.name_collection, data.film.filmRoom.id)
        val filmCollection = FilmCollection(data.film.filmRoom.id, collection.name_collection)
        if (checkCollectionInFilm) viewModel.deleteFilmCollection(filmCollection)
        else viewModel.addFilmCollection(filmCollection)
        viewModel.reloadFilmById(idFilm)
        data = viewModel.filmById
        return checkLikeOrFavorites(collection)
    }

    private suspend fun checkLikeOrFavorites(collection: CollectionRoom): Int {
        val checkButton =
            viewModel.getCollectionInFilm(collection.name_collection, data.film.filmRoom.id)
        return if (checkButton) collection.image_active
        else collection.image

    }

    private fun setImageIcons() {
        lifecycleScope.launch {
            binding.filmFavoritesIcon.setImageResource(checkLikeOrFavorites(FirstStartActivity.LIST_COLLECTION_STANDARD[1]))
        }
        lifecycleScope.launch {
            binding.filmLikeIcon.setImageResource(checkLikeOrFavorites(FirstStartActivity.LIST_COLLECTION_STANDARD[0]))
        }
    }

    private fun clickStaff(item: Int) {

        val bundle = Bundle().apply { putInt(PUT_STAFF_ID, item) }
        findNavController().navigate(
            resId = R.id.action_filmPage_to_staffPageFragment,
            args = bundle
        )
    }

    private suspend fun clickCollection(nameCollection: String, sql: String) {
        idCollectionEdit = nameCollection
        when (sql) {
            "INSERT" -> {
                viewModel.addFilmCollection(FilmCollection(data.film.filmRoom.id, nameCollection))
            }
            "DELETE" -> {
                viewModel.deleteFilmCollection(
                    FilmCollection(
                        data.film.filmRoom.id,
                        nameCollection
                    )
                )
            }

        }
        setImageIcons()
    }

    private fun showDialog() {


        val dialog = Dialog(requireContext(), R.style.CustomAlertDialog)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)

        val binding = AlertDialogCollectionBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)

        val alertDialog = MaterialAlertDialogBuilder(requireContext(), R.style.CustomAlertDialog)
        alertDialog.setTitle(getString(R.string.error))
            .setMessage(R.string.error_massage)
            .setPositiveButton("OK") { dialog, _ -> dialog.cancel() }
            .setCancelable(false)

        binding.closeAllertDialog.setOnClickListener {

            dialog.cancel()
        }
        binding.button2.setOnClickListener {

            lifecycleScope.launch {
                val nameCollection = binding.editTextTextPersonName.text.toString()
                try {

                    viewModel.addCollectionToRoom(CollectionRoom(name_collection = nameCollection))
                    viewModel.addFilmCollection(
                        FilmCollection(
                            data.film.filmRoom.id,
                            nameCollection
                        )
                    )
                    viewModel.reloadFilmById(idFilm)
                    data = viewModel.filmById
                    collectionAdapter.setData(
                        viewModel.getAllCollectionAndCountFilms(),
                        data.film.collection
                    )
                    dialog.cancel()

                } catch (e: Exception) {
                    alertDialog.show()

                }


            }
        }
        dialog.show()
    }

    private fun clickAddCollection() {
        showDialog()
    }

    companion object {
        const val PUT_STAFF_ID = "put_staff_id"

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun clickAll(all: SettingGetFilms) {

    }

    private fun clickItem(film: Int) {

    }
}


