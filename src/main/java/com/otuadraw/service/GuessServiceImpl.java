package com.otuadraw.service;

import com.google.gson.Gson;
import com.otuadraw.data.model.InkTrail;
import com.otuadraw.enums.ShapeEnum;
import com.otuadraw.service.interfaces.GuessService;
import com.otuadraw.util.QuickDrawUtil;
import okhttp3.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
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

        Response response = client.newCall(request).execute();
        ResponseBody responseBody = response.body();

        List<String> bestGuesses = quickDrawUtil.getBestGuesses(responseBody.string());

        LOGGER.log(Level.INFO, "the best guesses from Google are {}", new Gson().toJson(bestGuesses));

        if(bestGuesses.contains("circle")){
            return ShapeEnum.CIRCLE;
        }else if(bestGuesses.contains("triangle")){
            return ShapeEnum.TRIANGLE;
        }else if(bestGuesses.contains("square")){
            return ShapeEnum.SQUARE;
        }else return ShapeEnum.ELSE;
    }
}
