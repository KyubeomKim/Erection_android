package com.cybil.study.erection.util;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class Report {
    private final String date;
    private final String[] fileList;
    private final TotalSeed totalSeed;
    private final TotalProfit totalProfit;

    public Report(String date, String[] fileList, TotalSeed totalSeed, TotalProfit totalProfit) {
        this.date = date;
        this.fileList = fileList;
        this.totalSeed = totalSeed;
        this.totalProfit = totalProfit;
    }

    public class TotalSeed {
        private float player0;
        private float player1;
        private float player2;

        public TotalSeed(float player0, float player1, float player2) {
            this.player0 = player0;
            this.player1 = player1;
            this.player2 = player2;
        }

        public float getPlayer0() {
            return player0;
        }

        public float getPlayer1() {
            return player1;
        }

        public float getPlayer2() {
            return player2;
        }
    }

    public class TotalProfit {
        private float player0;
        private float player1;
        private float player2;

        public TotalProfit(float player0, float player1, float player2) {
            this.player0 = player0;
            this.player1 = player1;
            this.player2 = player2;
        }

        public float getPlayer0() {
            return player0;
        }

        public float getPlayer1() {
            return player1;
        }

        public float getPlayer2() {
            return player2;
        }
    }

    public String getDate() {return date;}
    public String[] getFileList() {return fileList;}
    public TotalSeed getTotalSeed() {return totalSeed;}
    public TotalProfit getTotalProfit() {return totalProfit;}
}
