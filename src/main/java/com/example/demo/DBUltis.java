package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import java.io.IOException;
import java.nio.channels.ConnectionPendingException;
import java.sql.*;
import java.util.Stack;

public class DBUltis {
    public static  void changeScene(ActionEvent event, String fxmlFile, String title, String username){
        Parent root = null;
        if (username != null){
           try {
               FXMLLoader loader = new FXMLLoader(DBUltis.class.getResource(fxmlFile));
               root = loader.load();
               LoggedInController loggedInController = loader.getController();
               loggedInController.setUserInformation(username);
           } catch (IOException e){
               e.printStackTrace();
           }
        }
        else {
            try {
                root = FXMLLoader.load(DBUltis.class.getResource(fxmlFile));
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root,500,400));
        stage.show();
    }
    public static void LogInUser(ActionEvent event,String username, String password){
        Connection connection = null;
        PreparedStatement preparedStatement =  null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/QLDV_app", "root","admin" );
            preparedStatement = connection.prepareStatement(" SELECT password FROM users WHERE username= ?");
            preparedStatement.setString(1,username);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.isBeforeFirst()){
                System.out.println("Không tìm thấy người dùng trong cơ sở dữ liệu");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Thông tin đăng nhập không đúng!");
                alert.show();
            }else {
                while (resultSet.next()){
                    String retrievedPassword = resultSet.getString("password");
                    if (retrievedPassword.equals(password)){
                        changeScene(event,"logged-in.fxml", "Welcome!",username);
                    }else {
                        System.out.println("Mật khẩu không đúng!");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Thông tin đăng nhập không đúng!");
                        alert.show();
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (resultSet != null){
                try {
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null){
                try {
                    preparedStatement.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (connection != null){
                try {
                    connection.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
