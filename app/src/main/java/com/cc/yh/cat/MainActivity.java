package com.cc.yh.cat;

import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,PlayGround.updateView{
    PlayGround playGround;
    Button btnFinishGame;
    Button btnResetBlocks;
    Button btnSelectDifficult;
    TextView tvCurrentLevel;
    public static int EASY=30;
    public static int NORMAL=20;
    public static int HARDER=15;
    public static int HELL=10;
    private PopupWindow mPopupWindow;
    DisplayMetrics mDisplayMetrics;
    TextView bestRecorder;
    TextView lastRecorder;
    int recentStep;
    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
                );
        setContentView(R.layout.activity_main);
        recentStep=0;
        mSharedPreferences=getSharedPreferences("best",MODE_PRIVATE);
        mEditor=mSharedPreferences.edit();
        mDisplayMetrics=getResources().getDisplayMetrics();
        playGround=(PlayGround)findViewById(R.id.play_ground);
        btnFinishGame=(Button)findViewById(R.id.finish_game);
        btnResetBlocks=(Button)findViewById(R.id.reset_blocks);
        btnSelectDifficult=(Button)findViewById(R.id.select_difficult);
        tvCurrentLevel=(TextView)findViewById(R.id.current_level);
        btnFinishGame.setOnClickListener(this);
        btnResetBlocks.setOnClickListener(this);
        btnSelectDifficult.setOnClickListener(this);
        playGround.setUpdateView(this);
        createLeftMenu();

    }

    private void createLeftMenu() {
        SlidingMenu LeftMenu=new SlidingMenu(this);
        LeftMenu.setMode(SlidingMenu.LEFT);
        LeftMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        LeftMenu.setBehindWidth(mDisplayMetrics.widthPixels/2);
        LeftMenu.attachToActivity(this,SlidingMenu.SLIDING_WINDOW);
        LeftMenu.setMenu(R.layout.layout_menu);
        bestRecorder=(TextView)LeftMenu.findViewById(R.id.best_number);
        lastRecorder=(TextView)LeftMenu.findViewById(R.id.recent_number);
        bestRecorder.setText(mSharedPreferences.getInt("best",0)+"");
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
                showPopupWindow();
                break;
            default:
                break;
        }


    }
    private void showPopupWindow() {
        View view=getLayoutInflater().inflate(R.layout.poupu_window_view,null);
        ListView listView=(ListView)view.findViewById(R.id.list_view);
        listView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1
        ,new String[]{"简单","正常","困难","地狱"}
        ));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               switch (position) {
                   case 0:
                       easyLevel();
                       break;
                   case 1:
                       normalLevel();
                       break;
                   case 2:
                       harderLevel();
                       break;
                   case 3:
                       hellLevel();
                       break;
                   default:
                       break;
               }
               mPopupWindow.dismiss();
            }
        });
        mPopupWindow = new PopupWindow(view,400,600);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        mPopupWindow.showAsDropDown(tvCurrentLevel);
    }
    public void easyLevel() {
            playGround.setLevel(EASY);
            tvCurrentLevel.setText(R.string.easy);
    }

    public void normalLevel() {
         playGround.setLevel(NORMAL);
        tvCurrentLevel.setText(R.string.normal);
    }

    public void harderLevel() {
       playGround.setLevel(HARDER);
        tvCurrentLevel.setText(R.string.harder);
    }

    public void hellLevel() {
        playGround.setLevel(HELL);
        tvCurrentLevel.setText(R.string.hell);
    }

    @Override
    public void updateLastView() {
        recentStep++;
        lastRecorder.setText(recentStep+"");
    }

    @Override
    public void saveBestStep() {
        if (mSharedPreferences.getInt("best",0)>recentStep
                ) {
            mEditor.clear();
            mEditor.putInt("best", recentStep);
            mEditor.commit();
        }

    }
    @Override
    public void updateBestView() {
       bestRecorder.setText(getSharedPreferences("best",MODE_PRIVATE).getInt("best",0)+"");
    }

    @Override
    public void resetStep() {
        recentStep=0;
    }

}
