package com.blackbelt.androidboundrv.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.blackbelt.androidboundrv.misc.UtilHelper;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.Date;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
public class TvShow implements Parcelable, SimpleBindableItem {

    @Expose
    @Getter
    private int id;

    @Expose
    @SerializedName("poster_path")
    @Getter
    private String posterPath;

    @Expose
    @Getter
    private String overview;

    @Expose
    @Getter
    @SerializedName("first_air_date")
    private Date firstAirDate;


    @Expose
    @Getter
    @SerializedName("genre_ids")
    List<String> genreIds;

    @Expose
    @Getter
    @SerializedName("origin_country")
    List<String> originCountry;


    @Expose
    @Getter
    @SerializedName("original_name")
    private String originalTitle;


    @Expose
    @SerializedName("name")
    private String title;

    @Expose
    @Getter
    @SerializedName("backdrop_path")
    private String backdropPath;

    @Expose
    @Getter
    private float popularity;

    @Expose
    @Getter
    @SerializedName("vote_count")
    private long voteCount;

    @Expose
    @Getter
    @SerializedName("vote_average")
    private float voteAverage;

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.posterPath);
        dest.writeString(this.overview);
        dest.writeLong(UtilHelper.convertDateToMillisec(this.firstAirDate));
        dest.writeStringList(this.genreIds);
        dest.writeStringList(this.originCountry);
        dest.writeString(this.originalTitle);
        dest.writeString(this.title);
        dest.writeString(this.backdropPath);
        dest.writeFloat(this.popularity);
        dest.writeLong(this.voteCount);
        dest.writeFloat(this.voteAverage);
    }

    public TvShow() {}

    protected TvShow(Parcel in) {
        this.id = in.readInt();
        this.posterPath = in.readString();
        this.overview = in.readString();
        this.firstAirDate = UtilHelper.convertMillisToDate(in.readLong());
        this.genreIds = in.createStringArrayList();
        this.originCountry = in.createStringArrayList();
        this.originalTitle = in.readString();
        this.title = in.readString();
        this.backdropPath = in.readString();
        this.popularity = in.readFloat();
        this.voteCount = in.readLong();
        this.voteAverage = in.readFloat();
    }

    public static final Parcelable.Creator<TvShow> CREATOR = new Parcelable.Creator<TvShow>() {
        @Override
        public TvShow createFromParcel(Parcel source) {return new TvShow(source);}

        @Override
        public TvShow[] newArray(int size) {return new TvShow[size];}
    };

    public String getTitle() {
        return TextUtils.isEmpty(originalTitle) ? title : originalTitle;
    }
}