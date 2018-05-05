package com.jati.dev.movielist.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.jati.dev.movielist.MovieListApp
import com.jati.dev.movielist.di.component.AppComponent

/**
 * Created by jati on 05/05/18
 */

abstract class BaseActivity<T> : AppCompatActivity() {
    val appComponent: AppComponent? by lazy {
        (application as MovieListApp).appComponent
    }

    var presenter: T? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onSetupLayout()
        onViewReady()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.let {
            (presenter as BasePresenter).clearDisposable()
        }
    }

    protected abstract fun onSetupLayout()
    protected abstract fun onViewReady()
}