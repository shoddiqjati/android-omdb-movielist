package com.jati.dev.movielist.model.response

import com.google.gson.annotations.SerializedName

data class MovieDetailsResponse(

        @SerializedName("Released")
        val released: String? = null,

        @SerializedName("Metascore")
        val metascore: String? = null,

        @SerializedName("imdbID")
        val imdbID: String? = null,

        @SerializedName("Plot")
        val plot: String? = null,

        @SerializedName("Director")
        val director: String? = null,

        @SerializedName("BoxOffice")
        val boxOffice: String? = null,

        @SerializedName("Title")
        val title: String? = null,

        @SerializedName("Website")
        val website: String? = null,

        @SerializedName("Actors")
        val actors: String? = null,

        @SerializedName("imdbRating")
        val imdbRating: String? = null,

        @SerializedName("imdbVotes")
        val imdbVotes: String? = null,

        @SerializedName("Response")
        val response: String? = null,

        @SerializedName("Runtime")
        val runtime: String? = null,

        @SerializedName("Type")
        val type: String? = null,

        @SerializedName("Awards")
        val awards: String? = null,

        @SerializedName("DVD")
        val dVD: String? = null,

        @SerializedName("Year")
        val year: String? = null,

        @SerializedName("Language")
        val language: String? = null,

        @SerializedName("Rated")
        val rated: String? = null,

        @SerializedName("Production")
        val production: String? = null,

        @SerializedName("Poster")
        val poster: String? = null,

        @SerializedName("Country")
        val country: String? = null,

        @SerializedName("Genre")
        val genre: String? = null,

        @SerializedName("Writer")
        val writer: String? = null
)