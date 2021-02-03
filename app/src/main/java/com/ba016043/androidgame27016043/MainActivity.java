package com.ba016043.androidgame27016043;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private MarioView gameView;     // Declare gameView from MarioView
    private Handler handler = new Handler();        // handler for multithreading
    private final static long Interval = 30;    // interval

    /**
     * Display content from MarioView using gameView
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameView = new MarioView(this);
        setContentView(gameView);       // Display gameView

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            /**
             * Run Runnable
             */
            @Override
            public void run() {
                handler.post(new Runnable() {

                    /**
                     * Redraw and display on screen
                     */
                    @Override
                    public void run() {
                        gameView.invalidate();
                    }
                });
            }
        }, 0, Interval);
    }
}
