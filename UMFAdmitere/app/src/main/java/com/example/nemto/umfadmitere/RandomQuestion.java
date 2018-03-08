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

        try {
            String[] output = new BackgroundWorker(this).execute(year, category).get();
            TextView textView = (TextView) findViewById(R.id.textView);
            RadioButton radio1 = (RadioButton) findViewById(R.id.radioButton1);
            RadioButton radio2 = (RadioButton) findViewById(R.id.radioButton2);
            RadioButton radio3 = (RadioButton) findViewById(R.id.radioButton3);
            RadioButton radio4 = (RadioButton) findViewById(R.id.radioButton4);
            RadioButton radio5 = (RadioButton) findViewById(R.id.radioButton5);

            byte[] data0 = Base64.decode(output[0], Base64.DEFAULT);
            byte[] data1 = Base64.decode(output[1], Base64.DEFAULT);
            byte[] data2 = Base64.decode(output[2], Base64.DEFAULT);
            byte[] data3 = Base64.decode(output[3], Base64.DEFAULT);
            byte[] data4 = Base64.decode(output[4], Base64.DEFAULT);
            byte[] data5 = Base64.decode(output[5], Base64.DEFAULT);
            byte[] data6 = Base64.decode(output[6], Base64.DEFAULT);
            try {
                String text0 = new String(data0, "UTF-8");
                textView.setText(text0);

                String text1 = new String(data1, "UTF-8");
                String text2 = new String(data2, "UTF-8");
                String text3 = new String(data3, "UTF-8");
                String text4 = new String(data4, "UTF-8");
                String text5 = new String(data5, "UTF-8");
                String text6 = new String(data6, "UTF-8");

                radio1.setText(text1);
                radio2.setText(text2);
                radio3.setText(text3);
                radio4.setText(text4);
                radio5.setText(text5);
                answer = Integer.parseInt(text6);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


            int max = output.length;
            if(max > 7){

                ImageView image = (ImageView) findViewById(R.id.imageView2);
                byte[] decodedString = Base64.decode(output[7], Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                image.setImageBitmap(decodedByte);
            }
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
        boolean isChecked5 = ((RadioButton) findViewById(R.id.radioButton5)).isChecked();

        int checked = 0;
        if (isChecked1) checked = 1;
        if (isChecked2) checked = 2;
        if (isChecked3) checked = 3;
        if (isChecked4) checked = 4;
        if (isChecked5) checked = 5;

        TextView feedback = (TextView) findViewById(R.id.feedback);

        if (answer == checked) {
            feedback.setTextColor(Color.rgb(0, 204, 0));
            feedback.setText("Răspuns corect!");

            findViewById(R.id.radioButton1).setEnabled(false);
            findViewById(R.id.radioButton2).setEnabled(false);
            findViewById(R.id.radioButton3).setEnabled(false);
            findViewById(R.id.radioButton4).setEnabled(false);
            findViewById(R.id.radioButton5).setEnabled(false);
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

