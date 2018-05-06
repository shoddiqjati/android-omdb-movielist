package com.jati.dev.movielist.ui.detail

import com.jati.dev.movielist.model.response.MovieDetailsResponse

/**
 * Created by jati on 06/05/18
 */
 
interface MovieDetailView {
    fun getDetail(isGetting: Boolean)
    fun showMovie(movie: MovieDetailsResponse)
    fun showError(message: String?)
}