package com.jati.dev.movielist.ui.main

import com.jati.dev.movielist.base.BasePresenter
import com.jati.dev.movielist.network.ApiManager
import com.jati.dev.movielist.utils.Constants

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
                            view.showResults(it.movieList, Constants.FIRST_PAGE.toInt(), (it.total.toInt() >= 40))
                        }, {
                            view.searchingMovie(false)
                        })
        )
    }

    fun loadMoreMovie(page: Int) {
        query[Constants.PAGE_PARAMS] = page.toString()
        compositeDisposable.add(
                apiManager.observableMovies(query)
                        .subscribe({
                            view.showResults(it.movieList, page, (it.total.toInt() >= 40))
                        }, {})
        )
    }
}