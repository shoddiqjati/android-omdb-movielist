package com.jati.dev.movielist.utils

import com.jati.dev.movielist.model.MovieItem

/**
 * Created by jati on 06/05/18
 */
 
interface RecyclerViewItemClickListener {
    fun onItemClicked(item: MovieItem)
}