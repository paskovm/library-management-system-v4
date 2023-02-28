package com.metodipaskov.menu.user.interaction;

import com.metodipaskov.entities.Library;
import com.metodipaskov.entities.actors.Person;
import com.metodipaskov.menu.Menu;
import com.metodipaskov.services.UserManagementService;

import java.util.List;

public class ViewAllUsersMenu implements Menu {

    private Library library = Library.getInstance();
    private UserManagementService userService = UserManagementService.getInstance();

    @Override
    public void start() {
        printMenuHeader();

        List<Person> users = userService.getUsers();
        for (Person user : users) {
            user.printInfo();
        }

        library.getMainMenu().start();
    }

    @Override
    public void printMenuHeader() {
        System.out.println(System.lineSeparator() +
                            "----------------------------------------------------");
        System.out.println("=========  All Users Registered In Library  ========");
        System.out.println("----------------------------------------------------");
    }
}
