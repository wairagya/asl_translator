package com.example.ou.asl_translator.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.ou.asl_translator.model.HistoryModel;
import com.example.ou.asl_translator.model.StcModel;
import com.example.ou.asl_translator.model.WordModel;

import java.util.ArrayList;
import java.util.List;

public class DataHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "data.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_KUIS_KATA="KUIS_KATA";
    private static final String UID="UID";
    private static final String QUESTION="QUESTION";
    private static final String FALSE1="FALSE1";
    private static final String FALSE2="FALSE2";
    private static final String FALSE3="FALSE3";
    private static final String CORRECT="CORRECT";
    private static final String CREATE_TABLE_KUIS_KATA="CREATE TABLE " + TABLE_KUIS_KATA + " ( " + UID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + QUESTION + " VARCHAR(255), " + FALSE1 + " VARCHAR(255), " + FALSE2 + " VARCHAR(255), " + FALSE3 + " VARCHAR(255), " + CORRECT + " VARCHAR(255));";
    private static final String DROP_TABLE_KUIS_KATA="DROP TABLE IF EXISTS " + TABLE_KUIS_KATA;

    private static final String TABLE_KUIS_KALIMAT="KUIS_KALIMAT";
    private static final String UID2="UID";
    private static final String ENG_STC="ENG_STC";
    private static final String ASL_STC="ASL_STC";
    private static final String CREATE_TABLE_KUIS_KALIMAT="CREATE TABLE " + TABLE_KUIS_KALIMAT + " ( " + UID2 + " INTEGER PRIMARY KEY AUTOINCREMENT , " + ENG_STC + " VARCHAR(255), " + ASL_STC + " VARCHAR(255));";
    private static final String DROP_TABLE_KUIS_KALIMAT="DROP TABLE IF EXISTS " + TABLE_KUIS_KALIMAT;

    private static final String TABLE_DELAY ="DELAY";
    private static final String UID3="UID";
    private static final String WORD_DELAY="WORD_DELAY";
    private static final String STC_DELAY="STC_DELAY";
    private static final String CREATE_TABLE_DELAY="CREATE TABLE "+ TABLE_DELAY +" ( "+UID3+" INTEGER PRIMARY KEY AUTOINCREMENT , "+WORD_DELAY+" INTEGER, "+STC_DELAY+" INTEGER);";
    private static final String DROP_TABLE_DELAY="DROP TABLE IF EXISTS "+ TABLE_DELAY;

    private static final String TABLE_HISTORY ="HISTORY";
    private static final String UID4="UID";
    private static final String FILENAME="FILENAME";
    private static final String INTENT_DESC ="INTENT_DESC";
    private static final String LIST1 ="LIST1";
    private static final String LIST2 ="LIST2";
    private static final String INSTC1 ="INSTC1";
    private static final String INSTC2 ="INSTC2";
    private static final String PROGRESS ="PROGRESS";
    private static final String CREATE_TABLE_HISTORY="CREATE TABLE "+ TABLE_HISTORY +" ( "+UID4+" INTEGER PRIMARY KEY AUTOINCREMENT , "+FILENAME+" TEXT, "+ INTENT_DESC +" TEXT, "+ LIST1 +" TEXT, "+ LIST2 +" TEXT, "+ INSTC1 +" TEXT, "+ INSTC2 +" TEXT, "+ PROGRESS +" INTEGER);";
    private static final String DROP_TABLE_HISTORY="DROP TABLE IF EXISTS "+ TABLE_HISTORY;

    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_KUIS_KATA);
        db.execSQL(CREATE_TABLE_KUIS_KALIMAT);
        db.execSQL(CREATE_TABLE_DELAY);
        db.execSQL(CREATE_TABLE_HISTORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_KUIS_KATA);
        db.execSQL(DROP_TABLE_KUIS_KALIMAT);
        db.execSQL(DROP_TABLE_DELAY);
        db.execSQL(DROP_TABLE_HISTORY);
        onCreate(db);
    }
    public List<HistoryModel> getHistory(){
        List<HistoryModel> historyModels = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        String coloumn[]={UID,FILENAME,INTENT_DESC,PROGRESS};
        try {
            Cursor cursor = db.query(TABLE_HISTORY,coloumn,null,null,null,null,null);
            while (cursor.moveToNext()){
                HistoryModel historyModel = new HistoryModel();
                historyModel.setUid(cursor.getInt(0));
                historyModel.setFilename(cursor.getString(1));
                historyModel.setIntentDescription(cursor.getString(2));
                historyModel.setProgress(cursor.getInt(3));
                historyModels.add(historyModel);
            }
            cursor.close();
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
        return historyModels;
    }

    public void addHistory(String filename,String intent_desc){
        Log.d("Adding History", "Filename: "+filename+", Intent Desc :"+intent_desc);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM " + TABLE_HISTORY+" WHERE INTENT_DESC='"+intent_desc+"';",null);
        int count = cursor.getCount();
        if (count==0){
            db.beginTransaction();
            try {
                ContentValues values = new ContentValues();
                values.put(FILENAME, filename);
                values.put(INTENT_DESC, intent_desc);
                db.insert(TABLE_HISTORY, null, values);
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
                db.close();
            }
        }
    }

    public void updateHistory(String intentDesc,String list1, String list2, String inStc1, String inStc2,int progress){
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(LIST1, list1);
            values.put(LIST2, list2);
            values.put(INSTC1, inStc1);
            values.put(INSTC2, inStc2);
            values.put(PROGRESS,progress);
            db.update(TABLE_HISTORY, values, "INTENT_DESC='"+intentDesc+"'",null);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }


    public void clearHistory(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_HISTORY);
    }

    public List<String> importHistoryData(int uid){
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        String coloumn[]={INTENT_DESC,LIST1,LIST2,INSTC1,INSTC2};
        List data = new ArrayList();
        try {
            Cursor cursor = db.query(TABLE_HISTORY,coloumn,"UID='"+ uid +"'",null,null,null,null);
            while (cursor.moveToNext()){
                data.add(cursor.getString(1));
                data.add(cursor.getString(2));
                data.add(cursor.getString(3));
                data.add(cursor.getString(4));
            }
            cursor.close();
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
        return data;
    }

    public void initDelay(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(WORD_DELAY, 0);
            values.put(STC_DELAY, 0);
            db.insert(TABLE_DELAY, null, values);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public void updateDelay(int wordDelay, int stcDelay){
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(WORD_DELAY, wordDelay);
            values.put(STC_DELAY, stcDelay);
            db.update(TABLE_DELAY, values, "UID=1",null);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public int getWordDelay(){
        int temp=-1;
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        String coloumn[]={UID3,WORD_DELAY,STC_DELAY};
        Cursor cursor = db.query(TABLE_DELAY,coloumn,null,null,null,null,null);
        while (cursor.moveToNext()){
            temp=cursor.getInt(1);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        cursor.close();
        db.close();
        return temp;
    }
    public int getStcDelay(){
        int temp=-1;
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        String coloumn[]={UID3,WORD_DELAY,STC_DELAY};
        Cursor cursor = db.query(TABLE_DELAY,coloumn,null,null,null,null,null);
        while (cursor.moveToNext()){
            temp=cursor.getInt(2);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        cursor.close();
        db.close();
        return temp;
    }

    public void wordQuiz(){
        ArrayList<WordModel> wordModels = new ArrayList<>();
        wordModels.add(new WordModel("drive","run","follow","sing","drive"));
        wordModels.add(new WordModel("run","follow","sing","drive","run"));
        wordModels.add(new WordModel("truck","car","bike","swing","truck"));
        wordModels.add(new WordModel("car","truck","bike","swing","car"));
        wordModels.add(new WordModel("bike","car","truck","swing","bike"));
        wordModels.add(new WordModel("swing","car","bike","drive","swing"));
        wordModels.add(new WordModel("follow","car","bike","swing","follow"));
        wordModels.add(new WordModel("sing","car","truck","drive","sing"));
        wordModels.add(new WordModel("swing","car","bike","sing","swing"));
        wordModels.add(new WordModel("dog","cat","mouse","tiger","dog"));
        this.addWord(wordModels);
    }
    private void addWord(ArrayList<WordModel> wordModels){
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (WordModel data : wordModels) {
                values.put(QUESTION, data.getQuestion());
                values.put(FALSE1, data.getFalse1());
                values.put(FALSE2, data.getFalse2());
                values.put(FALSE3, data.getFalse3());
                values.put(CORRECT, data.getCorrect());
                db.insert(TABLE_KUIS_KATA, null, values);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public List<WordModel> getAllWordQuiz(){
        List<WordModel> wordModels = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        String coloumn[]={UID,QUESTION,FALSE1,FALSE2,FALSE3,CORRECT};
        Cursor cursor = db.query(TABLE_KUIS_KATA,coloumn,null,null,null,null,null);
        while (cursor.moveToNext()){
            WordModel wordModel = new WordModel();
            wordModel.setKode(cursor.getInt(0));
            wordModel.setQuestion(cursor.getString(1));
            wordModel.setFalse1(cursor.getString(2));
            wordModel.setFalse2(cursor.getString(3));
            wordModel.setFalse3(cursor.getString(4));
            wordModel.setCorrect(cursor.getString(5));
            wordModels.add(wordModel);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        cursor.close();
        db.close();
        return wordModels;
    }

    public void stcQuiz(){
        ArrayList<StcModel> stcModels = new ArrayList<>();
        stcModels.add(new StcModel("I follow the truck","truck follow i"));
        stcModels.add(new StcModel("she love driving car","car drive love she"));
        stcModels.add(new StcModel("he not pouring the water","water pour not he"));
        stcModels.add(new StcModel("the brown car is fast","car brown fast"));
        stcModels.add(new StcModel("the book that borrowed from school was good","book school borrow good"));
        stcModels.add(new StcModel("vegetarians eats salad","vegetarian salad eat"));
        stcModels.add(new StcModel("why drive car to the city?","car city drive why"));
        stcModels.add(new StcModel("what is your name?","Name what"));
        stcModels.add(new StcModel("the car looks not good","car look good not"));
        stcModels.add(new StcModel("the phone never turned off","phone turn off not"));
        this.addStc(stcModels);
    }
    private void addStc(ArrayList<StcModel> stcModels){
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (StcModel data : stcModels) {
                values.put(ENG_STC, data.getEngStc());
                values.put(ASL_STC, data.getAslStc());
                db.insert(TABLE_KUIS_KALIMAT, null, values);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public List<StcModel> getAllStcQuiz(){
        List<StcModel> stcModels = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        String coloumn[]={UID,ENG_STC,ASL_STC};
        Cursor cursor = db.query(TABLE_KUIS_KALIMAT,coloumn,null,null,null,null,null);
        while (cursor.moveToNext()){
            StcModel stcModel = new StcModel();
            stcModel.setKode(cursor.getInt(0));
            stcModel.setEngStc(cursor.getString(1));
            stcModel.setAslStc(cursor.getString(2));
            stcModels.add(stcModel);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        cursor.close();
        db.close();
        return stcModels;
    }
}
