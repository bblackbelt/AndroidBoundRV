package com.blackbelt.androidboundrv.api.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Data;

@Data
public class Image implements Parcelable {

    @SerializedName("aspect_ratio")
    @Expose
    private float aspectRatio;

    @SerializedName("file_path")
    @Expose
    private String filePath;

    @SerializedName("height")
    @Expose
    private int height;

    @SerializedName("iso_639_1")
    @Expose
    private String iso6391;

    @SerializedName("vote_average")
    @Expose
    private float voteAverage;

    @SerializedName("vote_count")
    @Expose
    private int voteCount;

    @SerializedName("width")
    @Expose
    private int width;

    public Image() {
    }

    public String getFilePath() {
        return filePath;
    }

    public float getAspectRatio() {
        return aspectRatio;
    }


    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.aspectRatio);
        dest.writeString(this.filePath);
        dest.writeInt(this.height);
        dest.writeString(this.iso6391);
        dest.writeFloat(this.voteAverage);
        dest.writeInt(this.voteCount);
        dest.writeInt(this.width);
    }

    protected Image(Parcel in) {
        this.aspectRatio = in.readFloat();
        this.filePath = in.readString();
        this.height = in.readInt();
        this.iso6391 = in.readString();
        this.voteAverage = in.readFloat();
        this.voteCount = in.readInt();
        this.width = in.readInt();
    }

    public static final Parcelable.Creator<Image> CREATOR = new Parcelable.Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel source) {return new Image(source);}

        @Override
        public Image[] newArray(int size) {return new Image[size];}
    };
}
