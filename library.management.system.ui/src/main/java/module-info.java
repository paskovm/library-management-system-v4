module library.management.system.ui {
    requires javafx.controls;
    requires javafx.fxml;
    requires library.management.system.common;

    opens com.metodipaskov to javafx.fxml;
    exports com.metodipaskov;

}