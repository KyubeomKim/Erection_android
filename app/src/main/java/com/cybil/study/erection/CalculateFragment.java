package com.cybil.study.erection;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

import com.cybil.study.erection.util.Calculate;
import com.cybil.study.erection.util.Data;
import com.cybil.study.erection.util.Report;
import com.cybil.study.erection.util.RetrofitExService;

import org.w3c.dom.Text;

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
    private static CalculateFragment calculateFragmentInstance;

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

    TableRow reportLabel;
    TableRow reportKyubeom;
    TableRow reportSeongsu;
    TableRow reportZzangsu;

    TextView kyubeomTotalProfit;
    TextView seongsuTotalProfit;
    TextView zzangsuTotalProfit;

    Button calculateButton;

    Retrofit retrofit;
    RetrofitExService retrofitExService;

    AlertDialog.Builder currentDialog;

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

    public void setCommission(HashMap<String, Object> payload) {
        retrofitExService.setCommission(payload).enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                setStackValueDialog();
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {

            }
        });
    }

    public void getReport() {
        retrofitExService.getReport().enqueue(new Callback<List<Report>>() {
            @Override
            public void onResponse(Call<List<Report>> call, Response<List<Report>> response) {
                TableRow[] tr = {reportLabel, reportKyubeom, reportSeongsu, reportZzangsu};
                Log.d(TAG, "test");
                if ((reportLabel.getVirtualChildCount()-2) < response.body().size() ){
                    for (int i=(reportLabel.getVirtualChildCount()-2); i<response.body().size(); i++) {
                        TextView tv = new TextView(getContext());
                        tv.setText(response.body().get(i).getDate());
                        tv.setGravity(Gravity.CENTER);
                        tv.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/nougat_extrablack_webfont.ttf"));
                        tv.setTextColor(Color.BLACK);
                        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
                        reportLabel.addView(tv, i+1);

                        TextView kbtv = new TextView(getContext());
                        kbtv.setText(String.valueOf(response.body().get(i).getTotalProfit().getPlayer0()));
                        kbtv.setGravity(Gravity.CENTER);
                        kbtv.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/nougat_extrablack_webfont.ttf"));
                        kbtv.setTextColor(Color.BLACK);
                        kbtv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
                        reportKyubeom.addView(kbtv, i+1);

                        TextView sstv = new TextView(getContext());
                        sstv.setText(String.valueOf(response.body().get(i).getTotalProfit().getPlayer2()));
                        sstv.setGravity(Gravity.CENTER);
                        sstv.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/nougat_extrablack_webfont.ttf"));
                        sstv.setTextColor(Color.BLACK);
                        sstv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
                        reportSeongsu.addView(sstv, i+1);

                        TextView zstv = new TextView(getContext());
                        zstv.setText(String.valueOf(response.body().get(i).getTotalProfit().getPlayer1()));
                        zstv.setGravity(Gravity.CENTER);
                        zstv.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/nougat_extrablack_webfont.ttf"));
                        zstv.setTextColor(Color.BLACK);
                        zstv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
                        reportZzangsu.addView(zstv, i+1);
                    }
                } else {
                    for (int i=0; i<response.body().size(); i++) {
                        TextView tv = (TextView) reportLabel.getVirtualChildAt(i+1);
                        TextView kbtv = (TextView) reportKyubeom.getVirtualChildAt(i+1);
                        TextView sstv = (TextView) reportSeongsu.getVirtualChildAt(i+1);
                        TextView zstv = (TextView) reportZzangsu.getVirtualChildAt(i+1);

                        tv.setText(response.body().get(i).getDate());
                        kbtv.setText(String.valueOf(response.body().get(i).getTotalProfit().getPlayer0()));
                        sstv.setText(String.valueOf(response.body().get(i).getTotalProfit().getPlayer2()));
                        zstv.setText(String.valueOf(response.body().get(i).getTotalProfit().getPlayer1()));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Report>> call, Throwable t) {
                Log.d(TAG, "test");
            }
        });
    }

    public void calculate(HashMap<String, Object> payload) {
        retrofitExService.Calculate(payload).enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                setCommissionDialog();
//                getCalculateData();
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Log.d("kyubeom", "fail");
            }
        });
    }


    public void setCommissionDialog() {
        currentDialog = new AlertDialog.Builder(getContext());

        currentDialog.setTitle("짱수를");       // 제목 설정
        currentDialog.setMessage("구해라!");   // 내용 설정

        final EditText et = new EditText(getContext());
        et.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);

        currentDialog.setView(et);

        currentDialog.setPositiveButton("변경", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.v(TAG, "Yes Btn Click");

                // Text 값 받아서 로그 남기기
                String value = et.getText().toString();
                Log.v(TAG, value);

                HashMap<String, Object> payload = new HashMap<>();
                payload.put("commission", value);

                setCommission(payload);

                dialog.dismiss();     //닫기
                // Event
            }
        });

        currentDialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.v(TAG,"No Btn Click");
                dialog.dismiss();     //닫기
                // Event
            }
        });
        currentDialog.show();
    }

    public void setStackValueDialog() {
        currentDialog = new AlertDialog.Builder(getContext());

        currentDialog.setTitle("돈 한뭉치는");       // 제목 설정
        currentDialog.setMessage("얼마입니까?");   // 내용 설정

        final EditText et = new EditText(getContext());
        et.setInputType(InputType.TYPE_CLASS_NUMBER);
        currentDialog.setView(et);

        currentDialog.setPositiveButton("변경", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.v(TAG, "Yes Btn Click");

                // Text 값 받아서 로그 남기기
                String value = et.getText().toString().equals("") ? "10" : et.getText().toString();

                DashboardFragment.valueOfStack=Integer.valueOf(value);
                getCalculateData();
                getReport();
                DashboardFragment.getInstance().getDashboardData();

                dialog.dismiss();     //닫기
                // Event
            }
        });

        currentDialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.v(TAG,"No Btn Click");
                dialog.dismiss();     //닫기
                // Event
            }
        });
        currentDialog.show();
    }

    public CalculateFragment() {
        // Required empty public constructor
    }

    public static CalculateFragment getInstance() {
        return calculateFragmentInstance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        calculateFragmentInstance = this;
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

        reportLabel = getView().findViewById(R.id.report_label);
        reportKyubeom = getView().findViewById(R.id.report_kyubeom);
        reportSeongsu = getView().findViewById(R.id.report_seongsu);
        reportZzangsu = getView().findViewById(R.id.report_zzangsu);

        kyubeomTotalProfit = getView().findViewById(R.id.kyubeom_total_profit);
        seongsuTotalProfit = getView().findViewById(R.id.seongsu_total_profit);
        zzangsuTotalProfit = getView().findViewById(R.id.zzangsu_total_profit);

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
        getReport();
    }
}
