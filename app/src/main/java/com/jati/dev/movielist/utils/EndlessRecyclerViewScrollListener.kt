package com.jati.dev.movielist.utils

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.LinearLayoutManager


abstract class EndlessRecyclerViewScrollListener(val layoutManager: LinearLayoutManager
) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        if (!isLoading() && !isLastPage()) {
            if (totalItemCount <= 30
                    && firstVisibleItemPosition >= 0) {
                loadMoreItems()
            }
        }
    }

    protected abstract fun loadMoreItems()

    abstract fun isLastPage(): Boolean

    abstract fun isLoading(): Boolean
}