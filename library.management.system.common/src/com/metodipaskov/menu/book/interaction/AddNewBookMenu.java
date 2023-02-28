package com.metodipaskov.menu.book.interaction;

import com.metodipaskov.entities.Library;
import com.metodipaskov.menu.help.AddUpdateCheckBookMenu;

public class AddNewBookMenu extends AddUpdateCheckBookMenu {

    private Library library = Library.getInstance();

    @Override
    public void start() {
        printMenuHeader();
        createBook();

        library.getMainMenu().start();
    }

    @Override
    public void printMenuHeader() {
        System.out.println(System.lineSeparator() +
                            "----------------------------------------------------");
        System.out.println("===============  Add New Book Portal  ==============");
        System.out.println("----------------------------------------------------");
    }
}
