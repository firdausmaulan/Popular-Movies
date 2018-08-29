package com.popular.movies.network

import com.popular.movies.model.ModelGenres
import com.popular.movies.model.ModelMovies
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("genre/movie/list")
    fun getListGenreName(@Query("api_key") apiKey: String?): Observable<ModelGenres>

    @GET("movie/popular")
    fun getListPopularMovie(@Query("api_key") apiKey: String?,
                            @Query("page") lastIndex: Int?): Observable<ModelMovies>
}