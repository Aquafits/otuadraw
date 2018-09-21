package com.otuadraw.util;

public class StrokeTimeUtil {
    private Long startTime = null;

    public StrokeTimeUtil() {
        this.startTime = System.currentTimeMillis();
    }

    public Long getStrokeTIme(){
        return System.currentTimeMillis()-startTime;
    }
}
