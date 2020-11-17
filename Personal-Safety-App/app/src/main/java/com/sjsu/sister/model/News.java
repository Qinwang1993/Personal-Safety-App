package com.sjsu.sister.model;

import android.os.Parcel;
import android.os.Parcelable;

public class News implements Parcelable {
    private String title;
    private String url;
    private String description;
    private String instruction;
    private int id;

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getUrl() { return url; }

    public void setUrl(String url) { this.url = url; }

    public void setDescription(String description){ this.description = description;}

    public String getDescription() { return description; }

    public void setInstruction(String description){ this.instruction = instruction;}

    public String getInstruction() { return instruction; }

    public void setId(int type) { this.id = type; }

    public int getId() { return id; }

    public News(String title, String url, String description, String instruction, int id) {
        this.title = title;
        this.url = url;
        this.description = description;
        this.instruction = instruction;
        this.id = id;
    }

        @Override
        public int describeContents() {
        return 0;
    }

    protected News(Parcel in) {
        title = in.readString();
        url = in.readString();
        description = in.readString();
        instruction = in.readString();
        id = in.readInt();
    }

        @Override
        public void writeToParcel(Parcel dest, int i) {
        dest.writeString(title);
        dest.writeString(url);
        dest.writeString(description);
        dest.writeString(instruction);
        dest.writeInt(id);
    }

        public static final Parcelable.Creator<News> CREATOR = new Parcelable.Creator<News>() {
            @Override
            public News createFromParcel(Parcel in) {
                return new News(in);
            }

            @Override
            public News[] newArray(int size) {
                return new News[size];
            }
        };
    }

