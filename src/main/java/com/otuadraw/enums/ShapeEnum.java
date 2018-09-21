package com.otuadraw.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ShapeEnum {
    CIRCLE("circle","圆圈"),
    TRIANGLE("triangle","三角"),
    SQUARE("square","正方形"),
    ELSE("else","其他");

    @Getter
    private String engName;

    @Getter
    private String chnName;
}
