package com.example.recollectbookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView fundoView = findViewById(R.id.fundo);

        //Blurry.with(getApplicationContext()).capture(fundoView).into(fundoView);
    }

    public void enter(View view) {
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }
}
