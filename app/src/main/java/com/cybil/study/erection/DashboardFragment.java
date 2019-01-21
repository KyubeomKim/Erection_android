package com.cybil.study.erection;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.cybil.study.erection.util.Gambler;
import com.plattysoft.leonids.ParticleSystem;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {

    ImageView lee;
    ImageView kyubeomImage;
    ImageView seongsuImage;
    ImageView zzangsuImage;

    Gambler kyubeom;
    Gambler seongsu;
    Gambler zzangsu;
    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        kyubeom = new Gambler(0);
        seongsu = new Gambler(0);
        zzangsu = new Gambler(0);
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lee = (ImageView) getView().findViewById(R.id.lee);
        kyubeomImage = (ImageView) getView().findViewById(R.id.kyubeom);
        seongsuImage = (ImageView) getView().findViewById(R.id.seongsu);
        zzangsuImage = (ImageView) getView().findViewById(R.id.zzangsu);

        Button testButton = (Button) getView().findViewById(R.id.testbutton);
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.lee_appear);
        lee.startAnimation(animation);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 이수근 사라지기
//                Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.lee_disappear);
//                lee.startAnimation(animation);
                // 규범 한칸 올라가기

                float dip = 10f;
                Resources r = getResources();
                float px = TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        dip,
                        r.getDisplayMetrics()
                );

//                int dimensionInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
                ViewGroup.MarginLayoutParams marginParams = new ViewGroup.MarginLayoutParams(kyubeomImage.getLayoutParams());
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) kyubeomImage.getLayoutParams();
                int result = lp.topMargin - 26;

                marginParams.setMargins(0, result, 0, 0);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(marginParams);
                kyubeomImage.setLayoutParams(layoutParams);

            }
        });
    }
}
