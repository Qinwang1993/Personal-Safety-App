package com.sjsu.sister.model;

public class FirstAidEntry {

    private String title;
    private int icon;
    private int id;

    public FirstAidEntry(String title, int icon, int id) {
        this.title = title;
        this.icon = icon;
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getIcon() {
        return icon;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
