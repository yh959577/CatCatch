package com.cc.yh.cat;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

/**
 * Created by Administrator on 2017/9/1.
 */

public class WelcomeActivity extends Activity {
    ImageView welcomeView;
    Animation animation;
    LoginDialog mLoginDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        welcomeView=(ImageView)findViewById(R.id.welcome_view);
        animation=new AlphaAnimation(0.1f,1.0f);
        animation.setDuration(3000);
        welcomeView.setAnimation(animation);
        mLoginDialog=new LoginDialog(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showQuestion();
            }
        },3000);
    }

    private void showQuestion() {
         mLoginDialog.show();
    }
}
