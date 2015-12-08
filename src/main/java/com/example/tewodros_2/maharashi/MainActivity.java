package com.example.tewodros_2.maharashi;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class MainActivity extends Activity implements View.OnClickListener
 {

    private GameView gv;
    private Button flowerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        gv = new GameView(this);

        flowerButton = new Button(this);
        flowerButton.setWidth(350);
        flowerButton.setHeight(100);
        flowerButton.setBackgroundColor(Color.LTGRAY);
        flowerButton.setTextColor(Color.RED);
        flowerButton.setTextSize(20);
        flowerButton.setText("Give Flower");
        flowerButton.setOnClickListener(this);
        flowerButton.setGravity(Gravity.CENTER);

        FrameLayout GameLayout = new FrameLayout(this);

        LinearLayout ButtonLayout = new LinearLayout (this);
        ButtonLayout.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);

        ButtonLayout.addView(flowerButton);

        GameLayout.addView(gv);
        GameLayout.addView(ButtonLayout);

        setContentView(GameLayout);
           }
     @Override
     protected void onResume()
     {
         SharedPreferences prefs = getSharedPreferences("GopherPokeData",MODE_PRIVATE);
         String retrievedHighScore = prefs.getString("GopherPokeScore", "1");

         gv.setScore( Integer.valueOf(retrievedHighScore) );

         super.onResume();
     }
    @Override
    protected void onPause()
    {
        SharedPreferences prefs = getSharedPreferences("GopherPokeData", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        String tempscore=Integer.toString( gv.getScore() );
        editor.putString("GopherPokeScore", tempscore);

        editor.commit();
        super.onPause();
        gv.killThread(); //Notice this reaches into the GameView object and runs the killThread mehtod.
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        gv.onDestroy();
    }

     @Override
     public void onClick(View v) {
         gv.giveFlower();
     }
 }
