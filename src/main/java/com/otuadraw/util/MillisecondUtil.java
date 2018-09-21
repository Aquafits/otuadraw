package com.otuadraw.util;

public class MillisecondUtil {
    private Long startTime = null;

    public MillisecondUtil() {
        this.startTime = System.currentTimeMillis();
    }

    public Long getStrokeTIme(){
        return System.currentTimeMillis()-startTime;
    }
}
