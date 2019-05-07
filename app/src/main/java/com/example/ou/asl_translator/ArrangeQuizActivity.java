package com.example.ou.asl_translator;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.ou.asl_translator.adapter.ArrangeQuizAdapter;
import com.example.ou.asl_translator.database.DataHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ArrangeQuizActivity extends AppCompatActivity {
    private GridView gridview;
    private TextView asl,eng;
//    private TextView timer; todo timer
    private Button reset,check;
    private int wordCount,count;
//    private CountDownTimer countDownTimer; todo timer
    private String question,correct,temp="";
    private List answers = new ArrayList();
//    private int timeValue = 15;todo timer
    DataHelper dataHelper;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrange_quiz);
        gridview = findViewById(R.id.gridView);
        asl=findViewById(R.id.asl);
        eng=findViewById(R.id.eng);
        reset=findViewById(R.id.reset);
        check=findViewById(R.id.check);
//        timer=findViewById(R.id.timer);todo timer
        dataHelper = new DataHelper(this);
        dataHelper.getWritableDatabase();
        if (dataHelper.getAllStcQuiz().size()==0){
            dataHelper.stcQuiz();
        }
        SQLiteDatabase db=dataHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM KUIS_KALIMAT ORDER BY RANDOM();",null);
        cursor.moveToFirst();
        if (cursor.getCount()>0){
            question= cursor.getString(cursor.getColumnIndex("ENG_STC"));
            correct=cursor.getString(cursor.getColumnIndex("ASL_STC"));
        }
        eng.setText(question);
        String[] temp2 = correct.split(" ");
        answers.addAll(Arrays.asList(temp2));
        Collections.shuffle(answers);
        count = generate(answers);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asl.setText("");
                temp="";
                generate(answers);
                wordCount=0;
                check.setVisibility(View.GONE);
            }
        });
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArrangeQuizActivity.this,QuizResultActivity.class);
                intent.putExtra("type","kalimat1");
                Log.d("answer", asl.getText().toString());
                Log.d("correct", correct);
                if (asl.getText().toString().toLowerCase().equals(correct)){
                    intent.putExtra("status","1");
                    Log.d("status", "1");
                } else {
                    intent.putExtra("status","0");
                    Log.d("status", "0");
                }
                intent.putExtra("correct",correct);
                startActivity(intent);
                finish();
            }
        });
//        countDownTimer = new CountDownTimer(16000, 1000) {
//            public void onTick(long millisUntilFinished) {
//                timer.setText(String.valueOf(timeValue));
//                timeValue -= 1;
//                if (timeValue == -1) {
//                    //todo efek
//                }
//            }
//            public void onFinish() {
//                Intent intent = new Intent(ArrangeQuizActivity.this,QuizResultActivity.class);
//                intent.putExtra("type","kalimat1");
//                intent.putExtra("status","2");
//                intent.putExtra("correct",correct);
//                startActivity(intent);
//                finish();
//            }
//        }.start();todo timer
    }
    public void addWord(String word){
        temp=temp+word.toUpperCase();
        asl.setText(temp);
        //asl.append(word.toUpperCase());
        wordCount=wordCount+1;
        if (wordCount == count){
            check.setVisibility(View.VISIBLE);
        }else {
            temp=temp+" ";
            //asl.append(" ");
        }
    }

    public int generate(List plantsList){
        ArrangeQuizAdapter arrangeQuizAdapter = new ArrangeQuizAdapter(ArrangeQuizActivity.this, plantsList);
        gridview.setAdapter(arrangeQuizAdapter);
        return arrangeQuizAdapter.getCount();
    }

    @Override
    protected void onStop() {
        super.onStop();
//        countDownTimer.cancel();todo timer
    }

    @Override
    protected void onPause() {
//        countDownTimer.cancel();todo timer
        super.onPause();
    }
}
