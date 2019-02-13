package com.cybil.study.erection;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cybil.study.erection.util.Calculate;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class CalculateFragment extends Fragment {
    String TAG="kyubeom";
    TextView kyubeomMoney;
    TextView seongsuMoney;
    TextView zzangsuMoney;

    EditText kyubeomRealMoney;
    EditText seongsuRealMoney;
    EditText zzangsuRealMoney;

    TextView kyubeomDifference;
    TextView seongsuDifference;
    TextView zzangsuDifference;

    Button calculateButton;

    Retrofit retrofit;
    RetrofitExService retrofitExService;

    public void getCalculateData() {
        retrofitExService.getCalculateData().enqueue(new Callback<List<Calculate>>() {
            @Override
            public void onResponse(Call<List<Calculate>> call, Response<List<Calculate>> response) {
                Log.d("kyubeom", "good");
                if (response.isSuccessful()) {
                    List<Calculate> body =  response.body();
                    if (body != null) {
                        Log.d("kyubeom", body.toString());
                        kyubeomMoney.setText(Integer.toString((int) body.get(0).getMoney()));
                        kyubeomDifference.setText(String.valueOf(body.get(0).getDifference()));

                        seongsuMoney.setText(Integer.toString((int) body.get(2).getMoney()));
                        seongsuDifference.setText(String.valueOf( body.get(2).getDifference()));

                        zzangsuMoney.setText(Integer.toString((int) body.get(1).getMoney()));
                        zzangsuDifference.setText(String.valueOf( body.get(1).getDifference()));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Calculate>> call, Throwable t) {
                Log.d("kyubeom", "fail");
            }
        });
    }

    public void calculate(HashMap<String, Object> payload) {
        retrofitExService.Calculate(payload).enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                getCalculateData();
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Log.d("kyubeom", "fail");
            }
        });
    }

    public CalculateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitExService.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitExService = retrofit.create(RetrofitExService.class);

        return inflater.inflate(R.layout.fragment_calculate, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        kyubeomMoney = getView().findViewById(R.id.kyubeom_money);
        seongsuMoney = getView().findViewById(R.id.seongsu_money);
        zzangsuMoney = getView().findViewById(R.id.zzangsu_money);

        kyubeomRealMoney = getView().findViewById(R.id.kyubeom_real_money);
        seongsuRealMoney = getView().findViewById(R.id.seongsu_real_money);
        zzangsuRealMoney = getView().findViewById(R.id.zzangsu_real_money);

        kyubeomDifference = getView().findViewById(R.id.kyubeom_difference);
        seongsuDifference = getView().findViewById(R.id.seongsu_difference);
        zzangsuDifference = getView().findViewById(R.id.zzangsu_difference);

        calculateButton = getView().findViewById(R.id.calculate);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HashMap<String, Object> payload = new HashMap<>();
                payload.put("player0", kyubeomRealMoney.getText().toString());
                payload.put("player1", zzangsuRealMoney.getText().toString());
                payload.put("player2", seongsuRealMoney.getText().toString());

                SimpleDateFormat s = new SimpleDateFormat("yy-MM-dd hh-mm");
                payload.put("filename", s.format(new Date()));
                calculate(payload);

            }
        });

        getCalculateData();


    }
}
