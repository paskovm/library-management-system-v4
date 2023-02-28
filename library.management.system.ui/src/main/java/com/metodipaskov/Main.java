package com.metodipaskov;

import com.metodipaskov.utils.PopulateLibrary;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;


/*
 * Admin:
 * admin, admin
 *
 * Librarian:
 * "emcneilly0@wordpress.com", "UOIBTReU1L"
 *
 * Clerk:
 * "lsoloway1@cam.ac.uk", "e9gIIOTBFcK"
 *
 * Borrower:
 * "glicciardi9@a8.net", "aPxZI4vPn"
 * */


public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        PopulateLibrary.populate();

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Library Management System");
        Image icon = new Image(getClass().getResourceAsStream("lib_main.jpg"));
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(event -> {
            event.consume();
            exit(stage);
        });

    }

    private void exit(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("LogOut");
        alert.setHeaderText("Are you sure you want to exit?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            stage.close();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}