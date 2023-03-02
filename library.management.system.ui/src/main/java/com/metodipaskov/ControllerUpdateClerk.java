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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerUpdateClerk {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private Library library = Library.getInstance();
    private UserManagementService userService = UserManagementService.getInstance();
    @FXML
    private TextArea usersInfo;
    @FXML
    private TextField clerkDetails;
    private static Clerk clerkToUpdate;

    public void submitUserSearch() {
        String providedClerkInfo = clerkDetails.getText().trim();
        if (providedClerkInfo.isEmpty() || providedClerkInfo.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("The provided users information must not be empty or blank!");

            alert.showAndWait();
            usersInfo.clear();

        } else {
            Person user = null;
            if (providedClerkInfo.matches("^[A-Za-z].*\\s[A-Za-z].*")) { // this must be users full name
                user = userService.getUserByFullName(providedClerkInfo);

            } else if (providedClerkInfo.contains("@")) { // this must be users email address
                user = userService.getUserByEmail(providedClerkInfo);

            } else { // this must be the users id
                try {
                    int id = Integer.parseInt(providedClerkInfo);
                    user = userService.getUserById(id);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }

            if (user == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("There is no user with id, name or email: \"" + providedClerkInfo + "\" registered into the system.");

                alert.showAndWait();
                usersInfo.clear();
            } else {
                if (user instanceof Clerk) {
                    usersInfo.setText(user.toString());
                    clerkToUpdate = (Clerk) user;

                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("The user you are trying to update is not clerk!");

                    alert.showAndWait();
                    usersInfo.clear();
                }
            }
        }
    }

    public void goToUpdate(ActionEvent event) throws IOException {
        if (clerkToUpdate != null) {
            root = FXMLLoader.load(getClass().getResource("updateUser.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);

            stage.setUserData(clerkToUpdate);

            stage.show();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Before continuing to update user you must select one first!");

            alert.showAndWait();
        }
    }

    public static void resetClerkToUpdate() {
        clerkToUpdate = null;
    }

    public static Clerk getClerkToUpdate() {
        return clerkToUpdate;
    }

    public void clearUserToUpdate() {
        clerkToUpdate = null;
        usersInfo.clear();
        clerkDetails.clear();
    }

    public void goBack(ActionEvent event) throws IOException {
        String resource = "";
        Person user = library.getLoggedInPerson();

        if (user instanceof Librarian) {
            resource = "librarian.fxml";
        } else if (user instanceof Clerk) {
            resource = "clerk.fxml";
        } else if (user instanceof Borrower) {
            resource = "borrower.fxml";
        } else { // this must be the admin
            resource = "admin.fxml";
        }

        if (!resource.isEmpty()) {
            root = FXMLLoader.load(getClass().getResource(resource));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
}
