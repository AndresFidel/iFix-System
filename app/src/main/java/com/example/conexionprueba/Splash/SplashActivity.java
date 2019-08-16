package com.example.conexionprueba.Splash;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.conexionprueba.R;
import com.example.conexionprueba.login;

public class SplashActivity extends AppCompatActivity {
    private ImageView logo;
    private static int splashTimeOut=2000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        logo=(ImageView)findViewById(R.id.imgIfix);

        final MediaPlayer sound= MediaPlayer.create(this, R.raw.soundintro);
        sound.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent sc=new Intent(SplashActivity.this, login.class);
                startActivity(sc);
                finish();
            }
        },splashTimeOut);

        Animation myanim= AnimationUtils.loadAnimation(this,R.anim.intro);
        logo.startAnimation(myanim);
    }
}
