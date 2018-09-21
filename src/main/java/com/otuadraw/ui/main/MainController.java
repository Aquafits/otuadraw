package com.otuadraw.ui.main;

import com.otuadraw.data.model.InkPoint;
import com.otuadraw.data.model.InkTrail;
import com.otuadraw.enums.ShapeEnum;
import com.otuadraw.service.factory.ServiceFactory;
import com.otuadraw.service.interfaces.GuessService;
import com.otuadraw.util.StrokeTimeUtil;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class MainController {
    public Canvas canvas;

    private GraphicsContext graphicsContext = null;
    private InkTrail trail = new InkTrail();
    private StrokeTimeUtil strokeTimeUtil = null;
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();

    private final static Logger LOGGER = LogManager.getLogger(MainController.class.getName());

    public void initialize() {
        graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setLineWidth(5);
    }

    public void onMousePressed(MouseEvent mouseEvent) {
        if(strokeTimeUtil == null){
            strokeTimeUtil = new StrokeTimeUtil();
        }
        graphicsContext.beginPath();

        final double x = mouseEvent.getX(), y = mouseEvent.getY();
        InkPoint point = getInkPoint(x, y, strokeTimeUtil);
        trail.push(point);
        LOGGER.log(Level.INFO, "brush strokes ( {} , {} ), timestamp is {}, mouse presses", point.getX(),
                point.getY(), point.getTime());
        graphicsContext.moveTo(x, y);
        graphicsContext.stroke();
    }

    public void onMouseDragged(MouseEvent mouseEvent) {
        final double x = mouseEvent.getX(), y = mouseEvent.getY();
        InkPoint point = getInkPoint(x, y, strokeTimeUtil);
        trail.push(point);
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
        trail.push(point);
        LOGGER.log(Level.INFO, "brush strokes ( {} , {} ), timestamp is {}, mouse releases", point.getX(),
                point.getY(), point.getTime());
        graphicsContext.lineTo(x, y);
        graphicsContext.stroke();
        graphicsContext.closePath();
    }

    public String guessTrail(ActionEvent actionEvent) {
        GuessService guessService = serviceFactory.getGuessService();
        try {
            ShapeEnum bestGuess = guessService.guessTrail(trail, canvas.getWidth(), canvas.getHeight());
            LOGGER.log(Level.INFO, "The system takes the best guess as {}, chinese translation is {}",
                    bestGuess.getEngName(), bestGuess.getChnName());
            return bestGuess.getChnName();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private InkPoint getInkPoint(double x, double y, StrokeTimeUtil s){
        final Integer xPos = (int)Math.floor(x), yPos = (int)Math.floor(y);
        final Long milliseconds = s.getStrokeTIme();
        InkPoint p = new InkPoint(xPos, yPos ,milliseconds);
        return p;
    }
}
