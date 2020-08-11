package com.example.mygw.repository

import javax.inject.Inject

class MainRepository  @Inject constructor(){

    fun loadPaints():String{
        return "Pints have been loaded"
    }

    fun loadProjects(): String {
        return "Projects have been loaded"
    }

}