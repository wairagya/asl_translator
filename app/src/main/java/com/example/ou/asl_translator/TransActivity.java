package com.example.ou.asl_translator;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.ou.asl_translator.api.ApiRequest;
import com.example.ou.asl_translator.api.RetroClient;
import com.example.ou.asl_translator.database.DataHelper;
import com.example.ou.asl_translator.model.HistoryModel;
import com.example.ou.asl_translator.model.QuizScore;
import com.example.ou.asl_translator.model.ResponseApiModel;
import com.tom_roush.pdfbox.pdmodel.PDDocument;
import com.tom_roush.pdfbox.text.PDFTextStripper;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransActivity extends Activity {
    private EditText inputText;
    private ImageView sideMenu;
    private VideoView mVideoView;
    private ProgressDialog dialog;
    private int mCurrentPosition = 0;
    private Button pauseButton,transButton,importButton,saveButton,resetButton,resumeButton;
    private CardView stcMarker;
    private TextView textView,back;
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
    private String tempList2="",tempListStc2="";
    private StringBuilder tempStc = new StringBuilder();
    private String inputStc,inputStc2;
    private int tempInt,wordDelay,stcDelay,progress,progressList;
    private String intentDescription,filename;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        importStatus=findViewById(R.id.importStatus);
        saveButton=findViewById(R.id.saveButton);
        resetButton=findViewById(R.id.resetButton);
        resumeButton=findViewById(R.id.resumeButton);
        sideMenu=findViewById(R.id.sideMenu);
        sideMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransActivity.this, TransLearnActivity.class);
                startActivity(intent);
                finish();
            }
        });
        resetButton=findViewById(R.id.resetButton);
        back=findViewById(R.id.back);
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
        dialog = new ProgressDialog(TransActivity.this);
        dialog.setMessage("Loading...");
        stcMarker=findViewById(R.id.stcMarker);
        inputText = findViewById(R.id.inputText);
        transButton = findViewById(R.id.transButton);
        importButton = findViewById(R.id.importButton);
        pauseButton = findViewById(R.id.pauseButton);
        textView = findViewById(R.id.textView);
        wordDelay=((QuizScore) this.getApplication()).getWordDelay()*1000;
        stcDelay=((QuizScore) this.getApplication()).getStcDelay()*1000;
        transButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputStc2=inputText.getText().toString();
                inputStc =inputText.getText().toString();
                inputStc=inputStc.replaceAll(",",".");
                inputStc=inputStc.replaceFirst("\\s++$", "");
                if (inputStc.length()>0){
                    Log.w("inputStc", inputStc);
                    if (!inputStc.substring(inputStc.length()-1).equals(".")) {
                        inputStc=inputStc+".";
                        Log.w("Stts", "True .: ");
                    }
                    stcMarker.setVisibility(View.VISIBLE);
                    inputStc=inputStc.replaceAll("\n","");
                    String[] inStc=inputStc.split("\\.");
                    listStc.clear();
                    listStc.addAll(Arrays.asList(inStc));
                    tempInt=0;
                    ApiRequest Api = RetroClient.getRequestService();
                    Call<ResponseApiModel> checker = Api.checker(inputStc);
                    checker.enqueue(new Callback<ResponseApiModel>() {
                        @Override
                        public void onResponse(Call<ResponseApiModel> call, Response<ResponseApiModel> response) {
                            ResponseApiModel res = response.body();
                            assert res != null;
                            String word=res.getPhares();
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
                            pauseButton.setHint("Pause");
                            Log.w("Daftar Word", list.toString());
                            Log.w("Daftar Stc", listStc.toString());
                            progress=list.size()+1;
                            initializePlayer(list,listStc);
                        }

                        @Override
                        public void onFailure(Call<ResponseApiModel> call, Throwable t) {
                            Toast.makeText(TransActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
//        importButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showHistory();
//            }
//        });
        importButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showHistory(TransActivity.this);
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                //intent.setType("*/.pdf*");
//                intent.setType("application/pdf");
//                startActivityForResult(intent, 7);
            }
        });
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                releasePlayer();
                inputText.setText("");
                pauseButton.setHint("Pause");
                importStatus.setVisibility(View.GONE);
                stcMarker.setVisibility(View.GONE);
            }
        });
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pauseButton.getHint().equals("Play")){
                    pauseButton.setHint("Pause");
                    initializePlayer(list,listStc);
                }
                else if (pauseButton.getHint().equals("Pause")){
                    pauseButton.setHint("Play");
                }
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String list1= tempList2;
                Log.d("Save: List1", tempList2);
                String list2= tempListStc2;
                Log.d("Save: List2", tempListStc2);
                String inStc1=inputStc;
                Log.d("Save: InputStc1", inputStc);
                String inStc2=inputStc2;
                Log.d("Save: InputStc2", inputStc2);
                String intDesc=intentDescription;
                int tempProgress=100-(progressList*100/progress);
                DataHelper dataHelper = new DataHelper(TransActivity.this);
                dataHelper.getWritableDatabase();
                Log.d("Saving String", intDesc+"\n"+list1+"\n"+list2+"\n"+inStc1+"\n"+inStc2);
                Log.d("Saving Integer", String.valueOf(tempProgress));
                dataHelper.updateHistory(intDesc,list1,list2,inStc1,inStc2,tempProgress);
                Toast.makeText(TransActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                transButton.setVisibility(View.VISIBLE);
                resumeButton.setVisibility(View.GONE);
                importStatus.setVisibility(View.GONE);
                stcMarker.setVisibility(View.GONE);
                pauseButton.setHint("Pause");
                inputText.setText("");
                releasePlayer();
            }
        });
        resumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stcMarker.setVisibility(View.VISIBLE);
                pauseButton.setHint("Pause");
                initializePlayer(list,listStc);
                transButton.setVisibility(View.VISIBLE);
                resumeButton.setVisibility(View.GONE);
                importStatus.setVisibility(View.VISIBLE);
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

    private void initializePlayer(final List inputList,final List inputListStc) {
        if (pauseButton.getHint().equals("Pause")){
            if(!(inputList.isEmpty())){
                switch (inputList.get(0).toString()) {
                    case "!":
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                inputList.remove(0);
                                Log.d("Save: Inputlist !", inputList.toString());
                                tempList2=inputList.toString();
                                progressList=inputList.size();
                                tempListStc2=inputListStc.toString();
                                Log.d("Save: tempList2 !", tempList2);
                                initializePlayer(inputList,inputListStc);
                            }
                        }, stcDelay);
                        break;
                    case ",":
                        inputList.remove(0);
                        Log.d("Save: tempList2 ,", tempList2);
                        Log.d("Save: Inputlist ,", inputList.toString());
                        initializePlayer(inputList,inputListStc);
                        break;
                    case "?":
                        inputList.remove(0);
                        Log.d("Save: tempList2 ?", tempList2);
                        Log.d("Save: Inputlist ?", inputList.toString());
                        tempStc.setLength(0);
                        if (!(inputList.isEmpty())) {
                            int n = 0;
                            do {
                                tempStc.append(inputList.get(n).toString()).append(" ");
                                n = n + 1;
                            } while (!(inputList.get(n).equals("!")));
                            textView.setText(tempStc);
                            initializePlayer(inputList,inputListStc);
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

                        firstx.setSpan(normalSpanx,0,firstx.length(),Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        secondx.setSpan(boldSpanx,0,secondx.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        thirdx.setSpan(normalSpanx,0,thirdx.length(),Spannable.SPAN_INCLUSIVE_INCLUSIVE);

                        inputText.setText(firstx);
                        inputText.append(secondx);
                        inputText.append(thirdx);

                        Log.w("Stcx", inputListStc.toString());
                        Log.w("Stcx 1", firstStcx);
                        Log.w("Stcx 2", secondStcx);
                        Log.w("Stcx 3", thirdStcx);

                        listStc.remove(0);

                        break;
                    default:
                        Log.d("Save: tempList2 nor", tempList2.toString());
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

                        textView.setText(first);
                        textView.append(second);
                        textView.append(third);

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
                                        initializePlayer(inputList,inputListStc);
                                    }
                                }, wordDelay);
                            }
                        });
                        break;
                }
            }
            else {
                stcMarker.setVisibility(View.GONE);
                pauseButton.setHint("Play");
                inputText.setText(inputStc2);
            }
        }
    }

    public void showHistory(final Activity activity){
        final Dialog dialog = new Dialog(activity);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.history_dialog);
        Button importButton = dialog.findViewById(R.id.buttonImport);
        Button cancelButton = dialog.findViewById(R.id.buttonCancel);
        final DataHelper dataHelper = new DataHelper(activity.getApplicationContext());
        dataHelper.getWritableDatabase();
        List historyList = new ArrayList();
        List uidList = new ArrayList();
        List currentProgress = new ArrayList();
        List<HistoryModel> historyModels = dataHelper.getHistory();
        if (historyModels.size()>0){
            for (int i=0;i<historyModels.size();i++){
                HistoryModel historyModel = historyModels.get(i);
                String tempFilename=historyModel.getFilename();
                tempFilename=tempFilename.substring(tempFilename.indexOf(":")+1,tempFilename.length());
                historyList.add(tempFilename);
                uidList.add(historyModel.getUid());
                currentProgress.add(historyModel.getProgress());
                Log.d("HistoryList", historyList.toString());
                Log.d("HistoryList", String.valueOf(historyModel.getProgress()));
                Log.d("HistoryList", String.valueOf(historyModel.getUid()));

            }
        }
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        importButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                //intent.setType("*/.pdf*");
                intent.setType("application/pdf");
                startActivityForResult(intent, 7);

