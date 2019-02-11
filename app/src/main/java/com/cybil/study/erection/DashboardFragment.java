package com.cybil.study.erection;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.cybil.study.erection.util.Dashboard;
import com.cybil.study.erection.util.Data;
import com.cybil.study.erection.util.Gambler;
import com.cybil.study.erection.util.RetrofitExService;

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
public class DashboardFragment extends Fragment {

    String TAG="kyubeom";
    int gamblerUpPixel = 26;
    int gamblerDownPixel = -26;
    int valueOfStack = 10;

    ImageView lee;
    ImageView chen;

    LinearLayout dashboardLayout;

    RelativeLayout kyubeomLayout;
    RelativeLayout seongsuLayout;
    RelativeLayout zzangsuLayout;

    ImageView kyubeomImage;
    ImageView seongsuImage;
    ImageView zzangsuImage;

    TextView kyubeomSeed;
    TextView kyubeomRate;
    TextView kyubeomBalance;
    TextView kyubeomProfit;

    TextView seongsuSeed;
    TextView seongsuRate;
    TextView seongsuBalance;
    TextView seongsuProfit;

    TextView zzangsuSeed;
    TextView zzangsuRate;
    TextView zzangsuBalance;
    TextView zzangsuProfit;

    VideoView vv;

    Button currentBalanceButton;
    AlertDialog.Builder currentDialog;


    Gambler kyubeom;
    Gambler seongsu;
    Gambler zzangsu;

    Retrofit retrofit;
    RetrofitExService retrofitExService;

    boolean appearFlag = false;

    public DashboardFragment() {
        // Required empty public constructor
    }

    // API 호출 메소드
    public void getDashboardData() {
        retrofitExService.getDashboardData().enqueue(new Callback<List<Dashboard>>() {
            @Override
            public void onResponse(@NonNull Call<List<Dashboard>> call, @NonNull Response<List<Dashboard>> response) {
                Log.d("kyubeom", "good");
                if (response.isSuccessful()) {
                    List<Dashboard> body =  response.body();
                    if (body != null) {
                        Log.d("kyubeom", body.toString());
                        kyubeomSeed.setText(Integer.toString((int) body.get(0).getSeed()));
                        kyubeomRate.setText(String.valueOf(body.get(0).getRate()));
                        kyubeomBalance.setText(Integer.toString((int) body.get(0).getBalance()));
                        kyubeomProfit.setText(Integer.toString((int) body.get(0).getProfit()));

                        seongsuSeed.setText(Integer.toString((int) body.get(2).getSeed()));
                        seongsuRate.setText(String.valueOf( body.get(2).getRate()));
                        seongsuBalance.setText(Integer.toString((int) body.get(2).getBalance()));
                        seongsuProfit.setText(Integer.toString((int) body.get(2).getProfit()));

                        zzangsuSeed.setText(Integer.toString((int) body.get(1).getSeed()));
                        zzangsuRate.setText(String.valueOf( body.get(1).getRate()));
                        zzangsuBalance.setText(Integer.toString((int) body.get(1).getBalance()));
                        zzangsuProfit.setText(Integer.toString((int) body.get(1).getProfit()));

                        int kyubeomStackDelta = setStack(kyubeom, Integer.parseInt(kyubeomProfit.getText().toString()));
                        int seongsuStackDelta = setStack(seongsu, Integer.parseInt(seongsuProfit.getText().toString()));
                        int zzangsuStackDelta = setStack(zzangsu, Integer.parseInt(zzangsuProfit.getText().toString()));

                        if (kyubeomStackDelta > 0 || seongsuStackDelta > 0 || zzangsuStackDelta > 0) {
                            leeAppear(kyubeomStackDelta, seongsuStackDelta, zzangsuStackDelta);
                        } else if (kyubeomStackDelta < 0 || seongsuStackDelta < 0 || zzangsuStackDelta < 0) {
                            chenAppear(Math.abs(kyubeomStackDelta), Math.abs(seongsuStackDelta), Math.abs(zzangsuStackDelta));
                        }

                        currentBalanceButton.setText(Integer.toString((int) body.get(4).getSeed()));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Dashboard>> call, @NonNull Throwable t) {
                Log.d("kyubeom", "fail");
            }
        });
    }

    public void setTotalBalance(HashMap<String, Object> payload) {
        retrofitExService.setTotalBalance(payload).enqueue(new Callback<Data> () {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                getDashboardData();
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Log.d("kyubeom", "fail");
            }
        });
    }

    public void setSeed(HashMap<String, Object> payload) {
        retrofitExService.setSeed(payload).enqueue(new Callback<Data> () {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                getDashboardData();
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Log.d("kyubeom", "fail");
            }
        });
    }


