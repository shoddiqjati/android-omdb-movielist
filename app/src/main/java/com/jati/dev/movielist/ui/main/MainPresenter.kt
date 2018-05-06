package com.jati.dev.movielist.ui.main

import com.jati.dev.movielist.base.BasePresenter
import com.jati.dev.movielist.network.ApiManager
import com.jati.dev.movielist.utils.Constants
import com.jati.dev.movielist.utils.getErrorDescription
import com.jati.dev.movielist.utils.handleThrowable

/**
 * Created by jati on 05/05/18
 */

class MainPresenter(private val apiManager: ApiManager, private val view: MainView) : BasePresenter() {

    private val query = HashMap<String, String>()

    fun searchMovie(keyword: String) {
        view.searchingMovie(true)
        query.clear()
        query[Constants.SEARCH_PARAMS] = keyword
        query[Constants.PAGE_PARAMS] = Constants.FIRST_PAGE
        query[Constants.API_KEY_PARAMS] = Constants.API_KEY
        compositeDisposable.add(
                apiManager.observableMovies(query)
                        .subscribe({
                            view.searchingMovie(false)
                            if (it.isSuccessful) {
                                it.body()?.let { view.showResults(it.movieList, Constants.FIRST_PAGE.toInt(), (it.total.toInt() >= 40)) }
                            } else {
                                it.errorBody()?.string()?.let { view.showError(getErrorDescription(it).error) }
                            }
                        }, {
                            view.searchingMovie(false)
                            view.showError(handleThrowable(it))
                        })
        )
    }

    fun loadMoreMovie(page: Int) {
        query[Constants.PAGE_PARAMS] = page.toString()
        compositeDisposable.add(
                apiManager.observableMovies(query)
                        .subscribe({
                            if (it.isSuccessful) {
                                it.body()?.let { view.showResults(it.movieList, page, (it.total.toInt() >= 40)) }
                            } else {
                                it.errorBody()?.string()?.let { view.showError(getErrorDescription(it).error) }
                            }
                        }, { view.showError(handleThrowable(it)) })
        )
    }
}