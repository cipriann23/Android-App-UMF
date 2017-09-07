package com.example.nemto.umfadmitere;


import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.AppThemeDark);
        setContentView(R.layout.activity_main);

        findViewById(R.id.menubar).bringToFront();
        final FloatingActionButton menu = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        menu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(findViewById(R.id.menubar).getVisibility() == View.VISIBLE) {
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

                }
                else {
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

