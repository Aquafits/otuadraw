package com.otuadraw.service.interfaces;

import com.otuadraw.data.model.InkTrail;

import java.io.IOException;

public interface GuessService {
    String guessTrail(InkTrail trail, double width, double height) throws IOException;
}
