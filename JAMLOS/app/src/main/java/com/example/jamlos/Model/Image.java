package com.example.jamlos.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Image implements Parcelable {
    private String Image1;
    private String Image2;
    private String Image3;

    public Image() {
    }

    public Image(String image1, String image2, String image3) {
        Image1 = image1;
        Image2 = image2;
        Image3 = image3;
    }

    protected Image(Parcel in) {
        Image1 = in.readString();
        Image2 = in.readString();
        Image3 = in.readString();
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    public String getImage1() {
        return Image1;
    }

    public void setImage1(String image1) {
        Image1 = image1;
    }

    public String getImage2() {
        return Image2;
    }

    public void setImage2(String image2) {
        Image2 = image2;
    }

    public String getImage3() {
        return Image3;
    }

    public void setImage3(String image3) {
        Image3 = image3;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Image1);
        parcel.writeString(Image2);
        parcel.writeString(Image3);
    }

}
