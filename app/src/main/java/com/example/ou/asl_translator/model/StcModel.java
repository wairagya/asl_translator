package com.example.ou.asl_translator.model;

public class StcModel {
    private String engStc, aslStc;
    private int kode;

    public StcModel(String engStc, String aslStc) {
        this.engStc = engStc;
        this.aslStc = aslStc;
    }

    public StcModel() {
        kode=0;
        engStc="";
        aslStc="";
    }

    public int getKode() {
        return kode;
    }

    public void setKode(int kode) {
        this.kode = kode;
    }

    public String getEngStc() {
        return engStc;
    }

    public void setEngStc(String engStc) {
        this.engStc = engStc;
    }

    public String getAslStc() {
        return aslStc;
    }

    public void setAslStc(String aslStc) {
        this.aslStc = aslStc;
    }
}
