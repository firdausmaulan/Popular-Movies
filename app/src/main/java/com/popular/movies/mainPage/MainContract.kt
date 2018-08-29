package com.popular.movies.mainPage

import com.popular.movies.model.ModelMovies
import com.popular.movies.mvp.BaseMvpPresenter
import com.popular.movies.mvp.BaseMvpView


object MainContract {

    interface View : BaseMvpView {
        fun showPopularMovies(listMovie: List<ModelMovies.Result>?)
    }

    interface Presenter : BaseMvpPresenter<View> {
        fun loadPopularMovies(lastIndex: Int?)
    }
}