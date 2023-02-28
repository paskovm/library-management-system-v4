package com.metodipaskov.menu.user.interaction;

import com.metodipaskov.entities.Library;
import com.metodipaskov.entities.actors.Borrower;
import com.metodipaskov.entities.actors.Person;
import com.metodipaskov.menu.help.AddUpdateCheckUserMenu;

public class CheckBorrowerInfoMenu extends AddUpdateCheckUserMenu {

    private Library library = Library.getInstance();

    @Override
    public void start() {
        printMenuHeader();
        Person user = getUser();
        if (user != null) {
            if (user instanceof Borrower) {
                user.printInfo();
            } else {
                System.out.println("The user you provided is not borrower!");
            }
        }

        library.getMainMenu().start();
    }

    @Override
    public void printMenuHeader() {
        System.out.println(System.lineSeparator() +
                            "----------------------------------------------------");
        System.out.println("==============  Borrowers Info Portal  =============");
        System.out.println("----------------------------------------------------");
    }
}
