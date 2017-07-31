package com.cc.yh.cat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
 * Created by Administrator on 2017/7/31.
 */

public class PlayGround extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {

    private static final int ROW = 10;
    private static final int COL = 10;
    private Dot[][] matrix;
    private Dot cat;
    private int width;
    private int height;

    public PlayGround(Context context) {
        super(context);
        initialGame();
        getHolder().addCallback(this);
        setOnTouchListener(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        this.width = width/11;
        this.height=height/20;
        draw();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent e) {
        if (e.getAction()==MotionEvent.ACTION_UP){
            int x;
            int y=(int) e.getY()/height;
            if (y%2!=0){
                 x=(int) (e.getX()-(width/2))/width;
            }else {
                 x=(int) e.getX()/width;
            }
            getDot(x,y).setStatus(Dot.status_on);
        }
        draw();


        return true;
    }

    private void initialGame() {
        matrix = new Dot[ROW][COL];
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                matrix[i][j] = new Dot(j, i);
                matrix[i][j].setStatus(Dot.status_off);
            }
        }
        cat = getDot(ROW / 2, COL / 2).setStatus(Dot.status_in);
        for (int i = 0; i < 15; ) {
            int x = (int) (Math.random() * 1000) % COL;
            int y = (int) (Math.random() * 1000) % ROW;
            if (getDot(x, y).getStatus() == Dot.status_off) {
                getDot(x, y).setStatus(Dot.status_on);
                i++;
            }
        }

    }

    private Dot getDot(int x, int y) {
        return matrix[y][x];


    }

    private Dot getNeighbourDot(Dot ori, int dir) {
        return null;
    }

    private int getDistance(Dot cat, int dir) {
        return 0;
    }

    private void catMove() {

    }

    private void draw() {
        Canvas c = getHolder().lockCanvas();
        c.drawColor(Color.CYAN);
        Paint p = new Paint();
        for (int i = 0; i < ROW; i++) {
            int offset=0;
            if (i%2!=0){
                offset=width/2;
            }
            for (int j = 0; j < COL; j++) {
                Dot one = getDot(j, i);
                switch (one.getStatus()) {
                    case Dot.status_off:
                        p.setColor(Color.WHITE);
                        break;
                    case Dot.status_on:
                        p.setColor(Color.YELLOW);
                        break;
                    case Dot.status_in:
                        p.setColor(Color.RED);
                        break;
                    default:
                        break;
                }
                c.drawOval(new RectF(one.getX()*width+offset,one.getY()*height,
                        (one.getX()+1)*width+offset,(one.getY()+1)*height
                        ),p);
            }
        }
        getHolder().unlockCanvasAndPost(c);
    }

}
