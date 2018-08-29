package com.popular.movies.mainPage

import com.popular.movies.model.ModelGenres
import com.popular.movies.mvp.BaseMvpPresenterImpl
import com.popular.movies.network.ApiHelper
import com.popular.movies.util.AppLog
import com.popular.movies.util.AppUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainPresenter : BaseMvpPresenterImpl<MainContract.View>(),
        MainContract.Presenter {

    override fun loadPopularMovies(lastIndex: Int?) {
        val genresRequest = ApiHelper.service.getListGenreName(AppUtil().API_KEY)
        var listGenre: List<ModelGenres.Genre>? = null
        genresRequest.flatMap { response ->
            listGenre = response.genres
            val popularRequest = ApiHelper.service.getListPopularMovie(AppUtil().API_KEY, lastIndex)
            return@flatMap popularRequest
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { response ->
                            val size = response.results?.size ?: 0
                            for (i in 0 until size) {
                                for (j in 0 until (listGenre?.size ?: 0)) {
                                    if (response.results?.get(i)?.genreIds?.get(0)
                                            == listGenre?.get(j)?.id) {
                                        response.results?.get(i)?.genres = listGenre?.get(j)?.name
                                        break
                                    }
                                }
                            }
                            mView?.showPopularMovies(response.results)
                        },
                        { err -> AppLog.d(err.localizedMessage) },
                        { AppLog.d("Chains Completed") }
                )
    }
}