package com.popular.movies.util

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager

object RecyclerUtil {
    fun isLoadMore(dy: Int, layoutManager: GridLayoutManager, allowedToRequest: Boolean) : Boolean {
        if (dy > 0) {
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount
            val pastVisiblesItems = layoutManager.findFirstVisibleItemPosition()
            if (visibleItemCount + pastVisiblesItems >= totalItemCount && allowedToRequest) {
                if (totalItemCount > 0) {
                    return true
                }
            }
        }
        return false
    }

    fun isLoadMore(dy: Int, layoutManager: LinearLayoutManager, allowedToRequest: Boolean) : Boolean {
        if (dy > 0) {
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount
            val pastVisiblesItems = layoutManager.findFirstVisibleItemPosition()
            if (visibleItemCount + pastVisiblesItems >= totalItemCount && allowedToRequest) {
                if (totalItemCount > 0) {
                    return true
                }
            }
        }
        return false
    }
}
