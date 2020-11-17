package com.sjsu.sister.model;

public class FAQs {
    private String title, content;
    private boolean expendable;

    public FAQs(String title, String content){
        this.title = title;
        this.content = content;
        this.expendable = false;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setExpendable(boolean expendable) {
        this.expendable = expendable;
    }

    public boolean isExpendable() {
        return expendable;
    }

    @Override
    public String toString() {
        return "FAQs{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
