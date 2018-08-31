package com.popular.movies.mainPage

import com.popular.movies.model.ModelMovies
import com.popular.movies.mvp.BaseMvpPresenter
import com.popular.movies.mvp.BaseMvpView


object MainContract {

    interface View : BaseMvpView {
        fun showMovies(listMovie: List<ModelMovies.Result>?)

        fun showLoading(status: Boolean)

        fun hideKeyboard()
    }

    interface Presenter : BaseMvpPresenter<View> {
        fun loadPopularMovies(lastIndex: Int?)

        fun loadSearchMovies(lastIndex: Int?, query: String?)
    }
}