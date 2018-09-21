package com.otuadraw.ui.main;

import com.otuadraw.data.model.InkPoint;
import com.otuadraw.data.model.InkTrail;
import com.otuadraw.util.MillisecondUtil;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainController {
    public Canvas canvas;

    private GraphicsContext graphicsContext = null;
    private InkTrail trail = new InkTrail();
    private MillisecondUtil millisecondUtil = null;

    private final static Logger LOGGER = LogManager.getLogger(MainController.class.getName());

    public void initialize() {
        graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setLineWidth(5);
    }

    public void onMousePressed(MouseEvent mouseEvent) {
        if(millisecondUtil == null){
            millisecondUtil = new MillisecondUtil();
        }
        graphicsContext.beginPath();

        final double x = mouseEvent.getX(), y = mouseEvent.getY();
        InkPoint point = getInkPoint(x, y, millisecondUtil);
        trail.push(point);
        LOGGER.log(Level.INFO, "brush strokes ( {} , {} ), timestamp is {}, mouse presses", point.getX(),
                point.getY(), point.getTime());
        graphicsContext.moveTo(x, y);
        graphicsContext.stroke();
    }

    public void onMouseDragged(MouseEvent mouseEvent) {
        final double x = mouseEvent.getX(), y = mouseEvent.getY();
        InkPoint point = getInkPoint(x, y, millisecondUtil);
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
        InkPoint point = getInkPoint(x, y, millisecondUtil);
        trail.push(point);
        LOGGER.log(Level.INFO, "brush strokes ( {} , {} ), timestamp is {}, mouse releases", point.getX(),
                point.getY(), point.getTime());
        graphicsContext.lineTo(x, y);
        graphicsContext.stroke();
        graphicsContext.closePath();
    }

    private InkPoint getInkPoint(double x, double y, MillisecondUtil u){
        final Integer xPos = (int)Math.floor(x), yPos = (int)Math.floor(y);
        final Long milliseconds = u.getStrokeTIme();
        InkPoint p = new InkPoint(xPos, yPos ,milliseconds);
        return p;
    }
}
