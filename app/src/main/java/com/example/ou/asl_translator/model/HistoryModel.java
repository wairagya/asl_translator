package com.example.ou.asl_translator.model;

public class HistoryModel {
    String filename;
    String intentDescription;
    public HistoryModel() {

    }

    public HistoryModel(String filename, String intentDescription) {
        this.filename = filename;
        this.intentDescription = intentDescription;
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
