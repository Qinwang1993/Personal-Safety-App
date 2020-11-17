package com.sjsu.sister.model;
import android.os.Parcel;
import android.os.Parcelable;

public class Videos implements Parcelable {
    private String title;
    private String url;
    private String thumb;
    private String description;
    private String instruction;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public void setDescription(String description){ this.description = description;}

    public String getDescription() { return description; }

    public void setInstruction(String description){ this.instruction = instruction;}

    public String getInstruction() { return instruction; }

    public Videos(String title, String thumb, String url, String description, String instruction) {
        this.title = title;
        this.url = url;
        this.thumb = thumb;
        this.description = description;
        this.instruction = instruction;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected Videos(Parcel in) {
        title = in.readString();
        url = in.readString();
        thumb = in.readString();
        description = in.readString();
        instruction = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(title);
        dest.writeString(url);
        dest.writeString(thumb);
        dest.writeString(description);
        dest.writeString(instruction);
    }

    public static final Creator<Videos> CREATOR = new Creator<Videos>() {
        @Override
        public Videos createFromParcel(Parcel in) {
            return new Videos(in);
        }

        @Override
        public Videos[] newArray(int size) {
            return new Videos[size];
        }
    };
}

