package com.blackbelt.androidboundrv.api.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*


data class Movie(

        @Expose
        @SerializedName("poster_path")
        private val posterPath: String? = null,

        @Expose
        private val overview: String? = null,

        @Expose
        @SerializedName("release_date")
        private val releaseDate: Date? = null,

        @Expose
        private val adult: Boolean = false,

        @Expose
        private val video: Boolean = false,

        @Expose
        @SerializedName("genre_ids")
        internal var genreIds: List<Int>? = null,

        @Expose
        private val id: Int = 0,

        @Expose
        @SerializedName("original_title")
        private val originalTitle: String? = null,

        @Expose
        @SerializedName("original_language")
        private val originalLanguage: String? = null,

        @Expose
        private val title: String? = null,

        @Expose
        @SerializedName("backdrop_path")
        private val backdropPath: String? = null,

        @Expose
        private val popularity: Float = 0.toFloat(),

        @Expose
        @SerializedName("vote_count")
        private val voteCount: Long = 0,

        @Expose
        @SerializedName("vote_average")
        private val voteAverage: Float = 0.toFloat()
) : SimpleBindableItem {
    override fun getShowTitle(): String? {
        return title
    }

    override fun getShowPosterPath(): String? {
        return posterPath
    }

    override fun getShowBackdropPath(): String? {
        return backdropPath
    }

    override fun getShowId(): Int = id
}