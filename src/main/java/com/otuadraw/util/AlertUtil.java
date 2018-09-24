package com.otuadraw.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

public class AlertUtil {

    public static boolean warn(String title, String header, String content, boolean neglectful){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        if(neglectful){
            ButtonType neglect = new ButtonType("忽略");
            ButtonType confirm = new ButtonType("取消",ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(confirm, neglect);
            return neglect == alert.showAndWait().get();
        }else{
            alert.showAndWait();
        }
        return false;

    }
}
