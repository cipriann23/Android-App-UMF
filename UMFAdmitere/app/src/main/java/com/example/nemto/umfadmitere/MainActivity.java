package com.example.nemto.umfadmitere;


import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppThemeDark);
        setContentView(R.layout.activity_main);

        findViewById(R.id.toolbar).bringToFront();
        final FloatingActionButton menu = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        menu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(findViewById(R.id.toolbar).getVisibility() == View.VISIBLE) {
                    findViewById(R.id.toolbar).setVisibility(View.INVISIBLE);
                    findViewById(R.id.buttonBiologie).setVisibility(View.VISIBLE);
                    findViewById(R.id.buttonChimie).setVisibility(View.VISIBLE);
                }
                else {
                    findViewById(R.id.toolbar).setVisibility(View.VISIBLE);
                    findViewById(R.id.buttonBiologie).setVisibility(View.INVISIBLE);
                    findViewById(R.id.buttonChimie).setVisibility(View.INVISIBLE);
                }
            }
        });
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

