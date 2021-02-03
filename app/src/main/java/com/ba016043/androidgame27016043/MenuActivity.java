package com.ba016043.androidgame27016043;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MenuActivity extends AppCompatActivity {
    /**
     * Create a new thread
     * MenuActivity to appear for 5 seconds
     * Then go to MainActivity class
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Thread thread = new Thread() {      // New thread

            /**
             * Thread will make MenuActivity to appear for 5 seconds
             * Then go to MainActivity class
             */
            @Override
            public void run() {
                try {
                    sleep(5000);    // MenuActivity to appear for 5 seconds
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                    Intent goToMainAvtivity = new Intent(MenuActivity.this, MainActivity.class);  // Set location to MainActivity after the 5 seconds
                    startActivity(goToMainAvtivity);
                }
            }
        };
        thread.start();     // Start thread
    }

    /**
     * Pause
     */
    @Override
    protected void onPause() {
        super.onPause();
        finish();   // finish
    }
}
