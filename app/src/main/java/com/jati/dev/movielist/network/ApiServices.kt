package com.jati.dev.movielist.network

import com.jati.dev.movielist.model.response.MovieSearchResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * Created by jati on 05/05/18
 */

interface ApiServices {
    @GET("/")
    fun searchMovies(@QueryMap queries: HashMap<String, String>): Observable<MovieSearchResponse>
}