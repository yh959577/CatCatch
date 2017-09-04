package com.cc.yh.cat;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/31.
 */

public class PlayGround extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {

    private static final int ROW = 10;
    private static final int COL = 10;
    private static final String TAG = "PlayGround";
    private int BLOCKS=MainActivity.NORMAL;
    private Dot[][] matrix;
    private Dot cat;
    private int width;
    private int height;
    private Bitmap mObstacleBitmap;
    private Bitmap mCatBitmap;
    private Bitmap mEmptyBitmap;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private updateView mUpdateView;

    public PlayGround(Context context, AttributeSet attr){
        super(context,attr);
        setBackgroundResource(R.drawable.back_ground);
        setZOrderOnTop(true);
        getHolder().setFormat(PixelFormat.TRANSPARENT);
        initialGame();
        getHolder().addCallback(this);
        setOnTouchListener(this);

    }

    public void setLevel(int level){
        BLOCKS=level;
        initialGame();

    }

    public void setUpdateView(updateView updateView){
        mUpdateView=updateView;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        this.width = width / 11;
        this.height = height / 20;
        draw();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_UP) {
            int x;
            int y = (int) e.getY() / height;
            if (y % 2 != 0) {
                x = (int) (e.getX() - (width / 2)) / width;
            } else {
                x = (int) e.getX() / width;
            }
            try {
                if (getDot(x, y).getStatus() == Dot.status_off) {
                    getDot(x, y).setStatus(Dot.status_on);
                    mUpdateView.updateLastView();
                    catMove();
                }
            } catch (ArrayIndexOutOfBoundsException e1) {
                e1.printStackTrace();
            }
        }
        draw();
        return true;
    }

    public void initialGame() {
//        mSharedPreferences=getContext().getSharedPreferences();
        mObstacleBitmap= BitmapFactory.decodeResource(getResources(),R.drawable.obstacle);
        mCatBitmap=BitmapFactory.decodeResource(getResources(),R.drawable.cat);
        matrix = new Dot[ROW][COL];
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                matrix[i][j] = new Dot(j, i);
                matrix[i][j].setStatus(Dot.status_off);
            }
        }
        cat = getDot(ROW / 2, COL / 2).setStatus(Dot.status_in);
        for (int i = 0; i < BLOCKS; ) {
            int x = (int) (Math.random() * 1000) % COL;
            int y = (int) (Math.random() * 1000) % ROW;
            if (getDot(x, y).getStatus() == Dot.status_off) {
                getDot(x, y).setStatus(Dot.status_on);
                i++;
            }
        }
        if (width!=0&&height!=0) {
            draw();
            mUpdateView.resetStep();
        }
        }

    private Dot getDot(int x, int y) {
        return matrix[y][x];

    }

    private Dot getNeighbourDot(Dot ori, int dir) {
        if (isAtEdge(ori)){
            return null;
        }
        if (ori.getY() % 2 == 0) {
            switch (dir) {
                case 1:
                    return getDot(ori.getX() - 1, ori.getY());
                case 2:
                    return getDot(ori.getX() - 1, ori.getY() - 1);
                case 3:
                    return getDot(ori.getX(), ori.getY() - 1);
                case 4:
                    return getDot(ori.getX() + 1, ori.getY());
                case 5:
                    return getDot(ori.getX(), ori.getY() + 1);
                case 6:
                    return getDot(ori.getX() - 1, ori.getY() + 1);
            }

        } else {
            switch (dir) {
                case 1:
                    return getDot(ori.getX() - 1, ori.getY());
                case 2:
                    return getDot(ori.getX(), ori.getY() - 1);
                case 3:
                    return getDot(ori.getX() + 1, ori.getY() - 1);
                case 4:
                    return getDot(ori.getX() + 1, ori.getY());
                case 5:
                    return getDot(ori.getX() + 1, ori.getY() + 1);
                case 6:
                    return getDot(ori.getX(), ori.getY() + 1);
            }

        }
        return null;
    }

    private void lose() {
       Toast.makeText(getContext(),"YOU LOSE",Toast.LENGTH_SHORT).show();
    }

    private int getDistance(Dot next, int dir) {
        int steps = 1;
        while (true) {
            if (next.getStatus() == Dot.status_on) {
                return -steps;
            } else if (isAtEdge(next)) {
                return steps;
            } else {
                next = getNeighbourDot(next, dir);
                steps++;
            }

        }

    }

    private boolean isAtEdge(Dot d) {

        return (d.getX() * d.getY() == 0 || d.getX() == 9 || d.getY() == 9);
    }

    private void catMove() {
        Dot bestNext = getBestNextDot();
        if (bestNext==null){
            return;
        }
        getDot(cat.getX(),cat.getY()).setStatus(Dot.status_off);
        cat=bestNext;
        getDot(cat.getX(),cat.getY()).setStatus(Dot.status_in);
        if (isAtEdge(cat)){
            lose();
        }

    }

    private Dot getBestNextDot() {
        int best = 0;
        Dot bestDot=null;
        HashMap<Dot, Integer> dotAndDistance = new HashMap<>();
        for (int i = 1; i < 7; i++) {
            Dot next = getNeighbourDot(cat, i);
            if (next==null){
                return null;
            }
            int distance = getDistance(next, i);
            dotAndDistance.put(next, distance);
            Log.d(TAG, "catMove:  " + i + "方向======" + distance);
        }
        Iterator inter = dotAndDistance.entrySet().iterator();
        while (inter.hasNext()) {
            Map.Entry entry = (Map.Entry) inter.next();
            int distance = (int) entry.getValue();
            if (best == 0) {
                best = distance;
            } else {
                if (best > 0) {
                    if (distance > 0 && distance < best) {
                        best = distance;
                    }
                } else {
                    if (distance > 0 || distance < best) {
                        best = distance;
                    }
                }
            }
            Log.d(TAG, "getBestNextDot: value=====" + distance);
            if (distance==best){
                bestDot=(Dot) entry.getKey();
            }
        }
        if (best==-1){
            win();
            return null;
        }

        Log.d(TAG, "getBestNextDot: BestPath=====" +best);
        return bestDot;
    }

    private void win() {
        Toast.makeText(getContext(),"YOU WIN",Toast.LENGTH_SHORT).show();
        mUpdateView.saveBestStep();
        mUpdateView.updateBestView();
    }
    private void draw() {
        Canvas c = getHolder().lockCanvas();

//        c.drawColor(Color.CYAN);
        c.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        Paint p = new Paint();
        for (int i = 0; i < ROW; i++) {
            int offset = 0;
            if (i % 2 != 0) {
                offset = width / 2;
            }
            for (int j = 0; j < COL; j++) {
                Dot one = getDot(j, i);
                switch (one.getStatus()) {
                    case Dot.status_off:
//                        mBitmap=BitmapFactory.decodeResource(getResources(),R.drawable.white);
//                        p.setColor(Color.WHITE);
                        p.setColor(Color.WHITE);
                        c.drawOval(new RectF(one.getX() * width + offset, one.getY() * height,
                        (one.getX() + 1) * width + offset, (one.getY() + 1) * height
               ), p);
                        break;
                    case Dot.status_on:
                        c.drawBitmap(mObstacleBitmap,null
                                ,new RectF(one.getX() * width + offset, one.getY() * height,
                                        (one.getX() + 1) * width + offset, (one.getY() + 1) * height)
                                ,p);
//                        p.setColor(Color.YELLOW);
                        break;
                    case Dot.status_in:
                        c.drawBitmap(mCatBitmap,null,new RectF(one.getX() * width + offset, one.getY() * height,
                                        (one.getX() + 1) * width + offset, (one.getY() + 1) * height)
                                ,p);
//                        mBitmap=BitmapFactory.decodeResource(getResources(),R.drawable.cat);
//                        p.setColor(Color.RED);
                        break;
                    default:
                        break;
                }
//
//                c.drawBitmap(mBitmap,null,new RectF(one.getX() * width + offset, one.getY() * height,
//                        (one.getX() + 1) * width + offset, (one.getY() + 1) * height)
//                ,p);
//                c.drawOval(new RectF(one.getX() * width + offset, one.getY() * height,
//                        (one.getX() + 1) * width + offset, (one.getY() + 1) * height
//                ), p);
            }
        }
        getHolder().unlockCanvasAndPost(c);
    }

    public interface updateView{
        void updateLastView();
        void saveBestStep();
        void updateBestView();
        void resetStep();
    }
}
