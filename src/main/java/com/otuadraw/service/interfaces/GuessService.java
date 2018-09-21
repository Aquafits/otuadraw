package com.otuadraw.service.interfaces;

import com.otuadraw.data.model.InkTrail;
import com.otuadraw.enums.ShapeEnum;

import java.io.IOException;

public interface GuessService {
    ShapeEnum guessTrail(InkTrail trail, double width, double height) throws IOException;
}
