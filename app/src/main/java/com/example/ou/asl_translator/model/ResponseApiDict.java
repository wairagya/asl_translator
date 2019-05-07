package com.example.ou.asl_translator.model;

import java.util.List;

public class ResponseApiDict {
    String kode;
    List<ChildRow> result;

    public ResponseApiDict(){

    }
    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public List<ChildRow> getResult() {
        return result;
    }

    public void setResult(List<ChildRow> result) {
        this.result = result;
    }
}