//                String filename=editText.getText().toString();
//                dataHelper.addHistory(filename);
//                dataHelper.addHistory(intentDescription,intentDescription);
                dialog.dismiss();
                importStatus.setVisibility(View.VISIBLE);
            }
        });
        final List<Map<String, String>> tempList = new ArrayList<>();
        Map<String, String> map;
        int count = historyList.size();
        Log.d("TempList HistoryList", historyList.toString());
        Log.d("TempList curr", currentProgress.toString());
        for(int i = 0; i < count; i++) {
            map = new HashMap<>();
            map.put("name", historyList.get(i).toString());
            String tempString = String.valueOf(currentProgress.get(i));
            tempString=tempString+"%";
            map.put("total", tempString);
            map.put("id",uidList.get(i).toString());
            Log.d("TempList", tempList.toString());
            tempList.add(map);
        }
        final ListView listView = dialog.findViewById(R.id.listView);
        final SimpleAdapter adapter = new SimpleAdapter(this, tempList, R.layout.history_list, new String[] { "name", "total" }, new int[] { R.id.historyTV, R.id.intentDesc });
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String string = tempList.get(position).get("id");
                List listData = dataHelper.importHistoryData(Integer.parseInt(string));
                String[] items = listData.get(0).toString().replaceAll(" ","").replace("[","").replace("]","").split(",");
                List<String> listList = new ArrayList<String>(Arrays.asList(items));
                list=listList;
                Log.d("LOAD: list1", list.toString());
                items = listData.get(1).toString().replaceAll("\\s+"," ").replace("[","").replace("]","").split(",");
                listList = new ArrayList<String>(Arrays.asList(items));
                listStc=listList;
                Log.d("LOAD: list2", listStc.toString());
                inputStc=listData.get(2).toString();
                Log.d("LOAD: InputStc1", inputStc);
                inputStc2=listData.get(3).toString();
                Log.d("LOAD: InputStc2", inputStc2);
