package com.example.ou.asl_translator.model;

public class WordModel {
    private String question,false1,false2,false3,correct;
    private int kode;

    public WordModel(String question, String false1, String false2, String false3, String correct) {
        this.question=question;
        this.false1=false1;
        this.false2=false2;
        this.false3=false3;
        this.correct=correct;
    }

    public WordModel() {
        kode=0;
        question="";
        false1="";
        false2="";
        false3="";
        correct="";
    }

    public int getKode() {
        return kode;
    }

    public void setKode(int kode) {
        this.kode = kode;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getFalse1() {
        return false1;
    }

    public void setFalse1(String false1) {
        this.false1 = false1;
    }

    public String getFalse2() {
        return false2;
    }

    public void setFalse2(String false2) {
        this.false2 = false2;
    }

    public String getFalse3() {
        return false3;
    }

    public void setFalse3(String false3) {
        this.false3 = false3;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }
}
