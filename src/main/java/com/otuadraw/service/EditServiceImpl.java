package com.otuadraw.service;

import com.otuadraw.service.interfaces.EditService;

public class EditServiceImpl implements EditService {
    private static EditServiceImpl ourInstance = new EditServiceImpl();

    public static EditServiceImpl getInstance() {
        return ourInstance;
    }

    private EditServiceImpl() {
    }
}
