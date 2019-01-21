package com.cybil.study.erection.util;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitExService {
    String URL = "http://35.236.145.229:3000/api/";

    @GET("dashboard")
    Call<List<Dashboard>> getDashboardData();

    @GET("calculate")
    Call<List<Calculate>> getCalculateData();
}
