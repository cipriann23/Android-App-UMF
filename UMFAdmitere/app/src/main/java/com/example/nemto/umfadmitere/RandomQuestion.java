package com.example.nemto.umfadmitere;

/**
 * Created by nemto on 02.08.2017.
 */


import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.util.concurrent.ExecutionException;

public class RandomQuestion extends Activity {


    Integer answer;
    String category, year, theme_set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //setTheme(R.style.AppThemeDark);
        // Get the Intent that started this activity and extract the string

        Intent intent = getIntent();
        theme_set = intent.getStringExtra("theme");
        if (theme_set.equals("dark")) {
            setTheme(R.style.AppThemeDark);
        } else {
            setTheme(R.style.AppTheme);
        }

        setContentView(R.layout.activity_random_question);

        category = intent.getStringExtra("category");
        year = intent.getStringExtra("year");

        //BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        //String res_data;
        //backgroundWorker.execute(year,category);

        try {
            String[] output = new BackgroundWorker(this).execute(year, category).get();
            TextView textView = (TextView) findViewById(R.id.textView);
            textView.setText(output[0]);
            RadioButton radio1 = (RadioButton) findViewById(R.id.radioButton1);
            RadioButton radio2 = (RadioButton) findViewById(R.id.radioButton2);
            RadioButton radio3 = (RadioButton) findViewById(R.id.radioButton3);
            RadioButton radio4 = (RadioButton) findViewById(R.id.radioButton4);
            radio1.setText(output[1]);
            radio2.setText(output[2]);
            radio3.setText(output[3]);
            radio4.setText(output[4]);
            answer = Integer.parseInt(output[5]);
            //Log.d("myTag", output[6]);
            //Bitmap image;
            //image = BitmapFactory.decodeStream();

            //textView.setText(output[6]);
            ImageView image = (ImageView) findViewById(R.id.imageView2);
            byte[] decodedString = Base64.decode(output[6], Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0,decodedString.length);
            image.setImageBitmap(decodedByte);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        ///////--------------------------------

    }

    /**
     * Called when the user taps the Raspunde button
     */
    public void buttonRaspunde(View view) {

        boolean isChecked1 = ((RadioButton) findViewById(R.id.radioButton1)).isChecked();
        boolean isChecked2 = ((RadioButton) findViewById(R.id.radioButton2)).isChecked();
        boolean isChecked3 = ((RadioButton) findViewById(R.id.radioButton3)).isChecked();
        boolean isChecked4 = ((RadioButton) findViewById(R.id.radioButton4)).isChecked();
        int checked = 0;
        if (isChecked1) checked = 1;
        if (isChecked2) checked = 2;
        if (isChecked3) checked = 3;
        if (isChecked4) checked = 4;

        TextView feedback = (TextView) findViewById(R.id.feedback);

        if (answer == checked) {
            feedback.setTextColor(Color.rgb(0, 204, 0));
            feedback.setText("Răspuns corect!");

            findViewById(R.id.radioButton1).setEnabled(false);
            findViewById(R.id.radioButton2).setEnabled(false);
            findViewById(R.id.radioButton3).setEnabled(false);
            findViewById(R.id.radioButton4).setEnabled(false);

            findViewById(R.id.buttonRaspunde).setVisibility(View.INVISIBLE);
            findViewById(R.id.buttonUrmatoarea).setVisibility(View.VISIBLE);

        }
        if (answer != checked) {
            feedback.setTextColor(Color.rgb(200, 0, 0));
            feedback.setText("Răspuns gresit! Mai încearcă");
        }
        if (checked == 0) {
            feedback.setTextColor(Color.rgb(200, 0, 0));
            feedback.setText("Selectați un răspuns!");
        }
    }

    /**
     * Called when the user taps the Urmatoare button
     */
    public void buttonUrmatoarea(View view) {

        Intent intent = new Intent(this, RandomQuestion.class);
        intent.putExtra("category", category);
        intent.putExtra("year", year);
        intent.putExtra("theme", theme_set);
        startActivity(intent);
        finish();
    }

}

