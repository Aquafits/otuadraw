package com.otuadraw.service;

import com.google.gson.Gson;
import com.otuadraw.data.model.InkFile;
import com.otuadraw.service.interfaces.FileService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

public class FileServiceImpl implements FileService {

    private static FileServiceImpl ourInstance = new FileServiceImpl();

    public static FileServiceImpl getInstance() {
        return ourInstance;
    }

    private FileServiceImpl() {
    }

    private final static Logger LOGGER = LogManager.getLogger(FileServiceImpl.class.getName());
    private File currentJsonFile = null;

    @Override
    public void createFile() {
        currentJsonFile = null;
    }

    @Override
    public boolean saveFile(InkFile inkFile) throws IOException {
        if(currentJsonFile != null){
            return saveFile(inkFile, currentJsonFile);
        }else return false;
    }

    @Override
    public boolean saveFile(InkFile inkFile, File jsonFile) throws IOException {
        if (!jsonFile.exists()) {
            if (!jsonFile.createNewFile()) {
                LOGGER.log(Level.ERROR, "cannot create new file");
                return false;
            }
        }
        inkFile.setTempFile(false);
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(jsonFile));
        String jsonString = new Gson().toJson(inkFile);
        bufferedWriter.write(jsonString);
        bufferedWriter.close();
        return true;
    }

    @Override
    public InkFile openFile(File jsonFile) throws IOException {
        currentJsonFile = jsonFile;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(jsonFile));
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        String jsonString = stringBuilder.toString();
        return new Gson().fromJson(jsonString, InkFile.class);
    }
}
