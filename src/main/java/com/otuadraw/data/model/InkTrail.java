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
    private List<Integer> xList = new ArrayList<>();
    private List<Integer> yList = new ArrayList<>();
    private List<Long> tList = new ArrayList<>();

    public synchronized boolean push(InkPoint inkPoint) {
        xList.add(inkPoint.getX());
        yList.add(inkPoint.getY());
        tList.add(inkPoint.getT());
        return true;
    }
}
