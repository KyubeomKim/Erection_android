package com.cybil.study.erection.util;

import com.google.gson.annotations.SerializedName;

public class Dashboard {
    private final String name;
    private final float seed;
    private final float rate;
    private final float balance;
    private final float profit;
    private final float totalProfit;

    public Dashboard(String name, float seed, float rate, float balance, float profit, float totalProfit) {
        this.name = name;
        this.seed = seed;
        this.rate = rate;
        this.balance = balance;
        this.profit = profit;
        this.totalProfit = totalProfit;
    }

    public String getName() {return name;}
    public float getSeed() {return seed;}
    public float getRate() {return rate;}
    public float getBalance() {return balance;}
    public float getProfit() {return balance;}
    public float getTotalProfit() {return balance;}
}
