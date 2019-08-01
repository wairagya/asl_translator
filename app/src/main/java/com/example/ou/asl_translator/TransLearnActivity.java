package com.example.ou.asl_translator;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.ou.asl_translator.api.ApiRequest;
import com.example.ou.asl_translator.api.RetroClient;
import com.example.ou.asl_translator.model.QuizScore;
import com.example.ou.asl_translator.model.ResponseApiLearnModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransLearnActivity extends AppCompatActivity {
    private EditText inputText;
    private VideoView mVideoView;
    private ProgressDialog dialog;
    private int mCurrentPosition = 0;
    private Button transButton;
    private ImageView sideMenu;
    private TextView histText,back;
    private LinearLayout importStatus;
    private static final String PLAYBACK_TIME = "play_time";
    private static final String[] forbidden = new String[] {
            "abstract","break","case","catch","class",
            "continue","double","do","else","false","final","finally",
            "float","for","if","import","interface","long",
            "native","new","package","private","public","return","short",
            "super","switch","this","throw",
            "true","try","void","while"};
    private List list = new ArrayList();
    private List listStc = new ArrayList();
    private List listHist = new ArrayList();
    private StringBuilder tempStc = new StringBuilder();
    private String inputStc,inputStc2;
    private int tempInt,wordDelay,stcDelay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_learn);
        back=findViewById(R.id.back);
        histText=findViewById(R.id.histText);
        sideMenu=findViewById(R.id.sideMenu);
        sideMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransLearnActivity.this, TransActivity.class);
                startActivity(intent);
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mVideoView = findViewById(R.id.videoView);
        mVideoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mVideoView.isPlaying()) {
                    mVideoView.pause();
                } else {
                    mVideoView.start();
                }
            }
        });
        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(PLAYBACK_TIME);
        }
        inputText = findViewById(R.id.inputText);
        transButton = findViewById(R.id.transButton);
        wordDelay=((QuizScore) this.getApplication()).getWordDelay()*1000;
        stcDelay=((QuizScore) this.getApplication()).getStcDelay()*1000;

        transButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputStc2=inputText.getText().toString();
                inputStc =inputText.getText().toString();
                inputStc=inputStc.replaceAll(",",".");
                inputStc=inputStc.replaceFirst("\\s++$", "");
                if (!inputStc.substring(inputStc.length()-1).equals(".")) {
                    inputStc=inputStc+".";
                    Log.w("Stts", "True .: ");
                }
                String[] inStc=inputStc.split("\\.");
                listStc.clear();
                listHist.clear();
                listStc.addAll(Arrays.asList(inStc));
                tempInt=0;
                ApiRequest Api = RetroClient.getRequestService();
                Call<ResponseApiLearnModel> checker2 = Api.checker2(inputStc);
                checker2.enqueue(new Callback<ResponseApiLearnModel>() {
                    @Override
                    public void onResponse(Call<ResponseApiLearnModel> call, Response<ResponseApiLearnModel> response) {
                        ResponseApiLearnModel res = response.body();
                        assert res != null;
                        String word=res.getPhares();
                        String hist=res.getHist();
                        hist=hist.substring(7);
                        String[] histTemp=hist.split("<marks>");
                        listHist.addAll(Arrays.asList(histTemp));

                        word = word.substring(0, word.length() - 1);
                        list.clear();
                        String[] stc=word.split("\\.");
                        for (int i = 0; i<stc.length; i++){
                            if (stc[i].trim().length()>0){
                                list.add("?");
                                String sentence = stc[i];
                                String[] tempArray = sentence.split(" ");
                                for (int k = 0; k<tempArray.length; k++) {
                                    String temp=tempArray[k];
                                    if (temp.contains("-")){
                                        String[] tempArray2 = temp.split("-");
                                        for(int j=0;j<tempArray2.length;j++){
                                            if (tempArray2[j].length()!=0){
                                                list.add(tempArray2[j]);
                                            }
                                        }
                                    }else{
                                        if (tempArray[k].length()!=0){
                                            list.add(tempArray[k]);
                                        }
                                    }
                                }
                                list.add("!");
                            }
                            else {
                                listStc.remove(i);
                                tempInt=tempInt+1;
                            }
                        }
                        Log.w("Daftar Word", list.toString());
                        Log.w("Daftar Stc", listStc.toString());
                        Log.w("Daftar Hist", listHist.toString());
                        initializePlayer(list,listStc,listHist);
                    }

                    @Override
                    public void onFailure(Call<ResponseApiLearnModel> call, Throwable t) {
                        Toast.makeText(TransLearnActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
    private Uri getMedia(String mediaName) {
        if(checkForb(mediaName)){
            mediaName="_"+mediaName;
        }
        return Uri.parse("android.resource://" + getPackageName() +
                "/raw/" + mediaName);
    }

    public static boolean checkForb(String targetValue) {
        for (String s: forbidden) {
            if (s.equals(targetValue))
                return true;
        }
        return false;
    }

    private void initializePlayer(final List inputList, final List inputListStc, final List histList) {
        if(!(inputList.isEmpty())){
            switch (inputList.get(0).toString()) {
                case "!":
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            inputList.remove(0);
                            histList.remove(0);
                            initializePlayer(inputList,inputListStc,histList);
                        }
                    }, stcDelay);
                    break;
                case ",":
                    inputList.remove(0);
                    initializePlayer(inputList,inputListStc,histList);
                    break;
                case "?":
                    inputList.remove(0);
                    tempStc.setLength(0);
                    if (!(inputList.isEmpty())) {
                        int n = 0;
                        do {
                            tempStc.append(inputList.get(n).toString()).append(" ");
                            n = n + 1;
                        } while (!(inputList.get(n).equals("!")));
                        //textView.setText(tempStc);
                        initializePlayer(inputList,inputListStc,histList);
                    }

                    String firstStcx,secondStcx,thirdStcx = "";
                    secondStcx=inputListStc.get(0).toString();
                    if ((inputListStc.size()>0)) {
                        int n = 0;
                        StringBuilder tempWordx = new StringBuilder();
                        do {
                            tempWordx.append(inputListStc.get(n).toString()).append(".");
                            n = n + 1;
                            //if (inputListStc.size()>n) tempWordx.append(".");
                        } while ((inputListStc.size()>n));
                        thirdStcx= String.valueOf(tempWordx);
                        Log.w("3rd", thirdStcx);
                    }

                    firstStcx=inputStc.replaceFirst(thirdStcx,"");
                    thirdStcx=thirdStcx.replaceFirst(inputListStc.get(0).toString(),"");

                    final SpannableStringBuilder firstx = new SpannableStringBuilder(firstStcx);
                    final SpannableStringBuilder secondx = new SpannableStringBuilder(secondStcx);
                    final SpannableStringBuilder thirdx = new SpannableStringBuilder(thirdStcx);

                    final StyleSpan normalSpanx = new StyleSpan(Typeface.NORMAL);
                    final StyleSpan boldSpanx = new StyleSpan(Typeface.BOLD);

                    firstx.setSpan(normalSpanx,0,firstx.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    secondx.setSpan(boldSpanx,0,secondx.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    thirdx.setSpan(normalSpanx,0,thirdx.length(),Spannable.SPAN_INCLUSIVE_INCLUSIVE);

                    inputText.setText(firstx);
                    inputText.append(secondx);
                    inputText.append(thirdx);

                    histText.setText(histList.get(0).toString().substring(0,histList.get(0).toString().length()-5));

                    Log.w("Stcx", inputListStc.toString());
                    Log.w("Stcx 1", firstStcx);
                    Log.w("Stcx 2", secondStcx);
                    Log.w("Stcx 3", thirdStcx);

                    listStc.remove(0);

                    break;
                default:
                    String firstStc,secondStc,thirdStc = "";
                    secondStc=" "+inputList.get(0).toString()+" ";
                    if ((inputList.size()>0)) {
                        int n = 0;
                        StringBuilder tempWord = new StringBuilder();
                        do {
                            tempWord.append(inputList.get(n).toString()).append(" ");
                            n = n + 1;
                        } while (!(inputList.get(n).equals("!")));
                        thirdStc= String.valueOf(tempWord);
                    }

                    firstStc=tempStc.toString().replace(thirdStc,"");
                    thirdStc=thirdStc.replace(inputList.get(0).toString(),"");

                    final SpannableStringBuilder first = new SpannableStringBuilder(firstStc);
                    final SpannableStringBuilder second = new SpannableStringBuilder(secondStc);
                    final SpannableStringBuilder third = new SpannableStringBuilder(thirdStc);

                    final StyleSpan normalSpan = new StyleSpan(Typeface.NORMAL);
                    final StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);

                    first.setSpan(normalSpan,0,first.length(),Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    second.setSpan(boldSpan,0,second.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    third.setSpan(normalSpan,0,third.length(),Spannable.SPAN_INCLUSIVE_INCLUSIVE);

                    //textView.setText(first);
                    //textView.append(second);
                    //textView.append(third);

                    Uri videoUri = getMedia(inputList.get(0).toString());
                    mVideoView.setVideoURI(videoUri);
                    if (mCurrentPosition > 0) {
                        mVideoView.seekTo(mCurrentPosition);
                    } else {
                        mVideoView.seekTo(1);
                    }
                    inputList.remove(0);
                    mVideoView.start();
                    mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            mediaPlayer.reset();
                            new Handler().postDelayed(new Runnable() {
                                public void run() {
                                    initializePlayer(inputList,inputListStc,histList);
                                }
                            }, wordDelay);
                        }
                    });
                    break;
            }
        }
        else {
            inputText.setText(inputStc2);
        }
    }


    private void releasePlayer() {
        mVideoView.stopPlayback();
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
            mVideoView.pause();
        }
    }


}
