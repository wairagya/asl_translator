package com.example.ou.asl_translator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


public class LearnActivity extends AppCompatActivity {
    private LinearLayout content1a,content1b,content1c,content1d,content1e,content1f,content1g,
    content2a,content2b,content2c,content2d,content2e,content2f,content2g,
    content3a,content3b,content3c,content3d,content3e,
    content4a,content4b,
    content5a,content5b,content5c;
    private TextView asl1a,asl1b,asl1c,asl1d,asl1e,asl1f,asl1g,
            asl2a,asl2b,asl2c,asl2d,asl2e,asl2f,asl2g,
            asl3a,asl3b,asl3c,asl3d,asl3e,
            asl4a,asl4b,
            asl5a,asl5b,asl5c,back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        content1a=findViewById(R.id.content1a);
        asl1a=findViewById(R.id.asl1a);
        content1a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dictDetail = new Intent(LearnActivity.this, DictDetailActivity.class);
                dictDetail.putExtra("word",asl1a.getText());
                dictDetail.putExtra("type","stc");
                startActivity(dictDetail);
            }
        });
        content1b=findViewById(R.id.content1b);
        asl1b=findViewById(R.id.asl1b);
        content1b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dictDetail = new Intent(LearnActivity.this, DictDetailActivity.class);
                dictDetail.putExtra("word",asl1b.getText());
                dictDetail.putExtra("type","stc");
                startActivity(dictDetail);
            }
        });
        content1c=findViewById(R.id.content1c);
        asl1c=findViewById(R.id.asl1c);
        content1c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dictDetail = new Intent(LearnActivity.this, DictDetailActivity.class);
                dictDetail.putExtra("word",asl1c.getText());
                dictDetail.putExtra("type","stc");
                startActivity(dictDetail);
            }
        });
        content1d=findViewById(R.id.content1d);
        asl1d=findViewById(R.id.asl1d);
        content1d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dictDetail = new Intent(LearnActivity.this, DictDetailActivity.class);
                dictDetail.putExtra("word",asl1d.getText());
                dictDetail.putExtra("type","stc");
                startActivity(dictDetail);
            }
        });
        content1e=findViewById(R.id.content1e);
        asl1e=findViewById(R.id.asl1e);
        content1e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dictDetail = new Intent(LearnActivity.this, DictDetailActivity.class);
                dictDetail.putExtra("word",asl1e.getText());
                dictDetail.putExtra("type","stc");
                startActivity(dictDetail);
            }
        });
        content1f=findViewById(R.id.content1f);
        asl1f=findViewById(R.id.asl1f);
        content1f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dictDetail = new Intent(LearnActivity.this, DictDetailActivity.class);
                dictDetail.putExtra("word",asl1f.getText());
                dictDetail.putExtra("type","stc");
                startActivity(dictDetail);
            }
        });
        content1g=findViewById(R.id.content1g);
        asl1g=findViewById(R.id.asl1g);
        content1g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dictDetail = new Intent(LearnActivity.this, DictDetailActivity.class);
                dictDetail.putExtra("word",asl1g.getText());
                dictDetail.putExtra("type","stc");
                startActivity(dictDetail);
            }
        });



        content2a=findViewById(R.id.content2a);
        asl2a=findViewById(R.id.asl2a);
        content2a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dictDetail = new Intent(LearnActivity.this, DictDetailActivity.class);
                dictDetail.putExtra("word",asl2a.getText());
                dictDetail.putExtra("type","stc");
                startActivity(dictDetail);
            }
        });
        content2b=findViewById(R.id.content2b);
        asl2b=findViewById(R.id.asl2b);
        content2b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dictDetail = new Intent(LearnActivity.this, DictDetailActivity.class);
                dictDetail.putExtra("word",asl2b.getText());
                dictDetail.putExtra("type","stc");
                startActivity(dictDetail);
            }
        });
        content2c=findViewById(R.id.content2c);
        asl2c=findViewById(R.id.asl2c);
        content2c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dictDetail = new Intent(LearnActivity.this, DictDetailActivity.class);
                dictDetail.putExtra("word",asl2c.getText());
                dictDetail.putExtra("type","stc");
                startActivity(dictDetail);
            }
        });
        content2d=findViewById(R.id.content2d);
        asl2d=findViewById(R.id.asl2d);
        content2d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dictDetail = new Intent(LearnActivity.this, DictDetailActivity.class);
                dictDetail.putExtra("word",asl2d.getText());
                dictDetail.putExtra("type","stc");
                startActivity(dictDetail);
            }
        });
        content2e=findViewById(R.id.content2e);
        asl2e=findViewById(R.id.asl2e);
        content2e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dictDetail = new Intent(LearnActivity.this, DictDetailActivity.class);
                dictDetail.putExtra("word",asl2e.getText());
                dictDetail.putExtra("type","stc");
                startActivity(dictDetail);
            }
        });
        content2f=findViewById(R.id.content2f);
        asl2f=findViewById(R.id.asl2f);
        content2f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dictDetail = new Intent(LearnActivity.this, DictDetailActivity.class);
                dictDetail.putExtra("word",asl2f.getText());
                dictDetail.putExtra("type","stc");
                startActivity(dictDetail);
            }
        });
        content2g=findViewById(R.id.content2g);
        asl2g=findViewById(R.id.asl2g);
        content2g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dictDetail = new Intent(LearnActivity.this, DictDetailActivity.class);
                dictDetail.putExtra("word",asl2g.getText());
                dictDetail.putExtra("type","stc");
                startActivity(dictDetail);
            }
        });



        content3a=findViewById(R.id.content3a);
        asl3a=findViewById(R.id.asl3a);
        content3a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dictDetail = new Intent(LearnActivity.this, DictDetailActivity.class);
                dictDetail.putExtra("word",asl3a.getText());
                dictDetail.putExtra("type","stc");
                startActivity(dictDetail);
            }
        });
        content3b=findViewById(R.id.content3b);
        asl3b=findViewById(R.id.asl3b);
        content3b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dictDetail = new Intent(LearnActivity.this, DictDetailActivity.class);
                dictDetail.putExtra("word",asl3b.getText());
                dictDetail.putExtra("type","stc");
                startActivity(dictDetail);
            }
        });
        content3c=findViewById(R.id.content3c);
        asl3c=findViewById(R.id.asl3c);
        content3c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dictDetail = new Intent(LearnActivity.this, DictDetailActivity.class);
                dictDetail.putExtra("word",asl3c.getText());
                dictDetail.putExtra("type","stc");
                startActivity(dictDetail);
            }
        });
        content3d=findViewById(R.id.content3d);
        asl3d=findViewById(R.id.asl3d);
        content3d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dictDetail = new Intent(LearnActivity.this, DictDetailActivity.class);
                dictDetail.putExtra("word",asl3d.getText());
                dictDetail.putExtra("type","stc");
                startActivity(dictDetail);
            }
        });
        content3e=findViewById(R.id.content3e);
        asl3e=findViewById(R.id.asl3e);
        content3e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dictDetail = new Intent(LearnActivity.this, DictDetailActivity.class);
                dictDetail.putExtra("word",asl3e.getText());
                dictDetail.putExtra("type","stc");
                startActivity(dictDetail);
            }
        });
        content4a=findViewById(R.id.content4a);
        asl4a=findViewById(R.id.asl4a);
        content4a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dictDetail = new Intent(LearnActivity.this, DictDetailActivity.class);
                dictDetail.putExtra("word",asl4a.getText());
                dictDetail.putExtra("type","stc");
                startActivity(dictDetail);
            }
        });
        content4b=findViewById(R.id.content4b);
        asl4b=findViewById(R.id.asl4b);
        content4b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dictDetail = new Intent(LearnActivity.this, DictDetailActivity.class);
                dictDetail.putExtra("word",asl4b.getText());
                dictDetail.putExtra("type","stc");
                startActivity(dictDetail);
            }
        });
        content5a=findViewById(R.id.content5a);
        asl5a=findViewById(R.id.asl5a);
        content5a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dictDetail = new Intent(LearnActivity.this, DictDetailActivity.class);
                dictDetail.putExtra("word",asl5a.getText());
                dictDetail.putExtra("type","stc");
                startActivity(dictDetail);
            }
        });
        content5b=findViewById(R.id.content5b);
        asl5b=findViewById(R.id.asl5b);
        content5b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dictDetail = new Intent(LearnActivity.this, DictDetailActivity.class);
                dictDetail.putExtra("word",asl5b.getText());
                dictDetail.putExtra("type","stc");
                startActivity(dictDetail);
            }
        });
        content5c=findViewById(R.id.content5c);
        asl5c=findViewById(R.id.asl5c);
        content5c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dictDetail = new Intent(LearnActivity.this, DictDetailActivity.class);
                dictDetail.putExtra("word",asl5c.getText());
                dictDetail.putExtra("type","stc");
                startActivity(dictDetail);
            }
        });
    }
}
