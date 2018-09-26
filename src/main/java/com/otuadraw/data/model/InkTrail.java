package com.otuadraw.data.model;

import com.google.gson.Gson;
import com.otuadraw.enums.PointEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;
import java.util.function.Consumer;

@NoArgsConstructor
@AllArgsConstructor
public class InkTrail{

    @Getter
    @Setter
    private List<Integer> xList = new ArrayList<>();

    @Getter
    @Setter
    private List<Integer> yList = new ArrayList<>();

    @Getter
    @Setter
    private List<Long> tList = new ArrayList<>();

    @Getter
    @Setter
    private List<PointEnum> types = new ArrayList<>();

    @Getter
    private int trailLen = 0;

    @Getter
    private Long lastUpdateTime = 0L;

    void push(InkPoint inkPoint) {
        xList.add(inkPoint.getX());
        yList.add(inkPoint.getY());
        tList.add(inkPoint.getTime());
        types.add(inkPoint.getPointType());
        lastUpdateTime = inkPoint.getTime();
        trailLen += 1;
    }

    public InkPoint get(int i){
        return new InkPoint(xList.get(i), yList.get(i), tList.get(i), types.get(i));
    }

    InkTrail copy() {
        Gson gson = new Gson();
        String jsonString = gson.toJson(this);
        return gson.fromJson(jsonString, InkTrail.class);
    }
}
