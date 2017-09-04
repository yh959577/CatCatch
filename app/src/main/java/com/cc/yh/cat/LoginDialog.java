package com.cc.yh.cat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

/**
 * Created by Administrator on 2017/9/1.
 */

public class LoginDialog extends AlertDialog {
    private AlertDialog.Builder mBuilder;
    private AlertDialog mDialog;

    protected LoginDialog(@NonNull final Context context) {
        super(context);
        mBuilder=new AlertDialog.Builder(context);
        mBuilder.setView(R.layout.dialog_login)
                .setPositiveButton(R.string.OK, new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        context.startActivity(new Intent(context,MainActivity.class));
                    }
                })
                .setNegativeButton(R.string.finish_game, new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        mDialog = mBuilder.create();

    }

    @Override
    public void show() {
        super.show();
        mDialog.show();

    }
}
