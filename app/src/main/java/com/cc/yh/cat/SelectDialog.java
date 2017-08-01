package com.cc.yh.cat;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;

/**
 * Created by Administrator on 2017/8/1.
 */

public class SelectDialog extends Dialog implements View.OnClickListener {
   private   RadioButton btnEasy,btnNormal,btnHarder,btnHell;
   private   boolean isEasy,isNormal,isHarder,isHell;
   private CheckLevel mCheckLevel;
    public SelectDialog(Context context,int theme){
        super(context,theme);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_select_difficult);
        initView();
    }

    private void initView() {
        btnEasy=(RadioButton)findViewById(R.id.easy);
        btnNormal=(RadioButton)findViewById(R.id.normal);
        btnHarder=(RadioButton)findViewById(R.id.harder);
        btnHell=(RadioButton)findViewById(R.id.hell);
        btnEasy.setOnClickListener(this);
        btnNormal.setOnClickListener(this);
        btnHarder.setOnClickListener(this);
        btnHell.setOnClickListener(this);

        btnEasy.setChecked(isEasy);
        btnNormal.setChecked(isNormal);
        btnHarder.setChecked(isHarder);
        btnHell.setChecked(isHell);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.easy:
                isEasy=true;
                mCheckLevel.easyLevel();
                this.dismiss();
                break;
            case R.id.normal:
                isNormal=true;
                mCheckLevel.normalLevel();
                this.dismiss();
                break;
            case R.id.harder:
                isHarder=true;
                mCheckLevel.harderLevel();
                this.dismiss();
                break;
            case R.id.hell:
                isHell=true;
                mCheckLevel.hellLevel();
                this.dismiss();
                break;
            default:
                break;
        }
    }
    public void setCheckLevel(CheckLevel checkLevel){
        mCheckLevel=checkLevel;
    }
    public static interface CheckLevel{
      void easyLevel();
      void normalLevel();
      void harderLevel();
      void hellLevel();
  }

}
