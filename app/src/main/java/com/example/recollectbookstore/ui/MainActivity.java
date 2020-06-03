package com.example.recollectbookstore.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.recollectbookstore.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     *
     * @param view
     *
     * Go to page with list of books obtained from the API
     */
    public void enter(View view) {
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }
}
