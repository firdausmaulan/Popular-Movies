package com.popular.movies.mainPage

import com.popular.movies.model.ModelGenres
import com.popular.movies.model.ModelMovies
import com.popular.movies.mvp.BaseMvpPresenterImpl
import com.popular.movies.network.ApiHelper
import com.popular.movies.util.AppLog
import com.popular.movies.util.AppSecret
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainPresenter : BaseMvpPresenterImpl<MainContract.View>(),
        MainContract.Presenter {

    override fun loadPopularMovies(lastIndex: Int?) {
        mView?.showLoading(true)
        mView?.hideKeyboard()
        val genresRequest = ApiHelper.service.getListGenreName(AppSecret().API_KEY)
        var listGenre: List<ModelGenres.Genre>? = null
        genresRequest.flatMap { response ->
            listGenre = response.genres
            val popularRequest = ApiHelper.service.getListPopularMovie(AppSecret().API_KEY, lastIndex)
            return@flatMap popularRequest
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { response ->
                            response.results = setGenre(response.results, listGenre)
                            mView?.showLoading(false)
                            mView?.showMovies(response.results)
                        },
                        { err ->
                            mView?.showLoading(false)
                            AppLog.d(err.localizedMessage)
                        },
                        {
                            mView?.showLoading(false)
                            AppLog.d("Chains Completed")
                        }
                )
    }

    override fun loadSearchMovies(lastIndex: Int?, query: String?) {
        mView?.showLoading(true)
        mView?.hideKeyboard()
        val genresRequest = ApiHelper.service.getListGenreName(AppSecret().API_KEY)
        var listGenre: List<ModelGenres.Genre>? = null
        genresRequest.flatMap { response ->
            listGenre = response.genres
            val searchRequest = ApiHelper.service
                    .getListSearchMovie(AppSecret().API_KEY, lastIndex, query)
            return@flatMap searchRequest
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { response ->
                            response.results = setGenre(response.results, listGenre)
                            mView?.showLoading(false)
                            mView?.showMovies(response.results)
                        },
                        { err ->
                            mView?.showLoading(false)
                            AppLog.d(err.localizedMessage)
                        },
                        {
                            mView?.showLoading(false)
                            AppLog.d("Chains Completed")
                        }
                )
    }

    private fun setGenre(listMovie: List<ModelMovies.Result>?,
                         listGenre: List<ModelGenres.Genre>?): List<ModelMovies.Result>? {
        val movieSize = listMovie?.size ?: 0
        for (i in 0 until movieSize) {
            for (j in 0 until (listGenre?.size ?: 0)) {
                if (listMovie?.get(i)?.genreIds?.get(0) == listGenre?.get(j)?.id) {
                    listMovie?.get(i)?.genres = "Genre : ${listGenre?.get(j)?.name}"
                    break
                }
            }
        }
        return listMovie
    }
}