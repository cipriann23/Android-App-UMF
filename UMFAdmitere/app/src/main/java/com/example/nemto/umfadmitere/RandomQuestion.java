package com.example.nemto.umfadmitere;

/**
 * Created by nemto on 02.08.2017.
 */


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.TextView;

public class RandomQuestion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_question);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String category = intent.getStringExtra("category");
        String year = intent.getStringExtra("year");
        // Capture the layout's TextView and set the string as its text
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(category);

        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(year,category);

        ///////--------------------------------

    }

}

