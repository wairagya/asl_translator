package com.example.ou.asl_translator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MainMenuActivity extends AppCompatActivity {
    private LinearLayout menuIndo,menuAsl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        menuIndo =findViewById(R.id.menu_indo);
        menuIndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenuActivity.this, MenuIndoActivity.class);
                startActivity(intent);
            }
        });
        menuAsl =findViewById(R.id.menu_asl);
        menuAsl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenuActivity.this, MenuAslActivity.class);
                startActivity(intent);
            }
        });
    }
}
