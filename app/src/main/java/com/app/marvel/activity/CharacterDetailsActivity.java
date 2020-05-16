package com.app.marvel.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.app.marvel.R;

public class CharacterDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_details);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("title");
        //actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case android.R.id.home:
                finish();
                break;
        }
    }
}