//                inputStc2=inputText.getText().toString();
//                inputStc =inputText.getText().toString();
                inputStc=inputStc.replaceAll(",",".");
                inputStc=inputStc.replaceFirst("\\s++$", "");
                inputText.setText(inputStc);
                Toast.makeText(TransActivity.this, string, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                transButton.setVisibility(View.GONE);
                resumeButton.setVisibility(View.VISIBLE);
            }
        });
        Button clearButton = dialog.findViewById(R.id.buttonClear);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataHelper.clearHistory();
                dialog.dismiss();
            }
        });
        dialog.show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data!=null){
            switch (requestCode) {
                case 7:
                    if (resultCode == RESULT_OK) {
                        dialog.show();
                        intentDescription = data.toUri(0);
                        filename=data.getData().getPath();

                        DataHelper dataHelper = new DataHelper(this);
                        dataHelper.getWritableDatabase();
                        dataHelper.addHistory(filename,intentDescription);
                        Log.d("filename: ", filename);
                        Log.d("intent: ", intentDescription);
                        Intent intent = null;
                        try {
                            intent = Intent.parseUri(intentDescription, 0);
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                        new setText(this).execute(intent.getData());
                    }
                    break;
            }
        }

    }

    private static class setText extends AsyncTask<Uri, Integer, String>{
        WeakReference<TransActivity> transActivityWeakReference;
        setText(TransActivity transActivity) {
            this.transActivityWeakReference = new WeakReference<TransActivity>(transActivity);
        }

        @Override
        protected void onPostExecute(String text) {
            TransActivity transActivity=transActivityWeakReference.get();
            if (transActivity!=null){
                transActivity.setPdfText(text);
                transActivity.dismissPg();
            }
        }

        @Override
        protected String doInBackground(Uri... uri) {
            String parsedText = null;
            PDDocument document = null;
            try {
                document = PDDocument.load(Objects.requireNonNull(transActivityWeakReference.get().getContentResolver().openInputStream(uri[0])));
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                PDFTextStripper pdfStripper = new PDFTextStripper();
                pdfStripper.setStartPage(0);
                pdfStripper.setEndPage(1);
                parsedText = pdfStripper.getText(document);
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                try {
                    if (document != null) document.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            return parsedText;
        }
    }

    public void setPdfText(String text){
        inputText.setText(text);
    }

    public void dismissPg(){
        dialog.dismiss();
    }

}
