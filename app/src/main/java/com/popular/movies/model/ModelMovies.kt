package com.popular.movies.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ModelMovies : Serializable {

    @SerializedName("page")
    @Expose
    var page: Int? = null
    @SerializedName("total_results")
    @Expose
    var totalResults: Int? = null
    @SerializedName("total_pages")
    @Expose
    var totalPages: Int? = null
    @SerializedName("results")
    @Expose
    var results: List<Result>? = null

    inner class Result : Serializable {
        @SerializedName("vote_count")
        @Expose
        var voteCount: Int? = null
        @SerializedName("id")
        @Expose
        var id: Int? = null
        @SerializedName("video")
        @Expose
        var video: Boolean? = null
        @SerializedName("vote_average")
        @Expose
        var voteAverage: Double? = null
        @SerializedName("title")
        @Expose
        var title: String? = null
        @SerializedName("popularity")
        @Expose
        var popularity: Double? = null
        @SerializedName("poster_path")
        @Expose
        var posterPath: String? = null
        @SerializedName("original_language")
        @Expose
        var originalLanguage: String? = null
        @SerializedName("original_title")
        @Expose
        var originalTitle: String? = null
        @SerializedName("genre_ids")
        @Expose
        var genreIds: List<Int>? = null
        @SerializedName("backdrop_path")
        @Expose
        var backdropPath: String? = null
        @SerializedName("adult")
        @Expose
        var adult: Boolean? = null
        @SerializedName("overview")
        @Expose
        var overview: String? = null
        @SerializedName("release_date")
        @Expose
        var releaseDate: String? = null
        @SerializedName("genres")
        @Expose
        var genres: String? = null
        @SerializedName("isFavourite")
        @Expose
        var isFavourite: Boolean? = null
    }
}