package com.cc.yh.cat;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
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
    private PopupWindow mPopupWindow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
                );
        setContentView(R.layout.activity_main);

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
                showPopupWindow();
//                mSelectDialog.show();
                break;
            default:
                break;
        }


    }
    RadioButton[] radioButtons=new RadioButton[]{

    };
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
}
