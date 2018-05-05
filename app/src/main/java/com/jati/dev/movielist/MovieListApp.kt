package com.jati.dev.movielist

import android.app.Application
import com.jati.dev.movielist.di.component.AppComponent
import com.jati.dev.movielist.di.component.DaggerAppComponent
import com.jati.dev.movielist.di.module.NetworkingModule

/**
 * Created by jati on 05/05/18
 */
 
 class MovieListApp : Application() {
    val appComponent: AppComponent? by lazy {
        DaggerAppComponent
                .builder()
                .networkingModule(NetworkingModule())
                .build()
    }
}