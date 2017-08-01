package com.cc.yh.cat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,SelectDialog.CheckLevel {
    PlayGround playGround;
    Button btnFinishGame;
    Button btnResetBlocks;
    Button btnSelectDifficult;
    TextView tvCurrentLevel;
    private int flag=1;

    public static int EASY=30;
    public static int NORMAL=20;
    public static int HARDER=15;
    public static int HELL=10;

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
        tvCurrentLevel=(TextView)findViewById(R.id.current_level);

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
            playGround.setLevel(EASY);
            tvCurrentLevel.setText(R.string.easy);
    }

    @Override
    public void normalLevel() {
         playGround.setLevel(NORMAL);
        tvCurrentLevel.setText(R.string.normal);
    }

    @Override
    public void harderLevel() {
      playGround.setLevel(HARDER);
        tvCurrentLevel.setText(R.string.harder);
    }

    @Override
    public void hellLevel() {
     playGround.setLevel(HELL);
        tvCurrentLevel.setText(R.string.hell);
    }
}
