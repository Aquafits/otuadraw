package com.otuadraw.service;

import com.google.gson.Gson;
import com.otuadraw.data.model.InkTrail;
import com.otuadraw.enums.ShapeEnum;
import com.otuadraw.service.interfaces.GuessService;
import com.otuadraw.ui.main.Main;
import com.otuadraw.util.AlertUtil;
import com.otuadraw.util.QuickDrawUtil;
import okhttp3.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;

public class GuessServiceImpl implements GuessService {
    private static GuessServiceImpl ourInstance = new GuessServiceImpl();

    private final static Logger LOGGER = LogManager.getLogger(GuessServiceImpl.class.getName());

    public static GuessServiceImpl getInstance() {
        return ourInstance;
    }

    private GuessServiceImpl() {
    }

    @Override
    public ShapeEnum guessTrail(InkTrail trail, double width, double height) throws IOException {
        QuickDrawUtil quickDrawUtil = new  QuickDrawUtil();
        String payload = new QuickDrawUtil().getQuickDrawPayLoad(trail, (int)Math.floor(width),(int)Math.floor(height));

        LOGGER.log(Level.INFO, "payload to post is {}", payload);

        OkHttpClient client = new OkHttpClient.Builder()
                .proxy(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", 1080)))
                .build();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, payload);
        Request request = new Request.Builder()
                .url("https://inputtools.google.com/request?ime=handwriting&app=quickdraw&dbg=1&cs=1&oe=UTF-8")
                .post(body)
                .addHeader("Accept", "*/*")
                .addHeader("Content-Type", "application/json")
                .build();
        try {
            Response response = client.newCall(request).execute();
            ResponseBody responseBody = response.body();

            List<String> bestGuesses = quickDrawUtil.getBestGuesses(responseBody.string());

            LOGGER.log(Level.INFO, "the best guesses from Google are {}", new Gson().toJson(bestGuesses));

            int circleIndex = getGuessIndex(bestGuesses, "circle");
            int triangleIndex = getGuessIndex(bestGuesses, "triangle");
            int squareIndex = getGuessIndex(bestGuesses, "square");

            int min = Math.min(Math.min(circleIndex, triangleIndex), squareIndex);

            if (min == circleIndex) {
                return ShapeEnum.CIRCLE;
            } else if (min == triangleIndex) {
                return ShapeEnum.TRIANGLE;
            } else if (min == squareIndex) {
                return ShapeEnum.SQUARE;
            } else return ShapeEnum.ELSE;
        }catch (Exception e){
            AlertUtil.warn("提示","网络条件差",null,false);
        }
        return null;
    }

    private int getGuessIndex(List<String> bestGuesses, String string){
        int ret = bestGuesses.indexOf(string);
        if(ret<0){
            ret = Integer.MAX_VALUE;
        }
        return ret;

    }
}
