package com.otuadraw.ui.main;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

public class MainController {
    public Canvas canvas;

    public void onMousePressed(MouseEvent mouseEvent) {
        final GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.beginPath();
        graphicsContext.moveTo(mouseEvent.getX(), mouseEvent.getY());
        graphicsContext.stroke();
    }

    public void onMouseDragged(MouseEvent mouseEvent) {
        final GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.lineTo(mouseEvent.getX(), mouseEvent.getY());
        graphicsContext.stroke();
        graphicsContext.closePath();
        graphicsContext.beginPath();
        graphicsContext.moveTo(mouseEvent.getX(), mouseEvent.getY());
    }

    public void onMouseReleased(MouseEvent mouseEvent) {
        final GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.lineTo(mouseEvent.getX(), mouseEvent.getY());
        graphicsContext.stroke();
        graphicsContext.closePath();
    }
}
