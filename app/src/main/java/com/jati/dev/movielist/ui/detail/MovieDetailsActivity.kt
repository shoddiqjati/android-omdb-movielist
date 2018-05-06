package com.jati.dev.movielist.ui.detail

import android.annotation.SuppressLint
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.jati.dev.movielist.R
import com.jati.dev.movielist.base.BaseActivity
import com.jati.dev.movielist.model.response.MovieDetailsResponse
import com.jati.dev.movielist.network.ApiManager
import com.jati.dev.movielist.utils.Constants
import com.jati.dev.movielist.utils.ProgressDialog
import kotlinx.android.synthetic.main.activity_movie_details.*
import javax.inject.Inject
import android.content.Intent
import android.util.Patterns
import com.jati.dev.movielist.utils.toast


class MovieDetailsActivity : BaseActivity<MovieDetailPresenter>(), MovieDetailView {

    @Inject
    lateinit var apiManager: ApiManager

    lateinit var imdbId: String
    lateinit var movie: MovieDetailsResponse

    private val progressDialog: ProgressDialog by lazy {
        ProgressDialog(this@MovieDetailsActivity, getString(R.string.getting_movie_details))
    }

    private val alertDialog: AlertDialog by lazy {
        AlertDialog.Builder(this@MovieDetailsActivity)
                .setPositiveButton(R.string.ok) { dialog, _ ->
                    dialog.dismiss()
                    finish()
                }
                .setNegativeButton(R.string.try_again) { dialog, _ ->
                    dialog.dismiss()
                    presenter?.getMovieDetail(imdbId)
                }.create()
    }

    override fun onSetupLayout() {
        setContentView(R.layout.activity_movie_details)
        setSupportActionBar(toolbar)
        appComponent?.inject(this)
        presenter = MovieDetailPresenter(apiManager, this)
        imdbId = intent.getStringExtra(Constants.IMDB_ID)
    }

    override fun onViewReady() {
        presenter?.getMovieDetail(imdbId)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_share -> {
                if (Patterns.WEB_URL.matcher(movie.website.toString()).matches()) {
                    val sendIntent = Intent()
                    sendIntent.action = Intent.ACTION_SEND
                    sendIntent.putExtra(Intent.EXTRA_TEXT, movie.website)
                    sendIntent.type = Constants.TYPE_TEXT
                    startActivity(Intent.createChooser(sendIntent, getString(R.string.send_to)))
                } else {
                    toast(getString(R.string.doent_have_website))
                }
            }
        }
        return true
    }

    override fun getDetail(isGetting: Boolean) {
        if (isGetting) progressDialog.show() else progressDialog.dismiss()
    }

    @SuppressLint("SetTextI18n")
    override fun showMovie(movie: MovieDetailsResponse) {
        this.movie = movie
        Glide.with(this)
                .load(movie.poster)
                .apply(RequestOptions().placeholder(R.drawable.ic_image).error(R.drawable.ic_broken_image))
                .into(iv_header)

        collapsing_toolbar.setExpandedTitleColor(ContextCompat.getColor(this, android.R.color.transparent))
        collapsing_toolbar.title = movie.title

        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        tv_title.text = movie.title
        tv_rating.text = movie.imdbRating
        tv_information.text = "${movie.type} | ${movie.year} | ${movie.runtime}"

        tv_production.text = movie.production
        tv_plot.text = movie.plot
        tv_writer.text = movie.writer
        tv_actors.text = movie.actors
        tv_director.text = movie.director
    }

    override fun showError(message: String?) {
        message?.let {
            alertDialog.setMessage(it)
            alertDialog.show()
        }
    }
}
