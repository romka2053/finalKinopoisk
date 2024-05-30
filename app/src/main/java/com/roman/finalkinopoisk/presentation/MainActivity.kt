package com.roman.finalkinopoisk.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController

import com.roman.finalkinopoisk.R
import com.roman.finalkinopoisk.databinding.ActivityMainBinding
import com.roman.finalkinopoisk.entity.room.CollectionRoom
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        lifecycleScope.launch {
            val collectionRoom=viewModel.getCollections()
            if (collectionRoom.isEmpty())
            {
                val intent = Intent(this@MainActivity, FirstStartActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                binding = ActivityMainBinding.inflate(layoutInflater)
                setContentView(binding.root)
                val navHostFragment =
                    supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
                navController = navHostFragment.navController
                setupWithNavController(binding.bottomNavigationView, navController)
            }

        }






    }







}
