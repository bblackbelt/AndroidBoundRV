package com.blackbelt.androidboundrv.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class PaginatedResponse<T> {

    public static final class Date {

        @Expose
        @Getter
        private String minimum;

        @Expose
        @Getter
        private String maximum;

    }

    @Expose
    @Setter
    @Getter
    private int page;

    @Expose
    @Getter
    @Setter
    @SerializedName("total_pages")
    private int totalPages;

    @Expose
    @Getter
    @Setter
    @SerializedName("total_results")
    private int totalResults;

    @Expose
    @Getter
    List<T> results = new ArrayList<>();

    @Expose
    @Getter
    Date dates;

    public static <T> PaginatedResponse<T> getEmpty() {
        return new PaginatedResponse<>();
    }
}