package com.popular.movies.mvp

import android.content.Context
import android.support.annotation.StringRes

/**
 * Created by andrewkhristyan on 10/2/16.
 */
interface BaseMvpView {

    fun getContext(): Context

    fun showError(error: String?)

    fun showError(@StringRes stringResId: Int)

    fun showMessage(@StringRes srtResId: Int)

    fun showMessage(message: String)

}
