package com.otuadraw.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InkTrail {
    private static int INTERVAL = 10;

    private List<Integer> xList = new ArrayList<>();
    private List<Integer> yList = new ArrayList<>();
    private List<Long> tList = new ArrayList<>();
    private int trailLen = 0;

    public synchronized void push(InkPoint inkPoint) {
        if(trailLen != 0){
            if(inkPoint.getTime() < tList.get(trailLen - 1) + INTERVAL){
                return;
            }
        }
        xList.add(inkPoint.getX());
        yList.add(inkPoint.getY());
        tList.add(inkPoint.getTime());
        trailLen += 1;
    }
}
