package com.blackbelt.androidboundrv.api.model;

import com.google.gson.annotations.Expose;

import java.util.List;

import lombok.Data;

@Data
public class Images {

    @Expose
    private List<Image> backdrops;

    @Expose
    private List<Image> posters;

}
