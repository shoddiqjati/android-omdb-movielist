package com.jati.dev.movielist.ui.main

import android.content.Intent
import android.support.v4.internal.view.SupportMenuItem
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import com.jati.dev.movielist.R
import com.jati.dev.movielist.base.BaseActivity
import com.jati.dev.movielist.model.MovieItem
import com.jati.dev.movielist.network.ApiManager
import com.jati.dev.movielist.ui.detail.MovieDetailsActivity
import com.jati.dev.movielist.utils.*
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity<MainPresenter>(), MainView {

    @Inject
    lateinit var apiManager: ApiManager

    private lateinit var searchView: SearchView
    private var searchItem: MenuItem? = null
    private var isLoading = false

    private val movieList = mutableListOf<MovieItem>()
    private val movieAdapter by lazy {
        MovieAdapter(movieList, object : RecyclerViewItemClickListener {
            override fun onItemClicked(item: MovieItem) {
                val detailIntent = Intent(this@MainActivity, MovieDetailsActivity::class.java)
                detailIntent.putExtra(Constants.IMDB_ID, item.imdbId)
                startActivity(detailIntent)
            }
        })
    }
    private val progressDialog by lazy {
        ProgressDialog(this@MainActivity, getString(R.string.searching_movie))
    }

    private val alertDialog: AlertDialog by lazy {
        AlertDialog.Builder(this@MainActivity)
                .setPositiveButton(R.string.ok) { dialog, _ ->
                    dialog.dismiss()
                }.create()
    }

    override fun onSetupLayout() {
        setContentView(R.layout.activity_main)
        appComponent?.inject(this)
        presenter = MainPresenter(apiManager, this)
    }

    override fun onViewReady() {
        initUiState(true)
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

    private fun initUiState(isFirst: Boolean) {
        if (isFirst) {
            lin_search.show()
            rv_movie.hide()
        } else {
            lin_search.hide()
            rv_movie.show()
        }
    }

    override fun showResults(movies: List<MovieItem>, page: Int, isFourPage: Boolean) {
        initUiState(false)
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

    override fun showError(message: String?) {
        message?.let {
            alertDialog.setMessage(message)
            alertDialog.show()
        }
    }
}
