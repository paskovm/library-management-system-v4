package com.metodipaskov;

import com.metodipaskov.entities.Library;
import com.metodipaskov.entities.actors.Borrower;
import com.metodipaskov.entities.actors.Clerk;
import com.metodipaskov.entities.actors.Librarian;
import com.metodipaskov.entities.actors.Person;
import com.metodipaskov.services.UserManagementService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerLogInMenu {

    private UserManagementService userService = UserManagementService.getInstance();
    private Library library = Library.getInstance();
    private static final String ADMIN_LOGIN_NAME = "admin";
    private static final String ADMIN_LOGIN_PASSWORD = "admin";
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void submit(ActionEvent event) throws IOException {
        StringBuilder fields = new StringBuilder();
        if (username.getText().isEmpty() || username.getText().isBlank()) {
            fields.append("username");
        }

        if (password.getText().isEmpty() || password.getText().isBlank()) {
            if (fields.length() > 0) {
                fields.append(", ");
            }
            fields.append("password");
        }

        if (fields.length() > 0) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("ERROR");
            errorAlert.setContentText("You provided empty value(s) for " + fields + ". Please, try again.");

            errorAlert.showAndWait();
            username.clear();
            password.clear();

        } else {
            if (isAdmin(username.getText(), password.getText())) {
                root = FXMLLoader.load(getClass().getResource("admin.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            } else {
                Person user = userService.getUserByEmail(username.getText());

                if (user == null) {
                    Alert noSuchUserAlert = new Alert(Alert.AlertType.INFORMATION);
                    noSuchUserAlert.setHeaderText("There is no registered user in the system with email: " +
                            username.getText() + " and the provided password.");
                    noSuchUserAlert.showAndWait();
                    username.clear();
                    password.clear();

                } else {
                    String resource = "";
                    if (user.getPassword().equals(password.getText())) {
                        if (user instanceof Librarian) {
                            resource = "librarian.fxml";
                        } else if (user instanceof Clerk) {
                            resource = "clerk.fxml";
                        } else if (user instanceof Borrower) {
                            resource = "borrower.fxml";
                        }
                    } else {
                        Alert wrongPasswordAlert = new Alert(Alert.AlertType.ERROR);
                        wrongPasswordAlert.setHeaderText("There is no registered user in the system with email: " +
                                username.getText() + " and the provided password.");

                        wrongPasswordAlert.showAndWait();
                        username.clear();
                        password.clear();
                    }

                    if (resource.length() > 0) {
                        library.setLoggedInPerson(user);

                        root = FXMLLoader.load(getClass().getResource(resource));
                        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    }

                }


            }
        }
    }

    private static boolean isAdmin(String email, String password) {
        if (ADMIN_LOGIN_NAME.equals(email) && ADMIN_LOGIN_PASSWORD.equals(password)) {
            return true;
        }
        return false;
    }
}
