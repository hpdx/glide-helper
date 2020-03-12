package com.anbetter.glide.helper.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 图片信息实体类
 *
 * Created by android_ls on 16/11/1.
 */

public class PhotoData implements Parcelable {

    public String photoId;

    public String originalUrl;

    public String thumbnailUrl;

    public int width;

    public int height;

    public PhotoData() {
    }

    @Override
    public String toString() {
        return "PhotoInfo{" +
                "photoId='" + photoId + '\'' +
                ", originalUrl='" + originalUrl + '\'' +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.photoId);
        dest.writeString(this.originalUrl);
        dest.writeString(this.thumbnailUrl);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
    }

    protected PhotoData(Parcel in) {
        this.photoId = in.readString();
        this.originalUrl = in.readString();
        this.thumbnailUrl = in.readString();
        this.width = in.readInt();
        this.height = in.readInt();
    }

    public static final Creator<PhotoData> CREATOR = new Creator<PhotoData>() {
        @Override
        public PhotoData createFromParcel(Parcel source) {
            return new PhotoData(source);
        }

        @Override
        public PhotoData[] newArray(int size) {
            return new PhotoData[size];
        }
    };
}
