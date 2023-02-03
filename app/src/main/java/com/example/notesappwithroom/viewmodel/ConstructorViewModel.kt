package com.example.demoviewmodel.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.DialogFragmentNavigatorDestinationBuilder

class ConstructorViewModel(name : String) :ViewModel() {

    var myName : String = name
    init {
        Log.d("ConstructorViewModel", "My Youtube Channel name is : $myName")
    }
}