package com.example.ou.asl_translator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.ou.asl_translator.database.DataHelper;
import com.example.ou.asl_translator.model.QuizScore;

public class MenuAslActivity extends AppCompatActivity {
    private LinearLayout learnButton,dictButton,transButton,quizButton,aboutButton,settingButton;
    DataHelper dataHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_asl);
        learnButton = findViewById(R.id.menu_learn);
        dictButton = findViewById(R.id.menu_dict);
        transButton = findViewById(R.id.menu_trans);
        quizButton = findViewById(R.id.menu_quiz);
        aboutButton = findViewById(R.id.menu_about);
        settingButton =findViewById(R.id.menu_setting);
        learnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuAslActivity.this, LearnActivity.class);
                startActivity(intent);
            }
        });
        dictButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuAslActivity.this, DictActivity.class);
                startActivity(intent);
            }
        });
        transButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuAslActivity.this, TransActivity.class);
                startActivity(intent);
            }
        });
        quizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuAslActivity.this, QuizMenuActivity.class);
                startActivity(intent);
            }
        });
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuAslActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuAslActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });
        dataHelper = new DataHelper(this);
        dataHelper.getWritableDatabase();
        if (dataHelper.getStcDelay()==-1||dataHelper.getWordDelay()==-1){
            dataHelper.initDelay();
        }
        ((QuizScore) MenuAslActivity.this.getApplication()).setWordDelay(dataHelper.getWordDelay());
        ((QuizScore) MenuAslActivity.this.getApplication()).setStcDelay(dataHelper.getStcDelay());
    }
}
