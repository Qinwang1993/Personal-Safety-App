package com.sjsu.sister.model;

import java.io.File;

public class FakeCall {
    String name;
    String filePath;
    int isPlay;

    public FakeCall(){}
    public FakeCall(String name,String filePath, int isPlay){
        this.name = name;
        this.filePath = filePath;
        this.isPlay = isPlay;
    }
    public String getName() {
        return name;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setIsPlay(int isPlay) {
        this.isPlay = isPlay;
    }

    public int getIsPlay() {
        return isPlay;
    }
}
