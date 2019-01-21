package com.cybil.study.erection.util;

import com.google.gson.annotations.SerializedName;

public class Calculate {
    private final String name;
    private final float money;
    private final float difference;

    public Calculate(String name, float money, float difference) {
        this.name = name;
        this.money = money;
        this.difference = difference;
    }

    public String getName() {return name;}
    public float getMoney() {return money;}
    public float getDifference() {return difference;}
}
