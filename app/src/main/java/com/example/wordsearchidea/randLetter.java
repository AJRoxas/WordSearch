package com.example.wordsearchidea;

import java.util.Random;

public class randLetter implements Letter {
    private String letter;
    private int xCol;
    private int yRow;
    private boolean on;

    public randLetter(int x, int y) {
        Random random = new Random();
        int rand = random.nextInt(26) + 65;

        this.letter = Character.toString((char) rand);
        this.xCol = x;
        this.yRow = y;
        this.on = false;
    }

    @Override
    public String getLetter() {
        return this.letter;
    }

    @Override
    public int getxCol() {
        return this.xCol;
    }

    @Override
    public int getyRow() {
        return this.yRow;
    }

    @Override
    public boolean isOn() {
        return on;
    }

    @Override
    public boolean isSolved() {
        return false;
    }

    @Override
    public void turnOn() {
        on = true;
    }

    @Override
    public void turnOff() {
        on = false;
    }

    @Override
    public void solveOn() {
        return;
    }
}
