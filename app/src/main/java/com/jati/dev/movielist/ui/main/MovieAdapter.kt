package com.jati.dev.movielist.ui.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.jati.dev.movielist.R
import com.jati.dev.movielist.model.MovieItem
import com.jati.dev.movielist.utils.RecyclerViewItemClickListener
import kotlinx.android.synthetic.main.item_movie.view.*

/**
 * Created by jati on 05/05/18
 */

class MovieAdapter(val data: MutableList<MovieItem>, val clickListener: RecyclerViewItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val ITEM = 0
    private val LOADING = 1
    private var isLoadingAdded = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
                MovieHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_load_more, parent, false)
                LoadingHolder(view)
            }
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MovieHolder) {
            holder.bindData(data[position])
        }
    }

    override fun getItemViewType(position: Int): Int =
            if (position == data.size - 1 && isLoadingAdded) LOADING else ITEM

    inner class MovieHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindData(item: MovieItem) {
            itemView.tv_title.text = item.title
            itemView.tv_year.text = item.year
            itemView.tv_type.text = item.type


            Glide.with(itemView)
                    .load(item.poster)
                    .apply(RequestOptions().placeholder(R.drawable.ic_image).error(R.drawable.ic_broken_image))
                    .into(itemView.iv_poster)

            itemView.container.setOnClickListener {
                clickListener.onItemClicked(item)
            }
        }
    }

    inner class LoadingHolder(view: View) : RecyclerView.ViewHolder(view)

    fun add(movie: MovieItem) {
        data.add(movie)
        notifyItemInserted(data.size - 1)
    }

    fun addLoadingFooter() {
        isLoadingAdded = true
        add(MovieItem())
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false

        val position = data.size - 1

        data.removeAt(position)
        notifyItemRemoved(position)
    }
}