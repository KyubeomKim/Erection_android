package com.cybil.study.erection.util;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitExService {
    String URL = "http://35.236.145.229:3000/api/";

    @GET("dashboard")
    Call<List<Dashboard>> getDashboardData();

    @GET("calculate")
    Call<List<Calculate>> getCalculateData();

    @GET("checkinit")
    Call<Data> getCheckInit();

    @FormUrlEncoded
    @POST("totalupdate")
    Call<Data> setTotalBalance(@FieldMap HashMap<String, Object> param);

    @FormUrlEncoded
    @POST("commissionupdate")
    Call<Data> setCommission(@FieldMap HashMap<String, Object> param);

    @FormUrlEncoded
    @POST("insert")
    Call<Data> setSeed(@FieldMap HashMap<String, Object> param);

    @FormUrlEncoded
    @POST("calculate")
    Call<Data> Calculate(@FieldMap HashMap<String, Object> param);
}
