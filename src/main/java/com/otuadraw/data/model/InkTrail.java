package com.otuadraw.data.model;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;
import java.util.function.Consumer;

@NoArgsConstructor
@AllArgsConstructor
public class InkTrail implements Iterable<InkPoint>{

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
    private int trailLen = 0;

    @Getter
    private Long lastUpdateTime = 0L;

    void push(InkPoint inkPoint) {
        xList.add(inkPoint.getX());
        yList.add(inkPoint.getY());
        tList.add(inkPoint.getTime());
        lastUpdateTime = inkPoint.getTime();
        trailLen += 1;
    }

    public InkPoint get(int i){
        return new InkPoint(xList.get(i), yList.get(i), tList.get(i));
    }

    InkTrail copy() {
        Gson gson = new Gson();
        String jsonString = gson.toJson(this);
        return gson.fromJson(jsonString, InkTrail.class);
    }

    @Override
    public Iterator<InkPoint> iterator() {
        return new InkTrailIterator();
    }

    private class InkTrailIterator implements Iterator<InkPoint>{
        private int pointer = 0;

        @Override
        public boolean hasNext() {
            return pointer < trailLen-1;
        }

        @Override
        public InkPoint next() {
            return new InkPoint(xList.get(pointer), yList.get(pointer), tList.get(pointer));
        }

    }
}
