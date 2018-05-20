package com.blackbelt.androidboundrv.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


import java.util.List;

public class Configuration {

    public static class ImageConfiguration {

        public String getBaseUrl() {
            return baseUrl;
        }

        public String getSecureBaseUrl() {
            return secureBaseUrl;
        }

        public List<String> getBackdropSizes() {
            return backdropSizes;
        }

        public List<String> getLogoSizes() {
            return logoSizes;
        }

        public List<String> getPosterSizes() {
            return posterSizes;
        }

        public List<String> getProfileSizes() {
            return profileSizes;
        }

        public List<String> getStillSizes() {
            return stillSizes;
        }

        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }

        public void setSecureBaseUrl(String secureBaseUrl) {
            this.secureBaseUrl = secureBaseUrl;
        }

        public void setBackdropSizes(List<String> backdropSizes) {
            this.backdropSizes = backdropSizes;
        }

        public void setLogoSizes(List<String> logoSizes) {
            this.logoSizes = logoSizes;
        }

        public void setPosterSizes(List<String> posterSizes) {
            this.posterSizes = posterSizes;
        }

        public void setProfileSizes(List<String> profileSizes) {
            this.profileSizes = profileSizes;
        }

        public void setStillSizes(List<String> stillSizes) {
            this.stillSizes = stillSizes;
        }

        public static final ImageConfiguration EMPTY = new ImageConfiguration();

        @Expose
        @SerializedName("base_url")
        private String baseUrl;

        @Expose
        @SerializedName("secure_base_url")
        private String secureBaseUrl;

        @Expose
        @SerializedName("backdrop_sizes")
        private List<String> backdropSizes;

        @Expose
        @SerializedName("logo_sizes")
        private List<String> logoSizes;

        @Expose
        @SerializedName("poster_sizes")
        private List<String> posterSizes;

        @Expose
        @SerializedName("profile_sizes")
        private List<String> profileSizes;

        @Expose
        @SerializedName("still_sizes")
        private List<String> stillSizes;

    }

    public static final Configuration EMPTY = new Configuration();

    @Expose
    @SerializedName("images")
    private ImageConfiguration imagesConfiguration;

    @Expose
    @SerializedName("change_keys")
    private List<String> changeKeys;

    public void setImagesConfiguration(ImageConfiguration imagesConfiguration) {
        this.imagesConfiguration = imagesConfiguration;
    }

    public void setChangeKeys(List<String> changeKeys) {
        this.changeKeys = changeKeys;
    }

    public ImageConfiguration getImagesConfiguration() {
        return imagesConfiguration;
    }

    public List<String> getChangeKeys() {
        return changeKeys;
    }

    private int key;

    public int getKey() {
        return key;
    }

    public void setKey(int k) {
        key = k;
    }
}
