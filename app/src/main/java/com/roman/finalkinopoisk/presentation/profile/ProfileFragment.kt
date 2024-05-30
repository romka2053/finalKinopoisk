    package com.roman.finalkinopoisk.presentation.profile

    import android.app.Dialog
    import android.os.Bundle
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.view.Window
    import androidx.fragment.app.Fragment
    import androidx.fragment.app.viewModels
    import androidx.lifecycle.lifecycleScope
    import androidx.navigation.fragment.findNavController
    import com.google.android.material.dialog.MaterialAlertDialogBuilder
    import com.roman.finalkinopoisk.FilmsListAdapter
    import com.roman.finalkinopoisk.R
    import com.roman.finalkinopoisk.databinding.AlertDialogCollectionBinding
    import com.roman.finalkinopoisk.databinding.FragmentProfileBinding
    import com.roman.finalkinopoisk.entity.CategoryFilms
    import com.roman.finalkinopoisk.entity.FilmsList
    import com.roman.finalkinopoisk.entity.room.CollectionRoom
    import com.roman.finalkinopoisk.entity.room.FilmsAndActor
    import com.roman.finalkinopoisk.presentation.*
    import com.roman.finalkinopoisk.presentation.filmPage.FilmPageFragment
    import com.roman.finalkinopoisk.presentation.homeList.MainFragment
    import com.roman.finalkinopoisk.presentation.pagingFilms.FilmsListInNetworkFragment
    import com.roman.finalkinopoisk.presentation.recyclerview.CollectionInProfileAdapter
    import com.roman.finalkinopoisk.presentation.recyclerview.FilmAndStaffAdapter
    import dagger.hilt.android.AndroidEntryPoint
    import kotlinx.coroutines.launch

    @AndroidEntryPoint
    class ProfileFragment : Fragment() {

        private val viewModel: ProfileViewModel by viewModels()
        private var _binding: FragmentProfileBinding? = null
        private val binding get() = _binding!!
        private val adapterFilmsViewed =
            FilmsListAdapter({ film -> clickItem(film) }, { film -> onClickAll(film) })
        private val adapterCollection =
            CollectionInProfileAdapter ({ filmSett -> clickCollection(filmSett)},{collection ->clickDelete(collection) })


        private val adapterFilmsAndStaff =
            FilmAndStaffAdapter ({ filmOrStaff -> clickFilmOrStaff(filmOrStaff) },{clearHistoryClick()})


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
        }

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            _binding = FragmentProfileBinding.inflate(inflater, container, false)
            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            setViewedFilms()
            lifecycleScope.launch {
                setCollectionList()
                setFilmsAndStaff()

            }
            binding.createCollection.setOnClickListener {
                showDialog()
            }

        }

        private suspend fun setCollectionList() {
            val list = viewModel.getAllCollectionAndCountFilms()
            adapterCollection.setData(list)
            binding.collectionListViewed.isNestedScrollingEnabled = false
            binding.collectionListViewed.adapter = adapterCollection
        }

        private fun setViewedFilms() {
            lifecycleScope.launch {
                val filmsViewed = viewModel.getFilmsViewed()
                if (filmsViewed.isNotEmpty()) {
                    binding.viewedText.visibility = View.VISIBLE
                    binding.countViewedText.visibility = View.VISIBLE
                    binding.filmListViewed.visibility = View.VISIBLE
                    binding.countViewedText.text = filmsViewed.size.toString()
                    adapterFilmsViewed.setData(
                        CategoryFilms(
                            FilmsList(filmsViewed.size, filmsViewed),
                            SettingGetFilms.FilmsViewed(getString(R.string.viewed))
                        )
                    )
                    binding.filmListViewed.adapter = adapterFilmsViewed
                    binding.countViewedText.setOnClickListener {
                        onClickAll(SettingGetFilms.FilmsViewed(getString(R.string.viewed)))
                    }
                } else {
                    binding.viewedText.visibility = View.GONE
                    binding.countViewedText.visibility = View.GONE
                    binding.filmListViewed.visibility = View.GONE
                }
            }

        }

        private suspend fun setFilmsAndStaff() {
            val filmsAndStaff = viewModel.getFilmsAndStaff()
            binding.filmsAndStaffCount.text = filmsAndStaff.size.toString()
            adapterFilmsAndStaff.setData(filmsAndStaff)
            binding.filmAndStaffList.adapter = adapterFilmsAndStaff
        }

        private fun clickItem(film: Int) {
            val bundle = Bundle().apply { putInt(MainFragment.ID_FILM, film) }
            findNavController().navigate(
                resId = R.id.action_profileItem_to_filmPage,
                args = bundle
            )
        }


        override fun onDestroy() {
            super.onDestroy()
            _binding = null
        }

        private companion object {
            const val COUNT_HORIZONTAL_TABLE = 2
            const val MARGIN_ELEMENT = 40
        }

        private fun clickFilmOrStaff(filmOrStaff: FilmsAndActor) {
            val id: Int
            val nav: Int
            val putParam: String
            when (filmOrStaff) {
                is FilmsAndActor.FILMS -> {
                    id = filmOrStaff.film.id
                    nav = R.id.action_profileItem_to_filmPage
                    putParam = MainFragment.ID_FILM
                }
                is FilmsAndActor.STAFF -> {
                    id = filmOrStaff.staff.Id
                    nav = R.id.action_profileItem_to_staffPageFragment
                    putParam = FilmPageFragment.PUT_STAFF_ID
                }
            }
            val bundle = Bundle().apply { putInt(putParam, id) }
            findNavController().navigate(
                resId = nav,
                args = bundle
            )
        }

        private fun onClickAll(films: SettingGetFilms) {
            val bundle = Bundle().apply {
                putParcelable(FilmsListInNetworkFragment.PUT_PARCELABLE, films)
                putString(FilmsListInNetworkFragment.NAME_COLLECTION, films.nameCategory)
            }
            findNavController().navigate(
                resId = R.id.action_profileItem_to_filmsListInNetworkFragment,
                args = bundle
            )
        }

        private fun clickCollection(films: SettingGetFilms) {

            val bundle = Bundle().apply {
                putParcelable(FilmsListInNetworkFragment.PUT_PARCELABLE, films)
                putString(FilmsListInNetworkFragment.NAME_COLLECTION, films.nameCategory)
            }
            findNavController().navigate(
                resId = R.id.action_profileItem_to_filmsListInNetworkFragment,
                args = bundle
            )
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
                        val list = viewModel.getAllCollectionAndCountFilms()
                        adapterCollection.setData(list)
                        dialog.cancel()

                    } catch (e: Exception) {
                        alertDialog.show()

                    }


                }
            }
            dialog.show()
        }
        private fun clickDelete(collection:CollectionRoom){


                val alertDialog = MaterialAlertDialogBuilder(requireContext(), R.style.CustomAlertDialog)
                alertDialog.setTitle(getString(R.string.delete_collection))
                    .setMessage(R.string.delete_collection_text)
                    .setPositiveButton("OK") { _, _ -> deleteCollection(collection) }
                    .setNegativeButton("Not"){dialog, _ -> dialog.cancel()}
                    .setCancelable(true)
                    .show()



        }

        private fun deleteCollection(collection: CollectionRoom){
            lifecycleScope.launch {
                viewModel.deleteAllFilmCollectionWhereCollectionId(collection)
                val list = viewModel.getAllCollectionAndCountFilms()
                adapterCollection.setData(list)

            }
        }
        private fun clearHistoryClick() {

            val alertDialog = MaterialAlertDialogBuilder(requireContext(), R.style.CustomAlertDialog)
            alertDialog.setTitle(getString(R.string.delete_collection))
                .setMessage(R.string.delete_collection_text)
                .setPositiveButton("OK") { _, _ -> clearHistory() }
                .setNegativeButton("Not"){dialog, _ -> dialog.cancel()}
                .setCancelable(true)
                .show()


        }
        private fun clearHistory(){
            lifecycleScope.launch {
                viewModel.deleteAllHistory()
                setFilmsAndStaff()
            }
        }


    }
