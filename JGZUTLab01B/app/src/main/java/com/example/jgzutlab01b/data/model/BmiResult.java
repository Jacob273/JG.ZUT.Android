package com.example.jgzutlab01b.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DecimalFormat;

public class BmiResult implements Parcelable {


    public double Value;
    public String Message;

    //ctor
    public BmiResult(Double value, String message) {
        Value = value;
        Message = message;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public BmiResult createFromParcel(Parcel in) {
            return new BmiResult(in);
        }

        public BmiResult[] newArray(int size) {
            return new BmiResult[size];
        }
    };

    public BmiResult(Parcel in) {
        this.Value = in.readDouble();
        this.Message = in.readString();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.Value);
        dest.writeString(this.Message);
    }

    public String getValueFormatted(){
        return new DecimalFormat("#.0#").format(this.Value);
    }

    public String getFullBmiDescription(){
        return "BMI: [" + getValueFormatted() + "], " + this.Message;
    }
}
