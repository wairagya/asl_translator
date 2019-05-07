package com.example.ou.asl_translator;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ou.asl_translator.database.DataHelper;

public class StcQuizActivity extends AppCompatActivity {
    private TextView eng,asl;
//    private TextView timer; todo timer
//    private CountDownTimer countDownTimer; todo timer
    private String question,correct;
//    private int timeValue = 15; todo timer
    DataHelper dataHelper;
    Cursor cursor;
    private Button check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stc_quiz);
//        timer=findViewById(R.id.timer);todo timer
        asl=findViewById(R.id.asl);
        eng=findViewById(R.id.eng);
        check=findViewById(R.id.check);
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
        asl.setText(correct);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StcQuizActivity.this,QuizResultActivity.class);
                intent.putExtra("type","kalimat2");
                intent.putExtra("status","2");
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
//                Intent intent = new Intent(StcQuizActivity.this,QuizResultActivity.class);
//                intent.putExtra("type","kalimat2");
//                intent.putExtra("status","2");
//                intent.putExtra("correct",correct);
//                startActivity(intent);
//                finish();
//            }
//        }.start();todo timer
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
