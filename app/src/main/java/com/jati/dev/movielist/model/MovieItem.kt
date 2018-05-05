package com.jati.dev.movielist.model

import com.google.gson.annotations.SerializedName

/**
 * Created by jati on 05/05/18
 */
 
data class MovieItem(
        @SerializedName("Title")
        val title: String,
        @SerializedName("Year")
        val year: String,
        @SerializedName("imdbID")
        val imdbId: String,
        @SerializedName("Type")
        val type: String,
        @SerializedName("Poster")
        val poster: String
)

