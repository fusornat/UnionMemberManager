package com.example.demo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class LoggedInController implements Initializable {
    @FXML
    private Button button_logout;
    @FXML
    private Label label_welcome;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        button_logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            DBUltis.changeScene(event, "hello-view", "Hệ thống quản lý đoàn viên", null);
            }
        });

    }

    public void setUserInformation(String username){
    label_welcome.setText("Chào"+ username + "!");
    }
}
