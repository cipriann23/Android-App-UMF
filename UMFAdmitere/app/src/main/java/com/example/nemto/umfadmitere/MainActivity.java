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
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "App";
    private boolean isNightModeEnabled = false;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {


        String theme;
        if (AppCompatDelegate.getDefaultNightMode()
                == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.AppThemeDark);
        } else {
            setTheme(R.style.AppTheme);
        }

        super.onCreate(savedInstanceState);


        //setTheme(R.style.AppThemeDark);
        setContentView(R.layout.activity_main);

        findViewById(R.id.menubar).bringToFront();

        final String[] items = new String[]{"BIOLOGIE", "CHIMIE", "FIZICA"};
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);

        ListView listView = (ListView) findViewById(R.id.categorylist);
        listView.setAdapter(itemsAdapter);

        final String[] BIOLOGIE = new String[]{"Metabolism", "Analizatorii", "Celula si tesuturile", "BIOLOGIE4"};
        final String[] CHIMIE = new String[]{"CHIMIE1", "CHIMIE2", "CHIMIE3", "CHIMIE4"};
        final String[] FIZICA = new String[]{"FIZICA1", "FIZICA2", "FIZICA3", "FIZICA4"};

        final ArrayAdapter<String> itemsAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, BIOLOGIE);
        final ArrayAdapter<String> itemsAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, CHIMIE);
        final ArrayAdapter<String> itemsAdapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, FIZICA);

        final FloatingActionButton menu = (FloatingActionButton) findViewById(R.id.floatingActionButton);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {

                        ListView listView2 = (ListView) findViewById(R.id.subcategory_list);
                        if (Objects.equals(items[position], "BIOLOGIE")) {
                            listView2.setAdapter(itemsAdapter2);
                            listView2.setOnItemClickListener(
                                    new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
                                            getQuestion(BIOLOGIE[position]);
                                        }
                                    }
                            );
                        }
                        if (Objects.equals(items[position], "CHIMIE")){
                            listView2.setAdapter(itemsAdapter3);
                            listView2.setOnItemClickListener(
                                    new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
                                            getQuestion(CHIMIE[position]);
                                        }
                                    }
                            );
                        }
                        if (Objects.equals(items[position], "FIZICA")){
                            listView2.setAdapter(itemsAdapter4);
                            listView2.setOnItemClickListener(
                                    new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
                                            getQuestion(FIZICA[position]);
                                        }
                                    }
                            );
                        }


                        menu.performClick();
                    }
                }
        );




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
                            findViewById(R.id.subcategory_list).setVisibility(View.VISIBLE);
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
                            findViewById(R.id.subcategory_list).setVisibility(View.INVISIBLE);
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
    public void getQuestion(String category) {

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
        //category = "metabolism";
        Intent intent = new Intent(this, RandomQuestion.class);
        intent.putExtra("category", category);
        intent.putExtra("year", year);

        String theme_set;
        if (AppCompatDelegate.getDefaultNightMode()
                == AppCompatDelegate.MODE_NIGHT_YES) {
            theme_set = "dark";
        } else {
            theme_set = "light";
        }
        intent.putExtra("theme", theme_set);

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

