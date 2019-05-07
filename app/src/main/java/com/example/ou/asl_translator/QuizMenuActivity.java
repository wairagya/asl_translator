package com.example.ou.asl_translator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class QuizMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_menu);
        Button menu1 = findViewById(R.id.menu1);
        Button menu2 = findViewById(R.id.menu2);
        Button menu3 = findViewById(R.id.menu3);
        menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizMenuActivity.this,WordQuizActivity.class);
                startActivity(intent);
                finish();
            }
        });
        menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizMenuActivity.this,ArrangeQuizActivity.class);
                startActivity(intent);
                finish();
            }
        });
        menu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizMenuActivity.this,StcQuizActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
