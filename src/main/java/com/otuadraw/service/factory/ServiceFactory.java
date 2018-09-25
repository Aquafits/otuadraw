package com.otuadraw.service.factory;

import com.otuadraw.service.FileServiceImpl;
import com.otuadraw.service.GuessServiceImpl;
import com.otuadraw.service.interfaces.FileService;
import com.otuadraw.service.interfaces.GuessService;

public class ServiceFactory {
    private static ServiceFactory ourInstance = new ServiceFactory();

    public static ServiceFactory getInstance() {
        return ourInstance;
    }

    private ServiceFactory() {
    }

    public FileService getFileService(){
        return FileServiceImpl.getInstance();
    }

    public GuessService getGuessService(){
        return GuessServiceImpl.getInstance();
    }

}
