package com.example.tewodros_2.maharashi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Tewodros_2 on 12/2/2015.
 */
public class GameView extends SurfaceView {

    private SurfaceHolder holder;
    private Bitmap Maharishi;
    private GameThread gthread = null;
    private  float mahx = -205.0f;
    private  float mahy = 100.0f;
    private Bitmap flower;
    private float flowerX = -50.0f;
    private float flowerY = -101.0f;
    private boolean flowerActive = false;
    private int score = 0;
    private Paint scorePaint;
    private Bitmap test;


    public GameView(Context context) {

        super(context);

        holder = getHolder();

        holder.addCallback( new SurfaceHolder.Callback(){
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                Maharishi = BitmapFactory.decodeResource(getResources(), R.drawable.maharishi_mahesh_yogi);
                flower = BitmapFactory.decodeResource(getResources(),R.drawable.rose50);

             //   test = Bitmap.createScaledBitmap(Maharishi, 102, 125, false);

                scorePaint = new Paint();
                scorePaint.setColor(Color.BLACK);
                scorePaint.setTextSize(50.0f);

                makeThread();
                gthread.setRunning(true);
                gthread.start();

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        } );


    }
    public void makeThread()
    {
        gthread = new GameThread(this);

    }
    public int getScore()
    {
        return score;
    }

    public void setScore(int scoreIn)
    {
        score = scoreIn;
    }

    public void killThread()
    {
        boolean retry = true;
        gthread.setRunning(false);
        while(retry)
        {
            try
            {
                gthread.join();
                retry = false;
            }
            catch (InterruptedException e)
            {
            }
        }
    }
    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.drawColor(Color.WHITE);

        canvas.drawText("successfull shoot: " + String.valueOf(score), 10.0f, 50.0f, scorePaint);

        if(flowerActive)
        {
            flowerY = flowerY - 5; // flower travels up the screen 5 pixels per redraw
            if ( flowerY < 325 ) // if the flower goes beyond the bottom of the Maharishi graphic by 25 pixels
            {
                flowerX = -50.0f; // park the flower
                flowerY = -101.0f; // and
                flowerActive = false; // Turn off flower drawing
            }
            else // otherwise draw the flower in its new position
            {
                canvas.drawBitmap(flower, flowerX, flowerY, null);
            }
        }
        mahx += 2;
        if (mahx > getWidth()) mahx = -205;
        canvas.drawBitmap(Maharishi, mahx, mahy, null);
      //  canvas.drawBitmap(test, 200, 200, null);

        if ( flowerX >= mahx && flowerX <= mahx + Maharishi.getWidth()
                && flowerY <= mahy + Maharishi.getHeight() && flowerY >= mahy + Maharishi.getHeight() - 25.0f )
        {
            score++;
            flowerActive = false;
            flowerX = -50.0f;
            flowerY = -101.0f;
        }
    }
    public void giveFlower()
    {
        if(!flowerActive) {

            flowerActive = true;
            flowerX = getWidth() / 2.0f - flower.getWidth() / 2;
            flowerY = getHeight() - flower.getHeight() - 25;
        }

    }
    public void onDestroy()
    {
        Maharishi.recycle();
        Maharishi = null;
        flower.recycle();
        flower = null;
        System.gc();
    }
}
