package com.metodipaskov.menu.user.interaction;

import com.metodipaskov.entities.Library;
import com.metodipaskov.menu.help.AddUpdateCheckUserMenu;

public class AddBorrowerMenu extends AddUpdateCheckUserMenu {

    private Library library = Library.getInstance();

    @Override
    public void start() {
        printMenuHeader();
        createUser("Borrower");

        library.getMainMenu().start();
    }

    @Override
    public void printMenuHeader() {
        System.out.println(System.lineSeparator() +
                            "----------------------------------------------------");
        System.out.println("=============  Add New Borrower Portal  ============");
        System.out.println("----------------------------------------------------");
    }
}
