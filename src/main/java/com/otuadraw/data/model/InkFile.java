package com.otuadraw.data.model;

import com.otuadraw.enums.ShapeEnum;
import lombok.Getter;
import lombok.Setter;

public class InkFile {
    private InkTrail inkTrail;

    @Getter
    @Setter
    private ShapeEnum guess;

    @Getter
    @Setter
    private boolean isTempFile;

    @Getter
    @Setter
    private boolean isDirty;

    public InkFile() {
        inkTrail = new InkTrail();
        isTempFile = true;
    }

    public void clear(){
        inkTrail = new InkTrail();
        guess = null;
    }

    public InkTrail getInkTrail(){
        return inkTrail.copy();
    }

    public void append(InkPoint inkPoint){
        inkTrail.push(inkPoint);
    }
}
