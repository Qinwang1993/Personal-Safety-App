package com.sjsu.sister.model;

import android.os.Parcel;
import android.os.Parcelable;

public class FirstAids  {

    private String title;
    private String url;
    private String subtitle;
    private String content1;
    private String content2;
    private String content3;

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public void setContent1(String content1) {
        this.content1 = content1;
    }

    public String getContent1() {
        return content1;
    }

    public void setContent2(String content2) {
        this.content2 = content2;
    }

    public String getContent2() {
        return content2;
    }

    public void setContent3(String content3) {
        this.content3 = content3;
    }

    public String getContent3() {
        return content3;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getUrl() { return url; }

    public void setUrl(String url) { this.url = url; }

    public FirstAids(String title, String url, String subtitle,String content1, String content2, String content3) {
        this.title = title;
        this.url = url;
        this.subtitle = subtitle;
        this.content1 = content1;
        this.content2 = content2;
        this.content3 = content3;
    }

}
