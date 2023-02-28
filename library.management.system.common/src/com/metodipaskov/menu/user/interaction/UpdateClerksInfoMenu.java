package com.metodipaskov.menu.user.interaction;

import com.metodipaskov.entities.Library;
import com.metodipaskov.entities.actors.Clerk;
import com.metodipaskov.entities.actors.Person;
import com.metodipaskov.menu.help.AddUpdateCheckUserMenu;

public class UpdateClerksInfoMenu extends AddUpdateCheckUserMenu {

    private Library library = Library.getInstance();

    @Override
    public void start() {
        printMenuHeader();
        Person clerk = getUser();

        if (clerk != null) {
            if (clerk instanceof Clerk) {
                updateUser(clerk);
            } else {
                System.out.println("The user you are trying to update is not clerk!");
                clerk.printInfo();
            }
        }

        library.getMainMenu().start();
    }

    @Override
    public void printMenuHeader() {
        System.out.println(System.lineSeparator() +
                            "----------------------------------------------------");
        System.out.println("============  Update Clerks Info Portal  ===========");
        System.out.println("----------------------------------------------------");
    }
}
