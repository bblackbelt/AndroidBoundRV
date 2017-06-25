package com.blackbelt.androidboundrv.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.blackbelt.androidboundrv.misc.UtilHelper;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@ToString
@Data
public class Movie implements Parcelable, SimpleBindableItem {

    @Expose
    @SerializedName("poster_path")
    @Getter
    private String posterPath;

    @Expose
    @Getter
    private String overview;

    @Expose
    @Getter
    @SerializedName("release_date")
    private Date releaseDate;

    @Expose
    @Getter
    private boolean adult;

    @Expose
    @Getter
    private boolean video;

    @Expose
    @Getter
    @SerializedName("genre_ids")
    List<Integer> genreIds;

    @Expose
    @Getter
    private int id;

    @Expose
    @Getter
    @SerializedName("original_title")
    private String originalTitle;

    @Expose
    @Getter
    @SerializedName("original_language")
    private String originalLanguage;

    @Expose
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
        dest.writeString(this.posterPath);
        dest.writeString(this.overview);
        dest.writeLong(this.releaseDate != null ? releaseDate.getTime() : -1);
        dest.writeByte(this.adult ? (byte) 1 : (byte) 0);
        dest.writeByte(this.video ? (byte) 1 : (byte) 0);
        dest.writeList(this.genreIds);
        dest.writeInt(this.id);
        dest.writeString(this.originalTitle);
        dest.writeString(this.originalLanguage);
        dest.writeString(this.title);
        dest.writeString(this.backdropPath);
        dest.writeFloat(this.popularity);
        dest.writeLong(this.voteCount);
        dest.writeFloat(this.voteAverage);
    }

    protected Movie(Parcel in) {
        this.posterPath = in.readString();
        this.overview = in.readString();
        this.releaseDate = UtilHelper.convertMillisToDate(in.readLong());
        this.adult = in.readByte() != 0;
        this.video = in.readByte() != 0;
        this.genreIds = new ArrayList<Integer>();
        in.readList(this.genreIds, Integer.class.getClassLoader());
        this.id = in.readInt();
        this.originalTitle = in.readString();
        this.originalLanguage = in.readString();
        this.title = in.readString();
        this.backdropPath = in.readString();
        this.popularity = in.readFloat();
        this.voteCount = in.readLong();
        this.voteAverage = in.readFloat();
    }

    public Movie() {}

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {return new Movie(source);}

        @Override
        public Movie[] newArray(int size) {return new Movie[size];}
    };

    public String getTitle() {
        return TextUtils.isEmpty(title) ? originalTitle : title;
    }
}