    // 애니메이션 메소드 영역
    // 장첸 등장
    public void chenAppear(int kyubeomStack, int seongsuStack, int zzangsuStack){
        final int kStack = kyubeomStack;
        final int sStack = seongsuStack;
        final int zStack = zzangsuStack;

        TranslateAnimation tAnimation = new TranslateAnimation(-1000.0f, 0.0f, 0.0f, 0.0f);
        tAnimation.setDuration(2000);
        tAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                loseMoney(kyubeom, kyubeomImage, R.id.v_kyubeom, kStack, false);
                loseMoney(seongsu, seongsuImage, R.id.v_seongsu, sStack, true);
                loseMoney(zzangsu, zzangsuImage, R.id.v_zzangsu, zStack, false);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        chen.setVisibility(View.VISIBLE);
        chen.startAnimation(tAnimation);
    }
    // 장첸 퇴장
    public void chenDisappear(){
        TranslateAnimation tAnimation = new TranslateAnimation(0.0f, -1000.0f, 0.0f, 0.0f);
        tAnimation.setDuration(2000);
        chen.startAnimation(tAnimation);
        chen.setVisibility(View.GONE);
    }

    // 이수근 등장
    public void leeAppear(int kyubeomStack, int seongsuStack, int zzangsuStack){
        final int kStack = kyubeomStack;
        final int sStack = seongsuStack;
        final int zStack = zzangsuStack;
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.lee_appear);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                getMoney(kyubeom, kyubeomImage, R.id.v_kyubeom, kStack, false);
                getMoney(seongsu, seongsuImage, R.id.v_seongsu, sStack, true);
                getMoney(zzangsu, zzangsuImage, R.id.v_zzangsu, zStack, false);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        lee.setVisibility(View.VISIBLE);
        lee.startAnimation(animation);
    }
    // 이수근 퇴장
    public void leeDisappear(){
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.lee_disappear);
        lee.startAnimation(animation);
    }
    public int dpToPixel (float dp) {
        Resources r = getResources();
        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                r.getDisplayMetrics()
        );
        return px;
    }
    // 캐릭터 상하이동
    public void moveGambler(ImageView iv, int pixel) {
        ViewGroup.MarginLayoutParams marginParams = new ViewGroup.MarginLayoutParams(iv.getLayoutParams());
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) iv.getLayoutParams();
        int result = lp.topMargin - pixel;

        marginParams.setMargins(0, result, 0, 0);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(marginParams);
        iv.setLayoutParams(layoutParams);
    }

    public void loseMoney( Gambler gambler, ImageView iv, int layout, int stack, boolean chenDisapper) {
        if(stack != 0){
            final Gambler currentGambler = gambler;
            final ImageView currentIv = iv;
            final int currentLayoutId = layout;
            final int currentDuration = 1000/stack;
            final int currentStack = stack;
            final boolean mChenDisapper = chenDisapper;

            final int toStack = currentGambler.getStack() - stack;
            final RelativeLayout currentLayout = getView().findViewById(layout);

            final TranslateAnimation loseMoneyAnimation = new TranslateAnimation(0f, 0f, 0f, -2000f);
            loseMoneyAnimation.setDuration(currentDuration);
            loseMoneyAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if(toStack<currentGambler.getStack()) {
                        loseMoney(currentGambler, currentIv, currentLayoutId, currentStack-1, mChenDisapper);
                    } else if (mChenDisapper){
                        chenDisappear();
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            currentLayout.getChildAt(9+currentGambler.getStack()).startAnimation(loseMoneyAnimation);
            currentLayout.removeViewAt(9+currentGambler.getStack());
            currentGambler.addStack(-1);
            moveGambler(iv, gamblerDownPixel);
        }
    }

    public void getMoney( Gambler gambler, ImageView iv, int layout, int stack, boolean leeDisapper) {
        if(stack != 0){
            final Gambler currentGambler = gambler;
            final ImageView currentIv = iv;
            final int currentLayoutId = layout;
            final int currentDuration = 1000/stack;
            final int currentStack = stack;
            final boolean mLeeDisapper = leeDisapper;

            final int toStack = currentGambler.getStack() + stack;
            final RelativeLayout currentLayout = getView().findViewById(layout);

            final TranslateAnimation loseMoneyAnimation = new TranslateAnimation(0f, 0f, -2000f, 0f);
            loseMoneyAnimation.setDuration(currentDuration);
            loseMoneyAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    moveGambler(currentIv, gamblerUpPixel);
                    if(toStack>currentGambler.getStack()) {
                        getMoney(currentGambler, currentIv, currentLayoutId, currentStack-1, mLeeDisapper);
                    } else if (mLeeDisapper){
                        leeDisappear();
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            currentGambler.addStack(1);
            createMoney(layout, currentGambler.getStack());
            currentLayout.getChildAt(9+currentGambler.getStack()).startAnimation(loseMoneyAnimation);
        }
    }

    public void createMoney(int layout, int stack) {
        RelativeLayout currentLayout = getView().findViewById(layout);

        int height = (int) getResources().getDimension(R.dimen.money_height);
        int width = (int) getResources().getDimension(R.dimen.money_width);
        int topMargin = dpToPixel((float) 490 - (10*stack));

        ImageView newMoney = new ImageView(getContext());
        newMoney.setLayoutParams(new ViewGroup.LayoutParams(width, height));
        newMoney.setImageResource(R.drawable.ic_money);

        ViewGroup.MarginLayoutParams marginParams = new ViewGroup.MarginLayoutParams(newMoney.getLayoutParams());
        marginParams.setMargins(0, topMargin, 0, 0);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(marginParams);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
        newMoney.setLayoutParams(layoutParams);

        currentLayout.addView(newMoney, 9+stack);
    }

    // 기타 메소드 영역

    public void judgmentTime() {

    }

    public int setStack(Gambler gambler, int profit) {
        int stack = gambler.getStack();
        int newStack =(profit/valueOfStack);

        if(newStack < 0) {
            return -stack;
        } else {
            return newStack-stack;
        }
    }

    // 팝업 설정 메소드
    public void setChangeDialog() {
        currentDialog = new AlertDialog.Builder(getContext());

        currentDialog.setTitle("무언가가 바뀌었다..");       // 제목 설정
        currentDialog.setMessage("무엇을.. 변경하시겠습니까?");   // 내용 설정

        currentDialog.setPositiveButton("현재금액", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setCurrentDialog();

                dialog.dismiss();
            }
        });

        currentDialog.setNeutralButton("수수료", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();     //닫기
                // Event
            }
        });

        currentDialog.setNegativeButton("자본금", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setSeedDialog();
                dialog.dismiss();     //닫기
                // Event
            }
        });
        currentDialog.show();
    }

    public void setCurrentDialog() {
        currentDialog = new AlertDialog.Builder(getContext());

        currentDialog.setTitle("지금 우리는..");       // 제목 설정
        currentDialog.setMessage("얼마를 가지고 있나요?");   // 내용 설정

        final EditText et = new EditText(getContext());
        currentDialog.setView(et);

        currentDialog.setPositiveButton("변경", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.v(TAG, "Yes Btn Click");

                // Text 값 받아서 로그 남기기
                String value = et.getText().toString();
                Log.v(TAG, value);

                HashMap<String, Object> payload = new HashMap<>();
                payload.put("total", value);

                setTotalBalance(payload);

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

    public void setSeedDialog() {
        currentDialog = new AlertDialog.Builder(getContext());

        currentDialog.setTitle("돈을..");       // 제목 설정
        currentDialog.setMessage("더 넣으시게요?");   // 내용 설정

        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.HORIZONTAL);

        final EditText kb = new EditText(getContext());
        kb.setHint("규범");
        layout.addView(kb);
        final EditText ss = new EditText(getContext());
        ss.setHint("성수");
        layout.addView(ss);
        final EditText zs = new EditText(getContext());
        zs.setHint("짱수");
        layout.addView(zs);

        currentDialog.setView(layout);

        currentDialog.setPositiveButton("변경", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.v(TAG, "Yes Btn Click");

                // Text 값 받아서 로그 남기기
                String kbSeed = kb.getText().toString();
                String ssSeed = ss.getText().toString();
                String zsSeed = zs.getText().toString();

                HashMap<String, Object> payload = new HashMap<>();
                payload.put("player1", kbSeed);
                payload.put("player2", zsSeed);
                payload.put("player3", ssSeed);

                setSeed(payload);

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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        kyubeom = new Gambler(0);
        seongsu = new Gambler(0);
        zzangsu = new Gambler(0);

        retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitExService.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitExService = retrofit.create(RetrofitExService.class);

        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lee = (ImageView) getView().findViewById(R.id.lee);
        chen = (ImageView) getView().findViewById(R.id.chen);

        kyubeomImage = (ImageView) getView().findViewById(R.id.kyubeom);
        seongsuImage = (ImageView) getView().findViewById(R.id.seongsu);
        zzangsuImage = (ImageView) getView().findViewById(R.id.zzangsu);

        dashboardLayout = (LinearLayout) getView().findViewById(R.id.dashboard_layout);

        kyubeomLayout = (RelativeLayout) getView().findViewById(R.id.v_kyubeom);
        seongsuLayout = (RelativeLayout) getView().findViewById(R.id.v_seongsu);
        zzangsuLayout = (RelativeLayout) getView().findViewById(R.id.v_zzangsu);

        kyubeomSeed = getView().findViewById(R.id.kyubeom_seed);
        kyubeomBalance = getView().findViewById(R.id.kyubeom_balance);
        kyubeomRate = getView().findViewById(R.id.kyubeom_rate);
        kyubeomProfit = getView().findViewById(R.id.kyubeom_profit);

        seongsuSeed = getView().findViewById(R.id.seongsu_seed);
        seongsuBalance = getView().findViewById(R.id.seongsu_balance);
        seongsuRate = getView().findViewById(R.id.seongsu_rate);
        seongsuProfit = getView().findViewById(R.id.seongsu_profit);

        zzangsuSeed = getView().findViewById(R.id.zzangsu_seed);
        zzangsuBalance = getView().findViewById(R.id.zzangsu_balance);
        zzangsuRate = getView().findViewById(R.id.zzangsu_rate);
        zzangsuProfit = getView().findViewById(R.id.zzangsu_profit);

        currentBalanceButton = getView().findViewById(R.id.current_balance);

        Button testButton = (Button) getView().findViewById(R.id.testbutton);

        getDashboardData();

        // test 영역

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!appearFlag) {
                    leeAppear(3,5, 2);
                    appearFlag = true;

                } else {
                    chenAppear(2,6,1);
                    appearFlag = false;
                }
            }
        });

        dashboardLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                setChangeDialog();
                return true;
            }
        });

        currentBalanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setChangeDialog();
            }
        });

        kyubeomImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kyubeom.addStack(1);
                moveGambler(kyubeomImage, gamblerUpPixel);
                createMoney(R.id.v_kyubeom, kyubeom.getStack());
                Log.d(TAG, "onClick: kyubeom's stack: " + kyubeom.getStack());
            }
        });

        kyubeomImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                moveGambler(kyubeomImage, gamblerDownPixel);
                kyubeom.addStack(-1);
