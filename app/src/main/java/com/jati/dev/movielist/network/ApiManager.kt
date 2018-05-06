package com.jati.dev.movielist.network

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by jati on 05/05/18
 */

class ApiManager(private val services: ApiServices) {
    fun observableMovies(query: HashMap<String, String>) = services
            .searchMovies(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun observableMovieDetails(query: HashMap<String, String>) = services
            .getMovieDetails(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}