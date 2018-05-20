package com.blackbelt.androidboundrv.api.model


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Image(

        @SerializedName("aspect_ratio")
        @Expose
        val aspectRatio: Float?,

        @SerializedName("file_path")
        @Expose
        val filePath: String?,

        @SerializedName("height")
        @Expose
        var height: Int?,

        @SerializedName("iso_639_1")
        @Expose
        private val iso6391: String?,

        @SerializedName("vote_average")
        @Expose
        val voteAverage: Float?,

        @SerializedName("vote_count")
        @Expose
        val voteCount: Int?,

        @SerializedName("width")
        @Expose
        var width: Int?)
