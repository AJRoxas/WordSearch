package com.example.wordsearchidea;

public class wordLetter implements Letter {
    private String letter;
    private int xCol;
    private int yRow;
    private boolean on;
    private boolean solved;

    public wordLetter(String letter, int x, int y) {
        this.letter = letter;
        this.xCol = x;
        this.yRow = y;
        this.on = false;
        this.solved = false;
    }

    @Override
    public void solveOn() {
        on = false;
        solved = true;
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
        return solved;
    }

    @Override
    public void turnOn() {
        if (solved) {
            return;
        }

        on = true;
    }

    @Override
    public void turnOff() {
        on = false;
    }
}
