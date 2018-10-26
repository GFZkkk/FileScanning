package com.example.pc1.filescanning.Bean;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.pc1.filescanning.Util.OpenFileUtil;

import java.io.Serializable;
import java.util.Objects;

public class Filepo implements Parcelable {
    private String type;
    private String name;
    private String url;
    private String size;
    private Drawable image;
    private Intent intent;
//    private String createtime;
    private String modifytime;
    private String packageName;

    public Filepo(String name, String type, String url, String size, String modifytime) {
        this.type = type;
        this.name = name;
        this.url = url;
        this.size = size;
        this.modifytime = modifytime;
    }

    public Filepo( String name, String type,String url, String size, Drawable image, String modifytime) {
        this.type = type;
        this.name = name;
        this.url = url;
        this.size = size;
        this.image = image;
        this.modifytime = modifytime;
        this.intent = OpenFileUtil.openFile(url);

    }

    public Filepo() {
    }

    protected Filepo(Parcel in) {
        type = in.readString();
        name = in.readString();
        url = in.readString();
        size = in.readString();
        intent = in.readParcelable(Intent.class.getClassLoader());
        modifytime = in.readString();
    }

    public static final Creator<Filepo> CREATOR = new Creator<Filepo>() {
        @Override
        public Filepo createFromParcel(Parcel in) {
            return new Filepo(in);
        }

        @Override
        public Filepo[] newArray(int size) {
            return new Filepo[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getModifytime() {
        return modifytime;
    }

    public void setModifytime(String modifytime) {
        this.modifytime = modifytime;
    }

    public Drawable getImage() {
        return image;
    }
    public void setImage(Drawable image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Filepo filepo = (Filepo) o;
        return Objects.equals(name, filepo.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeString(name);
        dest.writeString(url);
        dest.writeString(size);
        dest.writeParcelable(intent, flags);
        dest.writeString(modifytime);
    }
}
