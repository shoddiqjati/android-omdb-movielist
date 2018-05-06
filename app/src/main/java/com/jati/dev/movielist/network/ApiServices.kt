package com.jati.dev.movielist.network

import com.jati.dev.movielist.model.response.MovieDetailsResponse
import com.jati.dev.movielist.model.response.MovieSearchResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * Created by jati on 05/05/18
 */

interface ApiServices {
    @GET("/")
    fun searchMovies(@QueryMap query: HashMap<String, String>): Observable<Response<MovieSearchResponse>>

    @GET("/")
    fun getMovieDetails(@QueryMap query: HashMap<String, String>): Observable<Response<MovieDetailsResponse>>
}