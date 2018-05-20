package com.blackbelt.androidboundrv.api.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.util.ArrayList


data class PaginatedResponse<T>(@Expose var page: Int? = 0,
                                @Expose @SerializedName("total_pages")
                                var totalPages: Int? = 0,
                                @Expose
                                @SerializedName("total_results")
                                var totalResults: Int = 0,
                                @Expose
                                var results: List<T> = ArrayList()) {
    companion object {

        fun <T> getEmpty(): PaginatedResponse<T> {
            return PaginatedResponse()
        }
    }
}