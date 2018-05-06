package com.jati.dev.movielist.ui.detail

import com.jati.dev.movielist.base.BasePresenter
import com.jati.dev.movielist.network.ApiManager
import com.jati.dev.movielist.utils.Constants
import com.jati.dev.movielist.utils.getErrorDescription
import com.jati.dev.movielist.utils.handleThrowable

/**
 * Created by jati on 06/05/18
 */

class MovieDetailPresenter(private val apiManager: ApiManager, private val view: MovieDetailView) : BasePresenter() {
    fun getMovieDetail(imdbId: String) {
        val query = HashMap<String, String>()
        query[Constants.IMDB_ID_PARAMS] = imdbId
        query[Constants.API_KEY_PARAMS] = Constants.API_KEY
        view.getDetail(true)
        compositeDisposable.add(
                apiManager.observableMovieDetails(query)
                        .subscribe({
                            view.getDetail(false)
                            if (it.isSuccessful) {
                                it.body()?.let { view.showMovie(it) }
                            } else {
                                it.errorBody()?.string()?.let { view.showError(getErrorDescription(it).error) }
                            }
                        }, {
                            view.getDetail(false)
                            view.showError(handleThrowable(it))
                        })
        )
    }
}