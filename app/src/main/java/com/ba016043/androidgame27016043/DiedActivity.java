package com.ba016043.androidgame27016043;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DiedActivity extends AppCompatActivity {

    private Button PlayAgain;       // Declare button
    private TextView DisplayScore;      // declare text for the score
    private String Score;       // delcare total score as string

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_died);

        Score = getIntent().getExtras().get("Score : ").toString();     // get from mario view, total score and display

        PlayAgain = (Button) findViewById(R.id.playAgain);      // button to play again

        DisplayScore = (TextView) findViewById(R.id.displayScore);      // display score text

        PlayAgain.setOnClickListener(new View.OnClickListener() {

            /**
             * When buttons is clicked an intent is used to go from DiedActivity to MainActivity
             */
            @Override
            public void onClick(View v) {
                Intent goToMainAvtivity = new Intent(DiedActivity.this, MainActivity.class);  // Set location to MainActivity after play again button is clicked
                startActivity(goToMainAvtivity);
            }
        });

        DisplayScore.setText("Score : " + Score);   // display score text and total score
    }
}
