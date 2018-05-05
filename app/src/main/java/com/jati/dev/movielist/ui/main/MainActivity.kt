package com.jati.dev.movielist.ui.main

import android.support.v4.internal.view.SupportMenuItem
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import com.jati.dev.movielist.R
import com.jati.dev.movielist.base.BaseActivity
import com.jati.dev.movielist.model.MovieItem
import com.jati.dev.movielist.network.ApiManager
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity<MainPresenter>(), MainView {

    @Inject
    lateinit var apiManager: ApiManager

    private lateinit var searchView: SearchView
    private var searchItem: MenuItem? = null

    private val movieList = mutableListOf<MovieItem>()
    private val movieAdapter by lazy { MovieAdapter(movieList) }

    override fun onSetupLayout() {
        setContentView(R.layout.activity_main)
        appComponent?.inject(this)
        presenter = MainPresenter(apiManager, this)
    }

    override fun onViewReady() {
        with(rv_movie) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = movieAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        searchItem = menu?.findItem(R.id.action_search)
        searchView = SearchView(supportActionBar?.themedContext)
        searchItem?.setShowAsAction(SupportMenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or SupportMenuItem.SHOW_AS_ACTION_ALWAYS)
        searchItem?.actionView = searchView
        listenSearch(searchView)
        return true
    }

    private fun listenSearch(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return if (query.length > 3) {
                    presenter?.searchMovie(query)
                    true
                } else false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }

        })
    }

    override fun showResults(movies: List<MovieItem>, page: Int) {
        if (page == 1) {
            searchView.onActionViewCollapsed()
            searchItem?.collapseActionView()
            movieList.clear()
        }
        movieList.addAll(movies)
        movieAdapter.notifyDataSetChanged()
    }
}
