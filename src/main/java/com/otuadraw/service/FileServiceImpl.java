package com.otuadraw.service;

import com.otuadraw.service.interfaces.FileService;

public class FileServiceImpl implements FileService {
    private static FileServiceImpl ourInstance = new FileServiceImpl();

    public static FileServiceImpl getInstance() {
        return ourInstance;
    }

    private FileServiceImpl() {
    }
}
