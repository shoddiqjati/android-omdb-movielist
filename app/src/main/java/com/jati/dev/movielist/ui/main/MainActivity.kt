package com.jati.dev.movielist.ui.main

import android.support.v4.internal.view.SupportMenuItem
import android.support.v7.widget.SearchView
import android.view.Menu
import com.jati.dev.movielist.R
import com.jati.dev.movielist.base.BaseActivity

class MainActivity : BaseActivity<MainPresenter>(), MainView {
    override fun onSetupLayout() {
        setContentView(R.layout.activity_main)
        appComponent?.inject(this)
    }

    override fun onViewReady() {

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = SearchView(supportActionBar?.themedContext)
        searchItem?.setShowAsAction(SupportMenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or SupportMenuItem.SHOW_AS_ACTION_ALWAYS)
        searchItem?.actionView = searchView
        listenSearch(searchView)
        return true
    }

    private fun listenSearch(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }
}
