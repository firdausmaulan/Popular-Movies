package com.popular.movies.mainPage

import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.jakewharton.rxbinding2.view.RxView
import com.popular.movies.R
import com.popular.movies.model.ModelMovies
import com.popular.movies.mvp.BaseMvpActivity
import com.popular.movies.util.RecyclerUtil
import kotlinx.android.synthetic.main.activity_main.*
import com.jakewharton.rxbinding2.widget.RxTextView
import com.popular.movies.util.AppLog
import com.popular.movies.util.hideSoftInput
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit


class MainActivity : BaseMvpActivity<MainContract.View,
        MainContract.Presenter>(), MainContract.View {

    override var mPresenter: MainContract.Presenter = MainPresenter()

    private var mMovieList: ArrayList<ModelMovies.Result> = ArrayList()
    private lateinit var mMainAdapter: MainAdapter
    private var mPage = 1
    private var mAllowedToRequest = true
    private var isExit = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setRecycleView()
        setSwipeRefreshLayout()
        setAction()
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

        RxTextView.textChanges(etSearch)
                //.filter { charSequence -> charSequence.length >= 3 }
                .debounce(2, TimeUnit.SECONDS)
                .map { charSequence -> charSequence.toString() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { string ->
                    AppLog.d(string)
                    mPage = 1
                    mMovieList.clear()
                    if (string.isEmpty()) {
                        mPresenter.loadPopularMovies(mPage)
                    } else if (string.length >= 3) {
                        mPresenter.loadSearchMovies(mPage, string)
                    }
                }
    }

    private fun onRefresh() {
        mPage = 1
        mMovieList.clear()
        if (etSearch.text.length >= 3) {
            mPresenter.loadSearchMovies(mPage, etSearch.text.toString())
        } else {
            mPresenter.loadPopularMovies(mPage)
        }
    }

    private fun loadMore() {
        mAllowedToRequest = false
        if (etSearch.text.length >= 3) {
            mPresenter.loadSearchMovies(mPage, etSearch.text.toString())
        } else {
            mPresenter.loadPopularMovies(mPage)
        }
    }

    override fun showMovies(listMovie: List<ModelMovies.Result>?) {
        listMovie?.let { mMovieList.addAll(it) }
        mMainAdapter.notifyDataSetChanged()
        mAllowedToRequest = true
        mPage++
    }

    override fun showLoading(status: Boolean) {
        srlMovieList?.isRefreshing = status
    }

    override fun hideKeyboard() {
        hideSoftInput()
    }

    override fun onBackPressed() {
        if (isExit) {
            super.onBackPressed()
        } else {
            isExit = true
            Toast.makeText(this,
                    "Please click BACK again to exit",
                    Toast.LENGTH_SHORT).show()
            Handler().postDelayed({ isExit = false }, 5000)
        }
    }
}
