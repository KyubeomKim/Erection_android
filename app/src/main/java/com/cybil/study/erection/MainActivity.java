package com.cybil.study.erection;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.cybil.study.erection.util.Dashboard;
import com.cybil.study.erection.util.RetrofitExService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.vp_pager);

        Fragment[] arrFragments = new Fragment[3];
        arrFragments[0] = new DashboardFragment();
        arrFragments[1] = new CalculateFragment();
        arrFragments[2] = new YellowFragment();

        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(), arrFragments);
        viewPager.setAdapter(adapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitExService.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitExService retrofitExService = retrofit.create(RetrofitExService.class);

        retrofitExService.getDashboardData().enqueue(new Callback<List<Dashboard>>() {
            @Override
            public void onResponse(@NonNull Call<List<Dashboard>> call, @NonNull Response<List<Dashboard>> response) {
                Log.d("kyubeom", "good");
                if (response.isSuccessful()) {
                    List<Dashboard> body =  response.body();
                    if (body != null) {
                        Log.d("kyubeom", body.toString());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Dashboard>> call, @NonNull Throwable t) {
                Log.d("kyubeom", "fail");
            }
        });

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
