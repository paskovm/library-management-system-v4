package com.metodipaskov;

import com.metodipaskov.entities.Library;

public class ControllerLogInMenu {

    private Library library = Library.getInstance();
    private static final String ADMIN_LOGIN_NAME = "admin";
    private static final String ADMIN_LOGIN_PASSWORD = "admin";




    private static boolean isAdmin(String email, String password) {
        if (ADMIN_LOGIN_NAME.equals(email) && ADMIN_LOGIN_PASSWORD.equals(password)) {
            return true;
        }
        return false;
    }
}
