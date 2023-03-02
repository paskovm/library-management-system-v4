package com.metodipaskov;

import com.metodipaskov.entities.Book;
import com.metodipaskov.entities.Library;
import com.metodipaskov.entities.actors.Borrower;
import com.metodipaskov.entities.actors.Clerk;
import com.metodipaskov.entities.actors.Librarian;
import com.metodipaskov.entities.actors.Person;
import com.metodipaskov.services.BookManagementService;
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

public class ControllerListAllBooks implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private Library library = Library.getInstance();
    private BookManagementService bookService = BookManagementService.getInstance();
    private ObservableList<Book> listBook = FXCollections.observableArrayList();
    @FXML
    private ListView<Book> bookListView;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Book> books = bookService.getAllBooksInLibrary();
        for (Book bk : books) {
            listBook.add(bk);
        }

        bookListView.setItems(listBook);
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