//                deleteMoney(R.id.v_kyubeom, kyubeom.getStack(), 1000);
                Log.d(TAG, "onClick: kyubeom's stack: " + kyubeom.getStack());
                return true;
            }
        });

        seongsuImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seongsu.addStack(1);
                moveGambler(seongsuImage, gamblerUpPixel);
                createMoney(R.id.v_seongsu, seongsu.getStack());
                Log.d(TAG, "onClick: seongsu's stack: " + seongsu.getStack());
            }
        });

        seongsuImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                moveGambler(seongsuImage, gamblerDownPixel);
                seongsu.addStack(-1);
//                deleteMoney(R.id.v_seongsu, seongsu.getStack(),1000);
                Log.d(TAG, "onClick: seongsu's stack: " + seongsu.getStack());
                return true;
            }
        });

        zzangsuImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zzangsu.addStack(1);
                moveGambler(zzangsuImage, gamblerUpPixel);
                createMoney(R.id.v_zzangsu, zzangsu.getStack());
                Log.d(TAG, "onClick: zzangsu's stack: " + zzangsu.getStack());
            }
        });

        zzangsuImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                moveGambler(zzangsuImage, gamblerDownPixel);
                zzangsu.addStack(-1);
//                deleteMoney(R.id.v_zzangsu, zzangsu.getStack(), 1000);
                Log.d(TAG, "onClick: zzangsu's stack: " + zzangsu.getStack());
                return true;
            }
        });
    }
}
