package com.cybil.study.erection;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.VideoView;

public class EndingActivity extends AppCompatActivity {

    VideoView videoView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ending);
        videoView = (VideoView) findViewById(R.id.ending);

        Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.dotnam_ending);
        videoView.setVideoURI(video);

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                startNextActivity();
            }
        });

        videoView.start();
    }

    private void startNextActivity() {
        if (isFinishing())
            return;
        ActivityCompat.finishAffinity(this);
        System.exit(0);
//        android.os.Process.killProcess(Process.myPid());
    }
}