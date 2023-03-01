package com.metodipaskov;

import com.metodipaskov.entities.Library;
import com.metodipaskov.entities.actors.Borrower;
import com.metodipaskov.entities.actors.Clerk;
import com.metodipaskov.entities.actors.Librarian;
import com.metodipaskov.entities.actors.Person;
import com.metodipaskov.services.UserManagementService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerListAllUsers implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private Library library = Library.getInstance();
    private UserManagementService userService = UserManagementService.getInstance();
    private ObservableList<Person> listPerson = FXCollections.observableArrayList();
    @FXML
    private ListView<Person> listAll;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Person> users = userService.getUsers();
        for (Person user : users) {
            listPerson.add(user);
        }
        listAll.setItems(listPerson);
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
