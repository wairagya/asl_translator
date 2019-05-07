package com.example.ou.asl_translator;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DictDetailActivity extends AppCompatActivity {
    private TextView textView,back;
    private VideoView videoView;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dict_detail);
        Intent parsedData = getIntent();
        word = parsedData.getStringExtra("word");
        Log.d("word", word);
        type = parsedData.getStringExtra("type");
        textView=findViewById(R.id.inputText);
        videoView=findViewById(R.id.videoView);
        play=findViewById(R.id.playButton);
        if (type.equals("word")){
            if(checkForb(word)){
                word="_"+word;
            }
            textView.setText(word);
            initializePlayer(word);
            play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    releasePlayer();
                    initializePlayer(word);
                }
            });
        }
        else if (type.equals("stc")){
            Log.d("Stc :", word);
            list.clear();
            textView.setText(word);
            word=word.toLowerCase();
            word=word.replace(".","");
            String[] stc=word.split(" ");
            list.addAll(Arrays.asList(stc));
            Log.d("List :", list.toString());
            initializePlayerStc(list);
            play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    word=word.toLowerCase();
                    String[] stc=word.split(" ");
                    list.addAll(Arrays.asList(stc));
                    initializePlayerStc(list);
                }
            });
        }
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            Uri videoUri = getMedia(inputList.get(0).toString());
            Log.d("Loop Word :", inputList.get(0).toString());
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
                    initializePlayerStc(inputList);
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
