package com.example.chapter3.homework;

import android.util.Log;

import java.util.ArrayList;

public class Friend {
    private String name;
    private int imageNumber;

    public Friend(String name, int imageNumber) {
        this.name = name;
        this.imageNumber = imageNumber;
    }

    public String getName() {
        return name;
    }

    public int getImageNumber() {
        return imageNumber;
    }

}
