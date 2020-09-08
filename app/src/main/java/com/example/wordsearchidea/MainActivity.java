package com.example.wordsearchidea;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Animation fadeIn = new AlphaAnimation(0.0f, 1.0f);
        fadeIn.setDuration(3000);

        final Animation fadeStart = new AlphaAnimation(0.0f, 1.0f);
        fadeStart.setDuration(6000);

        TextView word = (TextView) findViewById(R.id.introWord);
        TextView search = (TextView) findViewById(R.id.introSearch);
        TextView info = (TextView) findViewById(R.id.information);
        ImageButton button = (ImageButton) findViewById(R.id.startBut);
        
        word.startAnimation(fadeIn);
        search.startAnimation(fadeIn);
        info.startAnimation(fadeIn);
        button.startAnimation(fadeStart);

    }

    public void click(View view) {
        Intent startGame = new Intent(this, WordSearch.class);
        startActivity(startGame);
    }


    /*
    public void intro() {
        String[] titleWord = {"W", "O", "R", "D"};
        String[] titleSearch = {"S", "E", "A", "R", "C", "H"};
        int resID = 0;
        TextView view = null;

        final Animation fadeIn = new AlphaAnimation(0.0f, 1.0f);
        fadeIn.setDuration(2000);

        final Animation fadeOut = new AlphaAnimation(1.0f, 0.0f);
        fadeOut.setDuration(2000);

        for (int x = 3; x < 7; x++) {

            resID = getResources().getIdentifier("textView" + 4 + x, "id", this.getPackageName());
            view = ((TextView) findViewById(resID));
            view.setBackgroundColor(Color.TRANSPARENT);
            view.setText(titleWord[x - 3]);
            view.startAnimation(fadeIn);
        }

        for (int x = 2; x < 8; x++) {

            resID = getResources().getIdentifier("textView" + 5 + x, "id", this.getPackageName());
            view = ((TextView) findViewById(resID));
            view.setBackgroundColor(Color.TRANSPARENT);
            view.setText(titleSearch[x - 2]);
            view.startAnimation(fadeIn);
        }
    }

     */

}