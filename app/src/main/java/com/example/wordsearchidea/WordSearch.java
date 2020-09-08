package com.example.wordsearchidea;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class WordSearch extends AppCompatActivity {

    HashMap<String, ArrayList<Letter>> words = new HashMap<>();
    HashMap<TextView, Letter> letters = new HashMap<>();
    ArrayList<String> sets = new ArrayList<>();

    int numOn = 0;
    int numSolved = 0;
    int isWon = 0;

    String[][] puzzle = {
            {"", "", "", "", "", "", "", "", "", ""},
            {"", "", "", "", "", "", "", "", "", ""},
            {"", "", "", "", "", "", "", "", "", ""},
            {"", "", "", "", "", "", "", "", "", ""},
            {"", "", "", "", "", "", "", "", "", ""},
            {"", "", "", "", "", "", "", "", "", ""},
            {"", "", "", "", "", "", "", "", "", ""},
            {"", "", "", "", "", "", "", "", "", ""},
            {"", "", "", "", "", "", "", "", "", ""},
            {"", "", "", "", "", "", "", "", "", ""}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_search);

        final Animation fadeIn = new AlphaAnimation(0.0f, 1.0f);
        fadeIn.setDuration(2000);

        start();
        setReset();

        TableLayout layout = (TableLayout) findViewById(R.id.wordGrid);
        ImageButton resetB = (ImageButton) findViewById(R.id.resetBut);
        layout.startAnimation(fadeIn);
        resetB.startAnimation(fadeIn);
    }

    public void start() {
        resetAll();

        while (true) {
            boolean objC = setUpWord("OBJECTIVEC");
            boolean vari = setUpWord("VARIABLE");
            boolean mobi = setUpWord("MOBILE");
            boolean kotl = setUpWord("KOTLIN");
            boolean swft = setUpWord("SWIFT");
            boolean java = setUpWord("JAVA");

            if (objC && vari && mobi && kotl && swft && java) {
                break;
            } else {
                resetAll();
            }
        }

        setUpRandom(puzzle, letters);
        setTouch(letters);
    }

    public void resetAll() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                puzzle[i][j] = "";

                int resID = getResources().getIdentifier("textView" + i + j, "id", this.getPackageName());
                TextView view = ((TextView) findViewById(resID));

                view.setBackgroundColor(Color.TRANSPARENT);
            }
        }

        words.clear();
        letters.clear();
        sets.clear();
        numOn = 0;
        numSolved = 0;
        isWon = 0;

        for (int i = 0; i < 6; i++) {
            int wordID = getResources().getIdentifier("word" + i, "id", this.getPackageName());
            TextView wordView = ((TextView) findViewById(wordID));

            wordView.setTextColor(0xFF808080);
        }
    }

    public void setReset() {
        ImageButton search = (ImageButton)findViewById(R.id.resetBut);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setTouch(final HashMap<TextView, Letter> map) {
        for (final TextView text : map.keySet()) {
            text.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (map.get(text).isSolved() || win()){
                        return true;
                    }

                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            flickSwitch(map.get(text), text);
                            break;
                        case MotionEvent.ACTION_UP:
                            checkSolved();
                    }
                    return true;
                }
            });
        }
    }

    private boolean setUpWord(String word) {
        int index = 0;
        Random num = new Random();

        int len = word.length();
        int resID = 0;
        TextView textView = null;

        ArrayList<Letter> set = new ArrayList<>();

        String[] parts = word.split("");
        int orientation = num.nextInt(2);
        ; //1 = hor, 0 = ver
        int backwards = num.nextInt(2);
        ; //1 = backwards, 0 = forwards

        if (backwards > 0) {
            String[] rev = new String[len];
            for (int i = 0; i < len; i++) {
                rev[i] = parts[len - i - 1];
            }

            parts = rev;
        }

        while (index < 100) {
            int xCol = num.nextInt(10);
            int yRow = num.nextInt(10);

            switch (orientation) {
                case 0:
                    if (yRow + len <= 10 && checkEmpty(len, yRow, xCol, 0)) {
                        for (int i = 0; i < len; i++) {
                            int y = yRow + i;
                            resID = getResources().getIdentifier("textView" + y + xCol, "id", this.getPackageName());
                            textView = ((TextView) findViewById(resID));

                            letters.put(textView, new wordLetter(parts[i], xCol, y));
                            set.add(letters.get(textView));
                            textView.setText(letters.get(textView).getLetter());
                            //textView.setBackgroundColor(Color.RED);
                            puzzle[y][xCol] = letters.get(textView).getLetter();
                        }

                        words.put(word, set);
                        sets.add(word);
                        return true;
                    }
                case 1:
                    if (xCol + len <= 10 && checkEmpty(len, yRow, xCol, 1)) {
                        for (int i = 0; i < len; i++) {
                            int x = xCol + i;
                            resID = getResources().getIdentifier("textView" + yRow + x, "id", this.getPackageName());
                            textView = ((TextView) findViewById(resID));

                            letters.put(textView, new wordLetter(parts[i], x, yRow));
                            set.add(letters.get(textView));
                            textView.setText(letters.get(textView).getLetter());
                            //textView.setBackgroundColor(Color.RED);
                            puzzle[yRow][x] = letters.get(textView).getLetter();
                        }

                        words.put(word, set);
                        sets.add(word);
                        return true;
                    }
                default:
                    index++;
            }
        }

        return false;
    }

    private boolean checkEmpty(int len, int y, int x, int orientation) { // 0 = ver, 1 = hor
        switch (orientation) {
            case 0:
                for (int i = 0; i < len; i++) {
                    if (!(puzzle[y + i][x].isEmpty())) {
                        return false;
                    }
                }

                return true;
            case 1:
                for (int i = 0; i < len; i++) {
                    if (!(puzzle[y][x + i].isEmpty())) {
                        return false;
                    }
                }

                return true;
            default:
                return false;
        }
    }

    private void setUpRandom(String[][] array, HashMap<TextView, Letter> map) {
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                int resID = getResources().getIdentifier("textView" + y + x, "id", this.getPackageName());
                TextView textView = ((TextView) findViewById(resID));

                if (array[y][x].isEmpty()) {
                    map.put(textView, new randLetter(x, y));
                    array[y][x] = map.get(textView).getLetter();
                    textView.setText(map.get(textView).getLetter());
                    textView.setBackgroundColor(Color.TRANSPARENT);
                }
            }
        }

        String[] words = {"Objective C", "Variable", "Mobile", "Kotlin", "Swift", "Java"};
        for (int i = 0; i < 6; i++) {
            int wordID = getResources().getIdentifier("word" + i, "id", this.getPackageName());
            TextView wordView = ((TextView) findViewById(wordID));

            wordView.setText(words[i]);
        }
    }

    private void flickSwitch(Letter letter, TextView text) {
        final Animation fade = new AlphaAnimation(0.0f, 1.0f);
        fade.setDuration(500);

        if (letter.isOn()) {
            letter.turnOff();
            text.setBackgroundColor(Color.TRANSPARENT);
            text.startAnimation(fade);
            numOn--;
            return;
        }

        numOn++;
        checkOnStatus(text);
        letter.turnOn();
        text.setBackgroundColor(0xffcbc7fc);
        text.startAnimation(fade);
    }

    private void checkOnStatus(TextView text) {
        if (numOn > 10) {
            shutAllExcept(text);
            return;
        }

        int adjCount = 0;
        int resID = 0;
        TextView view = null;

        int yRow = letters.get(text).getyRow();
        int xCol = letters.get(text).getxCol();

        //moving up
        for (int y = yRow - 1; y >= 0; y--) {
            resID = getResources().getIdentifier("textView" + y + xCol, "id", this.getPackageName());
            view = ((TextView) findViewById(resID));

            if (letters.get(view).isOn()) {
                adjCount++;
            } else {
                break;
            }
        }

        //moving down
        for (int y = yRow + 1; y < 10; y++) {
            resID = getResources().getIdentifier("textView" + y + xCol, "id", this.getPackageName());
            view = ((TextView) findViewById(resID));

            if (letters.get(view).isOn()) {
                adjCount++;
            } else {
                break;
            }
        }

        //moving left
        for (int x = xCol - 1; x >= 0; x--) {
            resID = getResources().getIdentifier("textView" + yRow + x, "id", this.getPackageName());
            view = ((TextView) findViewById(resID));

            if (letters.get(view).isOn()) {
                adjCount++;
            } else {
                break;
            }
        }

        //moving right
        for (int x = xCol + 1; x < 10; x++) {
            resID = getResources().getIdentifier("textView" + yRow + x, "id", this.getPackageName());
            view = ((TextView) findViewById(resID));

            if (letters.get(view).isOn()) {
                adjCount++;
            } else {
                break;
            }
        }

        if (adjCount != numOn - 1) {
            shutAllExcept(text);
        }
    }

    private void shutAllExcept(TextView text) {
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                int resID = getResources().getIdentifier("textView" + y + x, "id", this.getPackageName());
                TextView view = ((TextView) findViewById(resID));

                if (!text.equals(view) && letters.get(view).isOn()) {
                    letters.get(view).turnOff();
                    view.setBackgroundColor(Color.TRANSPARENT);
                    numOn--;
                }
            }
        }
    }

    private void shutAll() {
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                int resID = getResources().getIdentifier("textView" + y + x, "id", this.getPackageName());
                TextView view = ((TextView) findViewById(resID));

                if (letters.get(view).isOn()) {
                    letters.get(view).turnOff();
                    view.setBackgroundColor(Color.TRANSPARENT);
                }
            }
        }

        numOn = 0;
    }

    private void checkSolved() {
        final Animation fade = new AlphaAnimation(0.0f, 1.0f);
        fade.setDuration(500);

        for (String word: words.keySet()) {
            boolean solved = true;

            for (Letter letter: words.get(word)) {
                if (word.length() != numOn) {
                    solved = false;
                    break;
                }

                if (letter.isOn()) {
                    continue;
                } else {
                    solved = false;
                    break;
                }
            }

            if (solved) {
                for (Letter letter : words.get(word)) {
                    letter.solveOn();

                    int resID = getResources().getIdentifier("textView" + letter.getyRow()
                            + letter.getxCol(), "id", this.getPackageName());
                    TextView view = ((TextView) findViewById(resID));

                    view.setBackgroundColor(0xffc8fcc7);
                    view.startAnimation(fade);
                }

                int index = sets.indexOf(word);
                int wordID = getResources().getIdentifier("word" + index, "id", this.getPackageName());
                TextView wordView = ((TextView) findViewById(wordID));
                wordView.setTextColor(Color.GREEN);

                numSolved++;
                shutAll();

            }
        }

        if(win()) {
            hasWon();
        }
    }

    public boolean win() {
        if (numSolved == 6) {
            return true;
        }

        return false;
    }

    public void hasWon() {
        final Animation fadeIn = new AlphaAnimation(0.0f, 1.0f);
        fadeIn.setDuration(3000);

        if (isWon == 0) {
            TextView win = (TextView) findViewById(R.id.winText);
            win.setText("S O L V E D !");
            win.startAnimation(fadeIn);
            isWon = 1;
        }
    }
}