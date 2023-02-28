package com.metodipaskov.menu.user.interaction;

import com.metodipaskov.entities.Library;
import com.metodipaskov.menu.help.AddUpdateCheckUserMenu;

public class AddClerkMenu extends AddUpdateCheckUserMenu {

    private Library library = Library.getInstance();

    @Override
    public void start() {
        printMenuHeader();
        createUser("Clerk");

        library.getMainMenu().start();
    }

    @Override
    public void printMenuHeader() {
        System.out.println(System.lineSeparator() +
                            "----------------------------------------------------");
        System.out.println("==============  Add New Clerk Portal  ==============");
        System.out.println("----------------------------------------------------");
    }
}
