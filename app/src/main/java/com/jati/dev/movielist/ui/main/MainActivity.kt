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
import com.jati.dev.movielist.utils.EndlessRecyclerViewScrollListener
import com.jati.dev.movielist.utils.ItemDivider
import com.jati.dev.movielist.utils.ProgressDialog
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity<MainPresenter>(), MainView {

    @Inject
    lateinit var apiManager: ApiManager

    private lateinit var searchView: SearchView
    private var searchItem: MenuItem? = null
    private var isLoading = false

    private val movieList = mutableListOf<MovieItem>()
    private val movieAdapter by lazy { MovieAdapter(movieList) }
    private val progressDialog by lazy {
        ProgressDialog(this@MainActivity, getString(R.string.searching_movie))
    }

    override fun onSetupLayout() {
        setContentView(R.layout.activity_main)
        appComponent?.inject(this)
        presenter = MainPresenter(apiManager, this)
    }

    override fun onViewReady() {
        with(rv_movie) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = movieAdapter
            addItemDecoration(ItemDivider(this@MainActivity))
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

    override fun showResults(movies: List<MovieItem>, page: Int, isFourPage: Boolean) {
        if (page == 1) {
            isLoading = false
            searchView.onActionViewCollapsed()
            searchItem?.collapseActionView()
            movieList.clear()
            movieList.addAll(movies)
            movieAdapter.notifyDataSetChanged()
        } else {
            isLoading = false
            movieAdapter.removeLoadingFooter()
            movieList.addAll(movies)
            movieAdapter.notifyItemInserted(movieList.size - movies.size)
        }

        if (isFourPage) {
            rv_movie.addOnScrollListener(object : EndlessRecyclerViewScrollListener(rv_movie.layoutManager as LinearLayoutManager) {
                override fun loadMoreItems() {
                    isLoading = true
                    movieAdapter.addLoadingFooter()
                    presenter?.loadMoreMovie(page + 1)
                }

                override fun isLastPage(): Boolean {
                    return page == 4
                }

                override fun isLoading(): Boolean = isLoading

            })
        }
    }

    override fun searchingMovie(isSearching: Boolean) {
        if (isSearching) progressDialog.show() else progressDialog.dismiss()
    }
}
