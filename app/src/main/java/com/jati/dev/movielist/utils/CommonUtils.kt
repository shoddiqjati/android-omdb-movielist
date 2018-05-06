package com.jati.dev.movielist.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import com.jati.dev.movielist.model.response.ErrorResponse
import java.net.UnknownHostException

/**
 * Created by jati on 06/05/18
 */

fun getErrorDescription(error: String) = Gson().fromJson(error, ErrorResponse::class.java)

fun handleThrowable(throwable: Throwable): String {
    return when (throwable) {
        is UnknownHostException -> "No Internet Connection"
        else -> throwable.localizedMessage
    }
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}