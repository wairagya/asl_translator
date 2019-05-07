package com.example.ou.asl_translator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.ou.asl_translator.database.DataHelper;
import com.example.ou.asl_translator.model.QuizScore;

public class SettingActivity extends AppCompatActivity {
    private SeekBar wordSeekBar,stcSeekBar;
    private TextView wordTv,stcTv,back;
    private Button applyBtn;
    DataHelper dataHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        dataHelper = new DataHelper(this);
        dataHelper.getWritableDatabase();
        wordSeekBar=findViewById(R.id.seekBarWordDelay);
        stcSeekBar=findViewById(R.id.seekBarStcDelay);
        wordTv=findViewById(R.id.wordDelayTV);
        stcTv=findViewById(R.id.stcDelayTV);
        applyBtn=findViewById(R.id.apply);
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        int val;
        val=((QuizScore) this.getApplication()).getWordDelay();
        wordTv.setText(String.valueOf(val));
        wordSeekBar.setProgress(val);
        val=((QuizScore) this.getApplication()).getStcDelay();
        stcTv.setText(String.valueOf(val));
        stcSeekBar.setProgress(val);
        wordSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                wordTv.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        stcSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                stcTv.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((QuizScore) SettingActivity.this.getApplication()).setWordDelay(Integer.valueOf(String.valueOf(wordTv.getText())));
                ((QuizScore) SettingActivity.this.getApplication()).setStcDelay(Integer.valueOf(String.valueOf(stcTv.getText())));
                dataHelper.updateDelay(Integer.valueOf(String.valueOf(wordTv.getText())),Integer.valueOf(String.valueOf(stcTv.getText())));
                finish();
            }
        });
    }
}
