package com.jati.dev.movielist.model.response

import com.google.gson.annotations.SerializedName

/**
 * Created by jati on 06/05/18
 */

data class ErrorResponse(
        @SerializedName("Response")
        val response: String,
        @SerializedName("Error")
        val error: String
)