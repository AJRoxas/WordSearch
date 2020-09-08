package com.example.wordsearchidea;

public interface Letter {

    String getLetter();
    int getxCol();
    int getyRow();

    boolean isOn(); //checks if it is higlighted
    boolean isSolved(); //checks if it is a solved letter
    void turnOn();
    void turnOff();
    void solveOn();
}
