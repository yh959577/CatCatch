package com.cc.yh.cat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,SelectDialog.CheckLevel {
    PlayGround playGround;
    Button btnFinishGame;
    Button btnResetBlocks;
    Button btnSelectDifficult;
    private int flag=1;

    private static int EASY=30;
    private static int NORMAL=20;
    private static int HARDER=15;
    private static int HELL=10;
    private SelectDialog mSelectDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
                );
        setContentView(R.layout.activity_main);
        mSelectDialog=new SelectDialog(this,R.style.SelectDialog);
        mSelectDialog.setCheckLevel(this);
        playGround=(PlayGround)findViewById(R.id.play_ground);
        btnFinishGame=(Button)findViewById(R.id.finish_game);
        btnResetBlocks=(Button)findViewById(R.id.reset_blocks);
        btnSelectDifficult=(Button)findViewById(R.id.select_difficult);

        btnFinishGame.setOnClickListener(this);
        btnResetBlocks.setOnClickListener(this);
        btnSelectDifficult.setOnClickListener(this);
    }








    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.finish_game:
                finish();
            case R.id.reset_blocks:
                playGround.initialGame();
                break;
            case R.id.select_difficult:
                mSelectDialog.show();
                break;
            default:
                break;
        }


    }

    @Override
    public void easyLevel() {

    }

    @Override
    public void normalLevel() {

    }

    @Override
    public void harderLevel() {

    }

    @Override
    public void hellLevel() {

    }
}
