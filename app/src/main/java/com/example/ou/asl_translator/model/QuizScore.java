package com.example.ou.asl_translator.model;

import android.app.Application;

public class QuizScore extends Application {
    private int wordCorrect=0,wordWrong=0;
    private int arrCorrect=0,arrWrong=0;
    private int stcCorrect=0,stcWrong=0;
    private int wordDelay=0,stcDelay=0;
    public void resetAll(){
        wordCorrect=0;
        wordWrong=0;
        arrCorrect=0;
        arrWrong=0;
        stcCorrect=0;
        stcWrong=0;
    }
    public void incWordCorrect (){
        wordCorrect=wordCorrect+1;
    }
    public void incWordWrong (){
        wordWrong=wordWrong+1;
    }
    public void incArrCorrect (){
        arrCorrect=arrCorrect+1;
    }
    public void incArrWrong (){
        arrWrong=arrWrong+1;
    }
    public void incStcCorrect (){
        stcCorrect=stcCorrect+1;
    }
    public void incStcWrong (){
        stcWrong=stcWrong+1;
    }

    public int getWordCorrect() {
        return wordCorrect;
    }

    public int getWordWrong() {
        return wordWrong;
    }

    public int getArrCorrect() {
        return arrCorrect;
    }

    public int getArrWrong() {
        return arrWrong;
    }

    public int getStcCorrect() {
        return stcCorrect;
    }

    public int getStcWrong() {
        return stcWrong;
    }

    public int getWordDelay() {

        return wordDelay;
    }

    public void setWordDelay(int wordDelay) {
        this.wordDelay = wordDelay;
    }

    public int getStcDelay() {
        return stcDelay;
    }

    public void setStcDelay(int stcDelay) {
        this.stcDelay = stcDelay;
    }
}
