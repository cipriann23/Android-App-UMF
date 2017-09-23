package com.example.nemto.umfadmitere;


import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "App";
    private boolean isNightModeEnabled = false;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        if (AppCompatDelegate.getDefaultNightMode()
                == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.AppThemeDark);
        }

        super.onCreate(savedInstanceState);


        //setTheme(R.style.AppThemeDark);
        setContentView(R.layout.activity_main);


        try {
            SQLiteDatabase myDB = this.openOrCreateDatabase("DATABASE", MODE_PRIVATE, null);

   /* Create a Table in the Database. */
            String TableName = "NUMETABEL";
            myDB.execSQL("CREATE TABLE IF NOT EXISTS "
                    + TableName
                    + " (Field1 VARCHAR, Field2 INT(3));");

   /* Insert data to a Table
            myDB.execSQL("INSERT INTO "
                    + TableName
                    + " (Field1, Field2)"
                    + " VALUES ('Saranga', 22);");

            myDB.execSQL("INSERT INTO "
                    + TableName
                    + " (Field1, Field2)"
                    + " VALUES ('Casanga', 21);");

   /*retrieve data from database */
            Cursor c = myDB.rawQuery("SELECT * FROM " + TableName + " WHERE Field2 = ?", new String[]{"21"});

            int Column1 = c.getColumnIndex("Field1");
            int Column2 = c.getColumnIndex("Field2");

            // Check if our result was valid.
            c.moveToFirst();
            String Data = null;
            String Result;
            if (c != null) {
                // Loop through all Results
                do {
                    String Name = c.getString(Column1);
                    int Age = c.getInt(Column2);
                    Data = Name + "/" + Age + "\n";
                } while (c.moveToNext());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }



        findViewById(R.id.menubar).bringToFront();

        final FloatingActionButton menu = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        menu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (findViewById(R.id.menubar).getVisibility() == View.VISIBLE) {
                    View menubar;
                    menubar = findViewById(R.id.menubar);
                    createBottomUpAnimation(menubar, null, menubar.getHeight()).start();
                    menubar.bringToFront();

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Do something after 5s = 5000ms
                            findViewById(R.id.buttonBiologie).setVisibility(View.VISIBLE);
                            findViewById(R.id.buttonChimie).setVisibility(View.VISIBLE);
                            findViewById(R.id.menubar).setVisibility(View.INVISIBLE);
                        }
                    }, 300);

                    //NIGHt MODE

                } else {
                    findViewById(R.id.menubar).setVisibility(View.VISIBLE);
                    View menubar;
                    menubar = findViewById(R.id.menubar);
                    createTopDownAnimation(menubar, null, menubar.getHeight()).start();

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Do something after 5s = 5000ms
                            findViewById(R.id.buttonBiologie).setVisibility(View.INVISIBLE);
                            findViewById(R.id.buttonChimie).setVisibility(View.INVISIBLE);
                        }
                    }, 100);

                }
            }
        });


    }

    public void onSwitchClicked(View v) {

        //Is the switch on?
        boolean on = ((Switch) v).isChecked();

        if (on) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            recreate();
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            recreate();
        }
    }

    public boolean isNightModeEnabled() {
        return isNightModeEnabled;
    }

    public void setIsNightModeEnabled(boolean isNightModeEnabled) {
        this.isNightModeEnabled = isNightModeEnabled;
    }


    /**
     * Called when the user taps the Biologie button
     */
    public void buttonBiologie(View view) {

        boolean isChecked = ((CheckBox) findViewById(R.id.currentYear)).isChecked();
        boolean isCheckedAll = ((CheckBox) findViewById(R.id.lastYears)).isChecked();
        String year;

        if (isChecked == true && isCheckedAll == false) {
            year = "last";
        } else {
            year = "all";
        }

        if (isCheckedAll) {
            year = "all";
        }

        Intent intent = new Intent(this, RandomQuestion.class);
        String category = "biologie";
        intent.putExtra("category", category);
        intent.putExtra("year", year);
        startActivity(intent);

    }


    /**
     * Called when the user taps the Chimie button
     */
    public void buttonChimie(View view) {

        boolean isChecked = ((CheckBox) findViewById(R.id.currentYear)).isChecked();
        boolean isCheckedAll = ((CheckBox) findViewById(R.id.lastYears)).isChecked();
        String year;

        if (isChecked == true && isCheckedAll == false) {
            year = "last";
        } else {
            year = "all";
        }

        if (isCheckedAll) {
            year = "all";
        }

        Intent intent = new Intent(this, RandomQuestion.class);
        String category = "chimie";
        intent.putExtra("category", category);
        intent.putExtra("year", year);
        startActivity(intent);

    }

    private static ObjectAnimator createBottomUpAnimation(View view,
                                                          AnimatorListenerAdapter listener, float distance) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", -distance);
//        animator.setDuration(???)
        animator.removeAllListeners();
        if (listener != null) {
            animator.addListener(listener);
        }
        return animator;
    }

    public static ObjectAnimator createTopDownAnimation(View view, AnimatorListenerAdapter listener,
                                                        float distance) {
        view.setTranslationY(-distance);
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", 0);
        animator.removeAllListeners();
        if (listener != null) {
            animator.addListener(listener);
        }
        return animator;
    }

}

