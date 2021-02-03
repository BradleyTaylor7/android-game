package com.ba016043.androidgame27016043;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class MarioView extends View {

    private Bitmap mario[] = new Bitmap[2];   // Declare bitmap image for mario1
    private int marioX = 10;    // Declare mario x axis set to 10
    private int marioY;     // Declare mario y axis
    private int marioSpeed; // Declare mario speed
    private int canvasWidth, canvasHeight;      // Declare canvas width and height

    private int level;      // Declare level
    private Paint levelText = new Paint();      // Declare level text

    private int coinX, coinY, coinSpeed = 16;       // Declare coin x, y axis and speed set to 16
    private Bitmap coin;    // Declare image of coin

    private int mushroomX, mushroomY, mushroomSpeed = 20;       // Declare mushroom x, y axis and speed set to 20
    private Bitmap mushroom;    // Declare image of mushroom

    private int bulletX, bulletY, bulletSpeed;      // Declare bullet x, y axis and speed
    private Bitmap bullet;      // Declare image of bullet

    private boolean touch = false;      // Delcare touch as currently false

    private Bitmap background;  // Declare bitmap image for world

    private Paint scoreText = new Paint();      // Declare score text
    private int score, livesCounter;        // Declare score and lives counter

    private Bitmap lives[] = new Bitmap[2];     // Declare images of lives using arrary

    /**
     * Set bitmap variables to images in drawable folder
     * Format text variables
     */
    public MarioView(Context context) {
        super(context);

        mario[0] = BitmapFactory.decodeResource(getResources(), R.drawable.mario1);    // Get mario1.png from drawable folder to be mario[0]
        mario[1] = BitmapFactory.decodeResource(getResources(), R.drawable.mario2);    // Get mario2.png from drawable folder to be mario[1]

        background = BitmapFactory.decodeResource(getResources(), R.drawable.worldmain);    // Get world.png from drawable folder to be background

        coin = BitmapFactory.decodeResource(getResources(), R.drawable.coin);       // Get coin.png from drawable folder to be coin
        mushroom = BitmapFactory.decodeResource(getResources(), R.drawable.mushroom);   // Get mushroom.png from drawable folder to be mushroom
        bullet = BitmapFactory.decodeResource(getResources(), R.drawable.bullet);   // Get bullet.png from drawable folder to be bullet

        scoreText.setColor(Color.BLACK);    // Set score text colour
        scoreText.setTextSize(70);      // Set score text size
        scoreText.setTypeface(Typeface.DEFAULT_BOLD);   // Set score text format
        scoreText.setAntiAlias(true);       // Antialiasing to keep text smooth

        levelText.setColor(Color.BLACK);    // Set level text colour
        levelText.setTextSize(70);      // Set level text size
        levelText.setTypeface(Typeface.DEFAULT_BOLD);   // Set level text format
        levelText.setAntiAlias(true);       // Antialiasing to keep text smooth

        lives[0] = BitmapFactory.decodeResource(getResources(), R.drawable.heart);     // Get hearts.png from drawable folder to be lives[0]
        lives[1] = BitmapFactory.decodeResource(getResources(), R.drawable.heart_grey);     // Get heart_grey.png.png from drawable folder to be lives[1]

        marioY = 550;

        score = 0;

        livesCounter = 3;
    }

    /**
     * Draw on the canvas
     * Set scores for coin and mushroom
     * Set minus points for bullet
     * Display lives
     * Display levels
     * Display score
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();

        canvas.drawBitmap(background, 0, 0, null);      // Draw background

        int minMarioY = mario[0].getHeight();
        int maxMarioY = canvasHeight - mario[0].getHeight() * 2;
        marioY = marioY + marioSpeed;

        if (marioY < minMarioY) {       // mario min y axis
            marioY = minMarioY;
        }

        if (marioY > maxMarioY) {       // mario max y axis
            marioY = maxMarioY;
        }

        marioSpeed = marioSpeed + 2;

        if (touch) {
            canvas.drawBitmap(mario[1], marioX, marioY, null);      // Draw mario2.png
            touch = false;      // touch variable goes back to false
        } else {
            canvas.drawBitmap(mario[0], marioX, marioY, null);      // Draw mario1.png
        }

        coinX = coinX - coinSpeed;

        if (hitGoodsCheck(coinX, coinY)) {      // if mario hits a coin
            score = score + 10;     // add 10 to score
            coinX = -100;
        }

        if (coinX < 0) {
            coinX = canvasWidth + 21;
            coinY = (int) Math.floor(Math.random() * (maxMarioY - minMarioY)) + minMarioY;
        }
        canvas.drawBitmap(coin, coinX, coinY, null);        // draw coin image on canvas

        mushroomX = mushroomX - mushroomSpeed;

        if (hitGoodsCheck(mushroomX, mushroomY)) {      // if mario hits a mushroom
            score = score + 20;     // add 20 to score
            mushroomX = -100;
        }

        if (mushroomX < 0) {
            mushroomX = canvasWidth + 21;
            mushroomY = (int) Math.floor(Math.random() * (maxMarioY - minMarioY)) + minMarioY;
        }
        canvas.drawBitmap(mushroom, mushroomX, mushroomY, null);        // draw mushroom image on canvas

        bulletX = bulletX - bulletSpeed;

        if (hitGoodsCheck(bulletX, bulletY)) {      // if mario hits a bullet
            bulletX = -100;
            livesCounter--;     // lives counter goes down

            if (livesCounter == 0) {        // if lives counter reached 0 then
                Toast.makeText(getContext(), "You Died", Toast.LENGTH_SHORT).show();    // display you died
                Intent diedIntent = new Intent(getContext(), DiedActivity.class);       // intent to go to DiedActivity
                diedIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                diedIntent.putExtra("Score : ", score);     // add score to DiedActivity
                getContext().startActivity(diedIntent);
            }
        }

        if (bulletX < 0) {
            bulletX = canvasWidth + 21;     // bullet x axis set to canvas width + 21
            bulletY = (int) Math.floor(Math.random() * (maxMarioY - minMarioY)) + minMarioY;    // random y axis for bullet
        }
        canvas.drawBitmap(bullet, bulletX, bulletY, null);      // draw bullet image on canvas

        canvas.drawText("Score: " + score, 20, 100, scoreText);      // Draw score

        if (level < 4) {        // if level is less than 4
            canvas.drawText("Level: " + level, 400, 100, levelText);    // draw level text with level variable
        } else {
            canvas.drawText("Level: Max", 400, 100, levelText);     // draw level text saying level: Max
        }

        if (score >= 0 && score < 49) {     // if score is between 0 and 49
            level = 1;      // level becomes 1
            bulletSpeed = 20;   // bullet speed becomes 20
        }
        if (score > 49 && score < 99) {     // if score is between 49 and 99
            level = 2;      // level becomes 2
            bulletSpeed = 25;       // bullet speed becomes 25
        }
        if (score > 99 && score < 149) {    // if score is between 99 and 149
            level = 3;      // level becomes 3
            bulletSpeed = 30;       // bullet speed becomes 30
        }
        if (score > 149) {      // if score is greater than 149
            level = 4;      // level becomes Max
            bulletSpeed = 35;       // bullet speed becomes 35
        }

        for (int i = 0; i < 3; i++) {
            int x = 780 + lives[0].getWidth() * 1 * i;      // placing the lives on x axis of screen
            int y = 35;     // placing lives on y axis of screen

            if (i < livesCounter) {         // if the number of lives is less than livescounter
                canvas.drawBitmap(lives[0], x, y, null);    // set to heart (heart is a life)
            } else {
                canvas.drawBitmap(lives[1], x, y, null);    // else set to heart grey (used a life)
            }
        }
    }

    /**
     * Check to see if mario has hit any goods
     * Goods = coin and mushroom
     */
    public boolean hitGoodsCheck(int x, int y) {
        if (marioX < x && x < (marioX + mario[0].getWidth()) && marioY < y && y < (marioY + mario[0].getHeight())) {
            return true;
        }
        return false;
    }

    /**
     * This allows the user to touch the screen to make mario move
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            touch = true;       // touch goes from false to true
            marioSpeed = -22;       // mario speed becomes -22
        }
        return true;
    }
}
