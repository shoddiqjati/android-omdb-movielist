package com.jati.dev.movielist.ui.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.jati.dev.movielist.R
import com.jati.dev.movielist.model.MovieItem
import kotlinx.android.synthetic.main.item_movie.view.*

/**
 * Created by jati on 05/05/18
 */

class MovieAdapter(val data: MutableList<MovieItem>) : RecyclerView.Adapter<MovieAdapter.MovieHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.bindData(data[position])
    }

    inner class MovieHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindData(item: MovieItem) {
            itemView.tv_title.text = item.title
            itemView.tv_year.text = item.year
            itemView.tv_type.text = item.type

            Glide.with(itemView)
                    .load(item.poster)
                    .apply(RequestOptions().placeholder(R.drawable.ic_image).error(R.drawable.ic_broken_image))
                    .into(itemView.iv_poster)
        }
    }
}