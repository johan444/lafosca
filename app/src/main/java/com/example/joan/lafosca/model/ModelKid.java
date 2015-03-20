package com.example.joan.lafosca.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Joan on 17/3/15.
 */
public class ModelKid implements Parcelable{

    @Expose
    private String name;
    @Expose
    private Integer age;

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public ModelKid() {}

    public ModelKid(String name, int age, String url) {
        this.name = name;
        this.age = age;
        this.url = url;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The age
     */
    public Integer getAge() {
        return age;
    }

    /**
     *
     * @param age
     * The age
     */
    public void setAge(Integer age) {
        this.age = age;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeValue(this.age);
    }

    private ModelKid(Parcel in) {
        this.name = in.readString();
        this.age = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<ModelKid> CREATOR = new Creator<ModelKid>() {
        public ModelKid createFromParcel(Parcel source) {
            return new ModelKid(source);
        }
        public ModelKid[] newArray(int size) {
            return new ModelKid[size];
        }
    };
}
