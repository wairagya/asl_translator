package com.example.ou.asl_translator.model;

public class HistoryModel {
    String filename;
    String intentDescription;
    int uid;
    int progress;

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public HistoryModel() {

    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public HistoryModel(String filename, String intentDescription, int uid,int progress) {
        this.filename = filename;
        this.intentDescription = intentDescription;
        this.uid=uid;
        this.progress=progress;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getIntentDescription() {
        return intentDescription;
    }

    public void setIntentDescription(String intentDescription) {
        this.intentDescription = intentDescription;
    }
}
