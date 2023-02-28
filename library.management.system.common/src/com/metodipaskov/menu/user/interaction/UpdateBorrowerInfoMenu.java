package com.metodipaskov.menu.user.interaction;

import com.metodipaskov.entities.Library;
import com.metodipaskov.entities.actors.Person;
import com.metodipaskov.menu.help.AddUpdateCheckUserMenu;

public class UpdateBorrowerInfoMenu extends AddUpdateCheckUserMenu {

    private Library library = Library.getInstance();

    @Override
    public void start() {
        printMenuHeader();

        Person user = getUser();
        if (user != null) {
            updateUser(user);
        }

        library.getMainMenu().start();
    }

    @Override
    public void printMenuHeader() {
        System.out.println(System.lineSeparator() +
                            "----------------------------------------------------");
        System.out.println("=============  Update Borrower Portal  =============");
        System.out.println("----------------------------------------------------");
    }
}
