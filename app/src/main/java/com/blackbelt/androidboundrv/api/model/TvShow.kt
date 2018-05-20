package com.blackbelt.androidboundrv.api.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.Date

data class TvShow(

        @Expose
        private val id: Int = 0,

        @Expose
        @SerializedName("poster_path")
        private val posterPath: String? = null,

        @Expose
        private val overview: String? = null,

        @Expose
        @SerializedName("first_air_date")
        private val firstAirDate: Date? = null,


        @Expose
        @SerializedName("genre_ids")
        internal var genreIds: List<String>? = null,

        @Expose
        @SerializedName("origin_country")
        internal var originCountry: List<String>? = null,


        @Expose
        @SerializedName("original_name")
        private val originalTitle: String? = null,


        @Expose
        @SerializedName("name")
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