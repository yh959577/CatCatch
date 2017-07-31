package com.cc.yh.cat;

/**
 * Created by Administrator on 2017/7/28.
 */

public class Dot {
    private int x;
    private int y;
    private int status;
    public static final int status_off=0;
    public static final int status_on=1;
    public static final int status_in=6;

    public Dot(int x,int y){
        this.x=x;
        this.y=y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getStatus() {
        return status;
    }

    public Dot setStatus(int status) {
        this.status = status;
        return this;
    }

    public static int getStatus_off() {
        return status_off;
    }

    public static int getStatus_on() {
        return status_on;
    }

    public static int getStatus_in() {
        return status_in;
    }

    public void setXY(int x,int y){
        this.x=x;
        this.y=y;
    }
}
