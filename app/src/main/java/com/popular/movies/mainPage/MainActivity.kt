package com.popular.movies.mainPage

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.popular.movies.R
import com.popular.movies.model.ModelMovies
import com.popular.movies.mvp.BaseMvpActivity
import com.popular.movies.util.RecyclerUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseMvpActivity<MainContract.View,
        MainContract.Presenter>(), MainContract.View {

    override var mPresenter: MainContract.Presenter = MainPresenter()

    private var mMovieList: ArrayList<ModelMovies.Result> = ArrayList()
    private lateinit var mMainAdapter: MainAdapter
    private var mLastIndex = 1
    private var mAllowedToRequest = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setRecycleView()
        setSwipeRefreshLayout()
        setAction()
        mPresenter.loadPopularMovies(mLastIndex)
    }

    private fun setSwipeRefreshLayout() {
        srlMovieList?.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorAccent))
    }

    private fun setRecycleView() {
        val glm = GridLayoutManager(this, 2)
        rvMovieList?.layoutManager = glm
        mMainAdapter = MainAdapter(this, mMovieList)
        rvMovieList?.adapter = mMainAdapter
    }

    private fun setAction() {
        srlMovieList?.setOnRefreshListener { onRefresh() }

        // handle event onItemClick and onClickFavourite
        mMainAdapter.setOnItemClickListener(object : MainAdapter.ClickListener {
            override fun onItemClick(position: Int, v: View, model: ModelMovies.Result?) {

            }

            override fun onFavouriteClick(position: Int, v: View, model: ModelMovies.Result?) {

            }
        })
        // read last recycle view item for load more
        rvMovieList?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView?.layoutManager as GridLayoutManager
                if (RecyclerUtil.isLoadMore(dy, layoutManager, mAllowedToRequest)) {
                    loadMore()
                }
            }
        })
    }

    private fun onRefresh() {
        srlMovieList?.isRefreshing = true
        mLastIndex = 1
        mMovieList.clear()
        mPresenter.loadPopularMovies(mLastIndex)
    }

    private fun loadMore() {
        srlMovieList?.isRefreshing = true
        mAllowedToRequest = false
        mPresenter.loadPopularMovies(mLastIndex)
    }

    override fun showPopularMovies(listMovie: List<ModelMovies.Result>?) {
        listMovie?.let { mMovieList.addAll(it) }
        srlMovieList?.isRefreshing = false
        mMainAdapter.notifyDataSetChanged()
        mAllowedToRequest = true
        mLastIndex++
    }
}
