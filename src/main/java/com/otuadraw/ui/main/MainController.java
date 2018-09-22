package com.otuadraw.ui.main;

import com.otuadraw.data.model.InkFile;
import com.otuadraw.data.model.InkPoint;
import com.otuadraw.data.model.InkTrail;
import com.otuadraw.enums.ShapeEnum;
import com.otuadraw.service.factory.ServiceFactory;
import com.otuadraw.service.interfaces.FileService;
import com.otuadraw.service.interfaces.GuessService;
import com.otuadraw.util.DateFormatUtil;
import com.otuadraw.util.StrokeTimeUtil;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

public class MainController {
    public Canvas canvas;
    public Text guessText;

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final static Logger LOGGER = LogManager.getLogger(MainController.class.getName());

    private GraphicsContext graphicsContext = null;
    private StrokeTimeUtil strokeTimeUtil = null;
    private InkFile inkFile = new InkFile();

    public void initialize() {
        graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setLineWidth(5);
    }

    public void onMousePressed(MouseEvent mouseEvent) {
        if (strokeTimeUtil == null) {
            strokeTimeUtil = new StrokeTimeUtil();
        }
        graphicsContext.beginPath();

        final double x = mouseEvent.getX(), y = mouseEvent.getY();
        InkPoint point = getInkPoint(x, y, strokeTimeUtil);
        inkFile.append(point);
        LOGGER.log(Level.INFO, "brush strokes ( {} , {} ), timestamp is {}, mouse presses", point.getX(),
                point.getY(), point.getTime());
        graphicsContext.moveTo(x, y);
        graphicsContext.stroke();
    }

    public void onMouseDragged(MouseEvent mouseEvent) {
        final double x = mouseEvent.getX(), y = mouseEvent.getY();
        InkPoint point = getInkPoint(x, y, strokeTimeUtil);
        inkFile.append(point);
        LOGGER.log(Level.INFO, "brush strokes ( {} , {} ), timestamp is {}, mouse drags", point.getX(),
                point.getY(), point.getTime());
        graphicsContext.lineTo(x, y);
        graphicsContext.stroke();
        graphicsContext.closePath();
        graphicsContext.beginPath();
        graphicsContext.moveTo(x, y);
    }

    public void onMouseReleased(MouseEvent mouseEvent) {
        final double x = mouseEvent.getX(), y = mouseEvent.getY();
        InkPoint point = getInkPoint(x, y, strokeTimeUtil);
        inkFile.append(point);
        LOGGER.log(Level.INFO, "brush strokes ( {} , {} ), timestamp is {}, mouse releases", point.getX(),
                point.getY(), point.getTime());
        graphicsContext.lineTo(x, y);
        graphicsContext.stroke();
        graphicsContext.closePath();
    }

    public void guessTrail() {
        GuessService guessService = serviceFactory.getGuessService();
        try {
            ShapeEnum bestGuess = guessService.guessTrail(inkFile.getInkTrail(), canvas.getWidth(), canvas.getHeight());
            inkFile.setGuess(bestGuess);
            LOGGER.log(Level.INFO, "the program takes the best guess as {}, chinese translation is {}",
                    bestGuess.getEngName(), bestGuess.getChnName());
            updateGuess(bestGuess.getChnName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clearCanvas() {
        graphicsContext.clearRect(0.0, 0.0, canvas.getWidth(), canvas.getHeight());
        strokeTimeUtil = null;
        inkFile.clear();
        LOGGER.log(Level.INFO, "Canvas cleared");
    }

    public void createFile() {
        FileService fileService = serviceFactory.getFileService();

    }

    public void openFile() {
        if (inkFile.isTempFile() || inkFile.isDirty()) {
            //TODO UI提示保存
        }

        FileService fileService = serviceFactory.getFileService();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("打开你的涂鸦");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("OtuaDraw drawing file", "*.otua")
        );
        File jsonFile = fileChooser.showOpenDialog(new Stage());
        if (jsonFile != null) {
            try {
                clearCanvas();
                inkFile = fileService.openFile(jsonFile);
                Long gap = inkFile.getInkTrail().getLastUpdateTime();
                strokeTimeUtil = new StrokeTimeUtil(gap);
                showOnCanvas(inkFile.getInkTrail());
                updateGuess(inkFile.getGuess().getChnName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveFile() {
        FileService fileService = serviceFactory.getFileService();
        try {
            if (inkFile.isTempFile()) {
                saveFileAs();
            } else {
                fileService.saveFile(inkFile);
            }
        } catch (IOException e) {
            //TODO UI提示 文件保存失败
            e.printStackTrace();
        }
    }

    public void saveFileAs() {
        FileService fileService = serviceFactory.getFileService();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("保存你的涂鸦");
        fileChooser.setInitialFileName("涂鸦 " + DateFormatUtil.formatDateTimeString(System.currentTimeMillis()) + ".otua");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("OtuaDraw drawing file", "*.otua")
        );
        File jsonFile = fileChooser.showSaveDialog(new Stage());

        if (jsonFile != null) {
            try {
                fileService.saveFile(inkFile, jsonFile);
            } catch (IOException e) {
                //TODO UI提示 文件保存失败
                e.printStackTrace();
            }
        }

    }

    private InkPoint getInkPoint(double x, double y, StrokeTimeUtil s) {
        final Integer xPos = (int) Math.floor(x), yPos = (int) Math.floor(y);
        final Long milliseconds = s.getStrokeTIme();
        return new InkPoint(xPos, yPos, milliseconds);
    }

    private void showOnCanvas(InkTrail inkTrail) {
        for (int i = 0; i < inkTrail.getTrailLen(); i++) {
            InkPoint p = inkTrail.get(i);
            if (i == 0) {
                graphicsContext.beginPath();
                graphicsContext.moveTo(p.getX(), p.getY());
                graphicsContext.stroke();
            } else if (i == inkTrail.getTrailLen() - 1) {
                graphicsContext.lineTo(p.getX(), p.getY());
                graphicsContext.stroke();
                graphicsContext.closePath();
            } else {
                graphicsContext.lineTo(p.getX(), p.getY());
                graphicsContext.stroke();
                graphicsContext.closePath();
                graphicsContext.beginPath();
                graphicsContext.moveTo(p.getX(), p.getY());
            }
        }
    }

    private void updateGuess(String guess){
        guessText.setText("我猜这是" + guess + "!");
    }
}
