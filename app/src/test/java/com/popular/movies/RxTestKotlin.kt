package com.popular.movies

import com.popular.movies.mainPage.MainContract
import com.popular.movies.model.ModelGenres
import com.popular.movies.model.ModelMovies
import com.popular.movies.network.ApiHelper
import com.popular.movies.util.AppLog
import org.junit.Test

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import org.mockito.Mock
import org.mockito.Mockito.verify


class RxTestKotlin {

    @Mock
    internal var mView: MainContract.View? = null

    val API_KEY = "6cb025fee4cbfb787415c63d5fa87583"

    @Test
    fun rxTest() {
        val num = Observable.just(1, 2, 3)
        val str = Observable.just("satu", "dua", "tiga")
        val composite = CompositeDisposable()

        composite.add(
                Observable.zip<Int, String, String>(num, str,
                        BiFunction<Int, String, String> { mnumber, mtext ->
                            return@BiFunction "$mnumber : $mtext"
                        })
                        .observeOn(Schedulers.io())
                        .subscribe { println(it) }
        )
    }

    @Test
    fun movies() {
        val genresRequest: Observable<ModelGenres> = ApiHelper.service
                .getListGenreName(API_KEY)
        val popularRequest: Observable<ModelMovies> = ApiHelper.service
                .getListPopularMovie(API_KEY, 1)
        Observable.zip<ModelGenres, ModelMovies, List<ModelMovies.Result>>(
                genresRequest, popularRequest,
                BiFunction<ModelGenres, ModelMovies, List<ModelMovies.Result>> { genre, movie ->
                    val movieSize = movie.results?.size ?: 0
                    val genreSize = genre.genres?.size ?: 0
                    for (i in 0 until movieSize) {
                        for (j in 0 until genreSize) {
                            if (movie.results?.get(i)?.genreIds?.get(0) == genre.genres?.get(j)?.id) {
                                movie.results?.get(i)?.genres = "Genre : ${genre.genres?.get(j)?.name}"
                                break
                            }
                        }
                    }
                    return@BiFunction movie.results!!
                })
                .subscribeOn(Schedulers.io())
                //.observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { response ->
                            verify(mView)?.showMovies(response)
                        },
                        { err ->
                            verify(mView)?.showError(err.localizedMessage)
                        },
                        {
                            verify(mView)?.showLoading(false)
                            AppLog.d("Chains Completed")
                        })
    }

}
