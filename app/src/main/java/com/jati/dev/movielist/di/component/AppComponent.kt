package com.jati.dev.movielist.di.component

import com.jati.dev.movielist.di.module.NetworkingModule
import com.jati.dev.movielist.ui.main.MainActivity
import dagger.Component
import javax.inject.Singleton

/**
 * Created by jati on 05/05/18
 */

@Singleton
@Component(modules = [(NetworkingModule::class)])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
}