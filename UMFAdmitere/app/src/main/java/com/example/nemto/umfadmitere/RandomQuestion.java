package com.example.nemto.umfadmitere;

/**
 * Created by nemto on 02.08.2017.
 */


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

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

        //BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        //String res_data;
        //backgroundWorker.execute(year,category);

        try {
            String[] output = new BackgroundWorker(this).execute(year,category).get();
            textView.setText(output[2]);
            RadioButton radio1 = (RadioButton) findViewById(R.id.radioButton1);
            RadioButton radio2 = (RadioButton) findViewById(R.id.radioButton2);
            RadioButton radio3 = (RadioButton) findViewById(R.id.radioButton3);
            RadioButton radio4 = (RadioButton) findViewById(R.id.radioButton4);
            radio1.setText(output[3]);
            radio2.setText(output[4]);
            radio3.setText(output[5]);
            radio4.setText(output[6]);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        ///////--------------------------------

    }

}

