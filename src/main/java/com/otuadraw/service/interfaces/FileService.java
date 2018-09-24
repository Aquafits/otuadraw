package com.otuadraw.service.interfaces;

import com.otuadraw.data.model.InkFile;

import java.io.File;
import java.io.IOException;

public interface FileService {
    void createFile();

    boolean saveFile(InkFile inkFile) throws IOException;

    boolean saveFile(InkFile inkFile, File jsonFile) throws IOException;

    InkFile openFile(File jsonFile) throws IOException;
}
