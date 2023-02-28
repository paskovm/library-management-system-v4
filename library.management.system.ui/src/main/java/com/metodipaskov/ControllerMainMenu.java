package com.metodipaskov;


import com.metodipaskov.entities.Library;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerMainMenu {

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    AnchorPane mainPane;

    public void logIn(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("logIn.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void exit(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("LogOut");
        alert.setHeaderText("Are you sure you want to exit?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            stage = (Stage) mainPane.getScene().getWindow();
            stage.close();
        }
    }
}