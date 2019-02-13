package com.cybil.study.erection;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.cybil.study.erection.util.Dashboard;
import com.cybil.study.erection.util.Data;
import com.cybil.study.erection.util.RetrofitExService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    String TAG = "kyubeom";
    ImageView iv;
    RelativeLayout mainLayout;

    ViewPager viewPager;
    MyPagerAdapter adapter;

    RetrofitExService retrofitExService;
    Retrofit retrofit;

    public void getCheckInit() {
        retrofitExService.getCheckInit().enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if(response.body().getResult()) {
                    viewPager.setAdapter(adapter);
                } else {
                    HashMap<String, Object> payload = new HashMap<>();
                    SimpleDateFormat s = new SimpleDateFormat("yy-MM-dd hh-mm");
                    payload.put("filename", s.format(new Date()));
                    initData(payload);
                }
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {

            }
        });
    }
    public void initData(HashMap<String, Object> payload) {
        retrofitExService.initData(payload).enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                viewPager.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Log.d(TAG, "failed");
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainLayout = findViewById(R.id.main_layout);

        viewPager = findViewById(R.id.vp_pager);

        final Fragment[] arrFragments = new Fragment[3];
        arrFragments[0] = new DashboardFragment();
        arrFragments[1] = new CalculateFragment();
        arrFragments[2] = new YellowFragment();

        adapter = new MyPagerAdapter(getSupportFragmentManager(), arrFragments);

        retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitExService.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitExService = retrofit.create(RetrofitExService.class);
        getCheckInit();

    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        private Fragment[] arrFragments;
        public MyPagerAdapter(FragmentManager fm, Fragment[] arrFragments) {
            super(fm);
            this.arrFragments = arrFragments;
        }

        @Override
        public Fragment getItem(int position) {
            return arrFragments[position];
        }

        @Override
        public int getCount() {
            return arrFragments.length;
        }
    }
}
