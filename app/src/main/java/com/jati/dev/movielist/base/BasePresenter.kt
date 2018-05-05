package com.jati.dev.movielist.base

import io.reactivex.disposables.CompositeDisposable

/**
 * Created by jati on 05/05/18
 */

abstract class BasePresenter {
    protected val compositeDisposable = CompositeDisposable()

    fun clearDisposable() {
        compositeDisposable.clear()
    }
}