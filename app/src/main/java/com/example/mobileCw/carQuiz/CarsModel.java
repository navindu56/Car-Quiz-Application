package com.example.mobileCw.carQuiz;

import android.os.Parcel;
import android.os.Parcelable;

public class CarsModel implements Parcelable {
    int id;
    String carimg;
    String carbrand;

    protected CarsModel(Parcel in) {
        id = in.readInt();
        carimg = in.readString();
        carbrand = in.readString();
    }

    public static final Creator<CarsModel> CREATOR = new Creator<CarsModel>() {
        @Override
        public CarsModel createFromParcel(Parcel in) {
            return new CarsModel(in);
        }

        @Override
        public CarsModel[] newArray(int size) {
            return new CarsModel[size];
        }
    };

    public CarsModel() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCarimg() {
        return carimg;
    }

    public void setCarimg(String carimg) {
        this.carimg = carimg;
    }

    public String getCarbrand() {
        return carbrand;
    }

    public void setCarbrand(String carbrand) {
        this.carbrand = carbrand;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int id) {
        dest.writeInt(id);
        dest.writeString(carimg);
        dest.writeString(carbrand);
    }

    @Override
    public String toString() {
        return getCarbrand();
    }
}
