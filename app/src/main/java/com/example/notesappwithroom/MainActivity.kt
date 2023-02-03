package com.example.notesappwithroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController

class MainActivity : AppCompatActivity() {

    private lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.myNavHostFragment)

        setupActionBarWithNavController(navController)

        //CREATING VIEW MODEL USING VIEWMODEL FACTORY
        //  val vIewModelFactory = ConstructorVIewModelFactory("CodingFlow")
        //   ViewModelProvider(this, vIewModelFactory).get(ConstructorViewModel::class.java)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}