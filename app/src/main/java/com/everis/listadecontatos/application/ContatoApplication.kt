package com.everis.listadecontatos.application

import android.app.Application
import com.everis.listadecontatos.helpers.HelperDB

class ContatoApplication : Application() {

    // put this here to everyone access the same instance.
    var helperDB: HelperDB? = null
    //protect variable
    private set
    companion object{
       lateinit var instance : ContatoApplication
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
        //intance of helperDB
        helperDB = HelperDB(this)
    }
}