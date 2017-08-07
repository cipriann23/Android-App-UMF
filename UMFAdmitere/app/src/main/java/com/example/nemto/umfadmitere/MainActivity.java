package com.example.nemto.umfadmitere;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /** Called when the user taps the Biologie button */
    public void buttonBiologie (View view) {

        boolean isChecked = ((CheckBox) findViewById(R.id.currentYear)).isChecked();
        boolean isCheckedAll = ((CheckBox) findViewById(R.id.lastYears)).isChecked();
        String year;

        if(isChecked==true && isCheckedAll==false){
            year = "last";
        }else{
            year = "all";
        }

        if(isCheckedAll){
            year = "all";
        }

        Intent intent = new Intent(this, RandomQuestion.class);
        String category = "biologie";
        intent.putExtra("category",category);
        intent.putExtra("year",year);
        startActivity(intent);

    }


    /** Called when the user taps the Chimie button */
    public void buttonChimie (View view) {

        boolean isChecked = ((CheckBox) findViewById(R.id.currentYear)).isChecked();
        boolean isCheckedAll = ((CheckBox) findViewById(R.id.lastYears)).isChecked();
        String year;

        if(isChecked==true && isCheckedAll==false){
            year = "last";
        }else{
            year = "all";
        }

        if(isCheckedAll){
            year = "all";
        }

        Intent intent = new Intent(this, RandomQuestion.class);
        String category = "chimie";
        intent.putExtra("category",category);
        intent.putExtra("year",year);
        startActivity(intent);

    }
}

