package com.jati.dev.movielist.model.response

import com.google.gson.annotations.SerializedName
import com.jati.dev.movielist.model.MovieItem

/**
 * Created by jati on 05/05/18
 */

data class MovieSearchResponse(
        @SerializedName("Search")
        val movieList: List<MovieItem> = mutableListOf(),
        @SerializedName("totalResults")
        val total: String
)