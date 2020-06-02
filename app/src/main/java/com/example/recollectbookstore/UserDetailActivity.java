package com.example.recollectbookstore;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class UserDetailActivity extends AppCompatActivity {

    public static final String OWNERID = "ownerID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_detail);

        Bundle extras = getIntent().getExtras();
        long ownerID = -1;

        if (extras != null) {
            ownerID = extras.getLong(OWNERID);
        }


    }
}
