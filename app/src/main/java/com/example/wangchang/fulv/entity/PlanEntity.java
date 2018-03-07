package com.example.wangchang.fulv.entity;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * 日程页面实体类
 * Created by liying on 2018-3-6.
 */
public class PlanEntity implements Parcelable {

    private String title;
    public PlanEntity(){

    }
    public PlanEntity(Parcel in) {
        title = in.readString();
    }

    public static final Creator<PlanEntity> CREATOR = new Creator<PlanEntity>() {
        @Override
        public PlanEntity createFromParcel(Parcel in) {
            return new PlanEntity(in);
        }

        @Override
        public PlanEntity[] newArray(int size) {
            return new PlanEntity[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
    }
}
