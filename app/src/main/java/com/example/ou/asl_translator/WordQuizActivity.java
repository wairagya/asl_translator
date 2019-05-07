package com.example.ou.asl_translator;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.ou.asl_translator.database.DataHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WordQuizActivity extends AppCompatActivity implements View.OnClickListener{
    private Button option1,option2,option3,option4;
    private TextView timer;
    private CountDownTimer countDownTimer;
    private String question, correct;
    private List answers = new ArrayList();
    private int timeValue = 15;
    DataHelper dataHelper;
    Cursor cursor;
    private String word,type;
    private int mCurrentPosition = 0;
    private static final String[] forbidden = new String[] {
            "abstract","break","case","catch","class",
            "continue","double","do","else","false","final","finally",
            "float","for","if","import","interface","long",
            "native","new","package","private","public","return","short",
            "super","switch","this","throw",
            "true","try","void","while"};
    private Button play;
    private List list = new ArrayList();
    private VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_quiz);
        videoView=findViewById(R.id.videoView);
        timer=findViewById(R.id.timer);
        option1=findViewById(R.id.option1);
        option2=findViewById(R.id.option2);
        option3=findViewById(R.id.option3);
        option4=findViewById(R.id.option4);

        dataHelper = new DataHelper(this);
        dataHelper.getWritableDatabase();
        if (dataHelper.getAllWordQuiz().size()==0){
            dataHelper.wordQuiz();
        }
        SQLiteDatabase db=dataHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM KUIS_KATA ORDER BY RANDOM();",null);
        cursor.moveToFirst();
        if (cursor.getCount()>0){
            question= cursor.getString(cursor.getColumnIndex("QUESTION"));
            answers.add(cursor.getString(cursor.getColumnIndex("FALSE1")));
            answers.add(cursor.getString(cursor.getColumnIndex("FALSE2")));
            answers.add(cursor.getString(cursor.getColumnIndex("FALSE3")));
            answers.add(cursor.getString(cursor.getColumnIndex("CORRECT")));
            correct=cursor.getString(cursor.getColumnIndex("CORRECT"));
        }
        Collections.shuffle(answers);
        //textView.setText(question);
        option1.setText(answers.get(0).toString());
        answers.remove(0);
        option2.setText(answers.get(0).toString());
        answers.remove(0);
        option3.setText(answers.get(0).toString());
        answers.remove(0);
        option4.setText(answers.get(0).toString());
        answers.remove(0);
        option1.setOnClickListener(this);
        option2.setOnClickListener(this);
        option3.setOnClickListener(this);
        option4.setOnClickListener(this);

        countDownTimer = new CountDownTimer(16000, 1000) {
            public void onTick(long millisUntilFinished) {
                timer.setText(String.valueOf(timeValue));
                timeValue -= 1;
                if (timeValue == -1) {
                    //todo efek
                }
            }
            public void onFinish() {
                Intent intent = new Intent(WordQuizActivity.this,QuizResultActivity.class);
                intent.putExtra("type","kata");
                intent.putExtra("status","2");
                intent.putExtra("correct",correct);
                startActivity(intent);
                finish();
            }
        }.start();

        if(checkForb(question)){
            question="_"+question;
        }
        initializePlayer(question);
        Log.d("question video", question);
    }

    @Override
    public void onClick(View v) {
        Button clickedOption=(Button) v;
        Intent intent = new Intent(WordQuizActivity.this,QuizResultActivity.class);
        intent.putExtra("type","kata");
        if (clickedOption.getText().equals(correct)){
            intent.putExtra("status","1");
            Log.d("result", "correct");
        }
        else {
            intent.putExtra("status","0");
            Log.d("result", "false");
        }
        intent.putExtra("correct",correct);
        startActivity(intent);
        finish();
    }

    public static boolean checkForb(String targetValue) {
        for (String s: forbidden) {
            if (s.equals(targetValue))
                return true;
        }
        return false;
    }

    private Uri getMedia(String mediaName) {
        return Uri.parse("android.resource://" + getPackageName() +
                "/raw/" + mediaName);
    }

    private void initializePlayer(String word) {
        word=word.replaceAll("\\s","_");
        word=word.replaceAll("\\.","_");
        final String temp=word;
        Uri videoUri = getMedia(word);
        videoView.setVideoURI(videoUri);
        if (mCurrentPosition > 0) {
            videoView.seekTo(mCurrentPosition);
        } else {
            videoView.seekTo(1);
        }
        videoView.start();
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.reset();
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        initializePlayer(temp);
                    }
                }, 500);
            }
        });
    }


    private void releasePlayer() {
        videoView.stopPlayback();
    }

    @Override
    protected void onStop() {
        super.onStop();
        countDownTimer.cancel();
        releasePlayer();
    }
    @Override
    protected void onPause() {
        super.onPause();
        countDownTimer.cancel();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            videoView.pause();
        }
    }

}
