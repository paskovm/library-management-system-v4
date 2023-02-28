package com.metodipaskov.menu.user.interaction;

import com.metodipaskov.entities.Library;
import com.metodipaskov.entities.actors.Clerk;
import com.metodipaskov.entities.actors.Person;
import com.metodipaskov.menu.help.AddUpdateCheckUserMenu;

public class CheckClerkInfoMenu extends AddUpdateCheckUserMenu {

    private Library library = Library.getInstance();

    @Override
    public void start() {
        printMenuHeader();
        Person user = getUser();

        if (user != null) {
            if (user instanceof Clerk) {
                user.printInfo();
            } else {
                System.out.println("User you requested to verify is not a clerk!");
            }
        }

        library.getMainMenu().start();
    }

    @Override
    public void printMenuHeader() {
        System.out.println(System.lineSeparator() +
                            "----------------------------------------------------");
        System.out.println("================  Clerk Info Portal  ===============");
        System.out.println("----------------------------------------------------");
    }
}
