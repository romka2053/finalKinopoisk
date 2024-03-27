package com.roman.finalkinopoisk.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.gson.Gson

import com.roman.finalkinopoisk.R
import com.roman.finalkinopoisk.databinding.ActivityMainBinding
import com.roman.finalkinopoisk.entity.Genre
import com.roman.finalkinopoisk.entity.room.CollectionRoom
import com.roman.finalkinopoisk.entity.room.FilmCollection
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navView=binding.bottomNavigationView
        val navController= findNavController(R.id.nav_host)
        createCollection()


        val appBarConfiguration =AppBarConfiguration(
            setOf(
                R.id.mainFragment, R.id.searchItem,R.id.profileItem
            )

        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        }


private fun createCollection(){
    val listCollectionStandard= listOf("Хочу посмотреть","Любимое")
    lifecycleScope.launch {
        listCollectionStandard.forEach{
            viewModel.addCollectionToRoom(CollectionRoom(name=it, edit = false))

        }


    }

}


    }
