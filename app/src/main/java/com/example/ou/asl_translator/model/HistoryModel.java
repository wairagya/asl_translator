package com.example.ou.asl_translator.model;

public class HistoryModel {
    String filename;
    String intentDescription;
    int uid;
    public HistoryModel() {

    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public HistoryModel(String filename, String intentDescription, int uid) {
        this.filename = filename;
        this.intentDescription = intentDescription;
        this.uid=uid;
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
