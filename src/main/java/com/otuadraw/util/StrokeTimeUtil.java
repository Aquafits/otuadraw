package com.otuadraw.util;

public class StrokeTimeUtil {
    private Long startTime = null;

    public StrokeTimeUtil() {
        this.startTime = System.currentTimeMillis();
    }

    public StrokeTimeUtil(Long gap) {
        this.startTime = System.currentTimeMillis() - gap;
    }

    public Long getStrokeTIme() {
        return System.currentTimeMillis() - startTime;
    }
}
