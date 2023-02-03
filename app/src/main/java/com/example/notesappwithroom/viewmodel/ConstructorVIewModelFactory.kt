package com.example.demoviewmodel.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class ConstructorVIewModelFactory(private  var  name :String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ConstructorViewModel::class.java)){
            return ConstructorViewModel(name) as T
        }
        throw  IllegalArgumentException("ViewModel class not Found")
    }
}