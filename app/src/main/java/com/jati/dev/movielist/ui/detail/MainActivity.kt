package com.jati.dev.movielist.ui.detail

import com.jati.dev.movielist.R
import com.jati.dev.movielist.base.BaseActivity

class MainActivity : BaseActivity<MainPresenter>() {
    override fun onSetupLayout() {
        setContentView(R.layout.activity_main)
        appComponent?.inject(this)
    }

    override fun onViewReady() {

    }

}
