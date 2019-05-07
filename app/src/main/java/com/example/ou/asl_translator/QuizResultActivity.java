package com.example.ou.asl_translator;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.ou.asl_translator.model.QuizScore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuizResultActivity extends AppCompatActivity {
    private TextView text,score;
    private LinearLayout correctImg, wrongImg, timeUpImg;
    private Button menus, replay;
    private String type, status, correct;
    private VideoView videoView;
    private String word;
    private int mCurrentPosition = 0,wordDelay;
    private static final String[] forbidden = new String[] {
            "abstract","break","case","catch","class",
            "continue","double","do","else","false","final","finally",
            "float","for","if","import","interface","long",
            "native","new","package","private","public","return","short",
            "super","switch","this","throw",
            "true","try","void","while"};
    private Button play;
    private List list = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);
        correctImg=findViewById(R.id.correct);
        wrongImg=findViewById(R.id.wrong);
        timeUpImg=findViewById(R.id.timeup);
        text=findViewById(R.id.word);
        score=findViewById(R.id.score);
        videoView=findViewById(R.id.videoView);
        menus=findViewById(R.id.menus);
        replay=findViewById(R.id.replay);
        play=findViewById(R.id.playButton);
        wordDelay=((QuizScore) this.getApplication()).getWordDelay()*1000;
        if (getIntent()!=null){
            type=getIntent().getStringExtra("type");
            if (type.equals("kata")){
                status=getIntent().getStringExtra("status");
                if (status.equals("1")){
                    ((QuizScore) this.getApplication()).incWordCorrect();
                    correctImg.setVisibility(View.VISIBLE);
                } else if (status.equals("0")){
                    ((QuizScore) this.getApplication()).incWordWrong();
                    wrongImg.setVisibility(View.VISIBLE);
                } else if (status.equals("2")){
                    ((QuizScore) this.getApplication()).incWordWrong();
                    timeUpImg.setVisibility(View.VISIBLE);
                }
                int scoreCorrect=((QuizScore) this.getApplication()).getWordCorrect();
                int scoreWrong=((QuizScore) this.getApplication()).getWordWrong();
                int totalScore=scoreCorrect+scoreWrong;
                String textScore=String.valueOf(scoreCorrect)+" of "+String.valueOf(totalScore);
                score.setText(textScore);
                score.setVisibility(View.VISIBLE);
                correct=getIntent().getStringExtra("correct");
                text.setText(correct);
                if(checkForb(correct)){
                    correct="_"+correct;
                }
                initializePlayer(correct);
                replay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(QuizResultActivity.this,WordQuizActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                play.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        releasePlayer();
                        initializePlayer(correct);
                    }
                });
            }else if (type.equals("kalimat1")){
                status=getIntent().getStringExtra("status");
                if (status.equals("1")){
                    ((QuizScore) this.getApplication()).incArrCorrect();
                    correctImg.setVisibility(View.VISIBLE);
                } else if (status.equals("0")){
                    ((QuizScore) this.getApplication()).incArrWrong();
                    wrongImg.setVisibility(View.VISIBLE);
                } else if (status.equals("2")){
                    timeUpImg.setVisibility(View.VISIBLE);
                }
                int scoreCorrect=((QuizScore) this.getApplication()).getArrCorrect();
                int scoreWrong=((QuizScore) this.getApplication()).getArrWrong();
                int totalScore=scoreCorrect+scoreWrong;
                String textScore=String.valueOf(scoreCorrect)+" of "+String.valueOf(totalScore);
                score.setText(textScore);
                score.setVisibility(View.VISIBLE);
                correct=getIntent().getStringExtra("correct");
                //todo video player

                String[] stc=correct.split(" ");
                list.addAll(Arrays.asList(stc));
                Log.d("List :", list.toString());
                initializePlayerStc(list);
                play.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        correct=correct.toLowerCase();
                        String[] stc=correct.split(" ");
                        list.addAll(Arrays.asList(stc));
                        initializePlayerStc(list);
                    }
                });

                text.setText(correct);
                replay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(QuizResultActivity.this,ArrangeQuizActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }else if (type.equals("kalimat2")){
                status=getIntent().getStringExtra("status");
                //timeUpImg.setVisibility(View.VISIBLE);
                correct=getIntent().getStringExtra("correct");
                String[] stc=correct.split(" ");
                list.addAll(Arrays.asList(stc));
                Log.d("List :", list.toString());
                initializePlayerStc(list);
                play.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        correct=correct.toLowerCase();
                        String[] stc=correct.split(" ");
                        list.addAll(Arrays.asList(stc));
                        initializePlayerStc(list);
                    }
                });

                text.setText(correct);
                replay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(QuizResultActivity.this,StcQuizActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        }
        menus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizResultActivity.this,QuizMenuActivity.class);
                startActivity(intent);
                finish();
            }
        });

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
            }
        });
    }

    private void initializePlayerStc(final List inputList) {
        if(!(inputList.isEmpty())){
            String temp = inputList.get(0).toString();
            if(checkForb(temp)){
                temp="_"+temp;
            }
            Uri videoUri = getMedia(temp);
            Log.d("Loop Word :", temp);
            videoView.setVideoURI(videoUri);
            if (mCurrentPosition > 0) {
                videoView.seekTo(mCurrentPosition);
            } else {
                videoView.seekTo(1);
            }

            inputList.remove(0);
            videoView.start();
            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer.reset();
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            initializePlayerStc(inputList);
                        }
                    }, wordDelay);

                }
            });
        }
    }

    private void releasePlayer() {
        videoView.stopPlayback();
    }

    @Override
    protected void onStop() {
        super.onStop();

        releasePlayer();
    }
    @Override
    protected void onPause() {
        super.onPause();

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            videoView.pause();
        }
    }

}
