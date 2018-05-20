package com.blackbelt.androidboundrv.api.model;

import com.google.gson.annotations.Expose;

import java.util.List;

public class Images {

    @Expose
    private List<Image> backdrops;

    @Expose
    private List<Image> posters;

    public List<Image> getPosters() {
        return posters;
    }

    public List<Image> getBackdrops() {
        return backdrops;
    }
}
