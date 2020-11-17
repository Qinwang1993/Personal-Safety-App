package com.sjsu.sister.model;

public class AboutUsElement {

    private String title;
    private Integer iconDrawable;

    public AboutUsElement() {

    }

    public AboutUsElement(String title, Integer iconDrawable) {
        this.title = title;
        this.iconDrawable = iconDrawable;
    }


    public String getTitle() {
        return title;
    }


    public AboutUsElement setTitle(String title) {
        this.title = title;
        return this;
    }

    public Integer getIconDrawable() {
        return iconDrawable;
    }

    public AboutUsElement setIconDrawable(Integer iconDrawable) {
        this.iconDrawable = iconDrawable;
        return this;
    }
}
