package com.popular.movies.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ModelGenres : Serializable {

    @SerializedName("genres")
    @Expose
    var genres: List<Genre>? = null

    inner class Genre : Serializable {
        @SerializedName("id")
        @Expose
        var id: Int? = null
        @SerializedName("name")
        @Expose
        var name: String? = null
    }
}