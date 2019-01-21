package com.cybil.study.erection.util;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public class ErectionCall {

    @SerializedName("dashboard")
    Dashboard dashboard;

    @SerializedName("calculate")
    Calculate calculate;

    public class Dashboard {

        public List<DashboardInfo> params = new ArrayList<>();
        public List<DashboardInfo> getDashboard() {return params;}

        public class DashboardInfo {
            @SerializedName("name") String name;
            public String getName() {return name;}

            @SerializedName("seed") int seed;
            public int getSeed() {return seed;}

            @SerializedName("rate") int rate;
            public int getRate() {return rate;}

            @SerializedName("balance") int balance;
            public int getBalance() {return balance;}

            @SerializedName("profit") int profit;
            public int getProfit() {return balance;}

            @SerializedName("totalProfit") int totalProfit;
            public int getTotalProfit() {return balance;}
        }
    }

    public class Calculate {

        public List<CalculateInfo> params = new ArrayList<>();
        public List<CalculateInfo> getHourly() {return params;}

        public class CalculateInfo {
            @SerializedName("name") String name;
            public String getName() {return name;}

            @SerializedName("money") int money;
            public int getMoney() {return money;}

            @SerializedName("difference") int difference;
            public int getDifference() {return difference;}
        }
    }

    public Dashboard getDashboard() {return dashboard;}
    public Calculate getCalculate() {return calculate;}

    public interface ErectionApiInterface {

        String URL = "http://35.236.145.229:3000/api/";

        @GET("dashboard")
        Call<Dashboard> getDashboardData();

        @GET("calculate")
        Call<Dashboard> getCalculateData();

    }
}
