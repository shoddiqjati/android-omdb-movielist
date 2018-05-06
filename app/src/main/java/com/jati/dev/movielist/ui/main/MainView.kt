package com.jati.dev.movielist.ui.main

import com.jati.dev.movielist.model.MovieItem

/**
 * Created by jati on 05/05/18
 */

interface MainView {
    fun showResults(movies: List<MovieItem>, page: Int, isFourPage: Boolean)
    fun searchingMovie(isSearching: Boolean)
}