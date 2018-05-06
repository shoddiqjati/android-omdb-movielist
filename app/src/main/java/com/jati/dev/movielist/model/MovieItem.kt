package com.jati.dev.movielist.model

import com.google.gson.annotations.SerializedName

/**
 * Created by jati on 05/05/18
 */

data class MovieItem(
        @SerializedName("Title")
        val title: String? = null,
        @SerializedName("Year")
        val year: String? = null,
        @SerializedName("imdbID")
        val imdbId: String? = null,
        @SerializedName("Type")
        val type: String? = null,
        @SerializedName("Poster")
        val poster: String? = null
)

