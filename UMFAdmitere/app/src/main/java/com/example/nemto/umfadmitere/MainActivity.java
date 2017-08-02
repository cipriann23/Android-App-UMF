package com.example.nemto.umfadmitere;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user taps the Biologie button */
    public void buttonBiologie (View view) {
        Intent intent = new Intent(this, RandomQuestion.class);
        String category = "biologie";
        intent.putExtra("name_key",category);
        startActivity(intent);
    }


    /** Called when the user taps the Chimie button */
    public void buttonChimie (View view) {
        Intent intent = new Intent(this, RandomQuestion.class);
        String category = "chimie";
        intent.putExtra("name_key",category);
        startActivity(intent);
    }
}

