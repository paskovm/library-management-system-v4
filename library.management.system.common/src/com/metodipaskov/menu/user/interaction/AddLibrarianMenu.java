package com.metodipaskov.menu.user.interaction;

import com.metodipaskov.entities.Library;
import com.metodipaskov.entities.actors.Librarian;
import com.metodipaskov.entities.actors.Person;
import com.metodipaskov.menu.help.AddUpdateCheckUserMenu;
import com.metodipaskov.services.UserManagementService;

import java.util.List;
import java.util.Scanner;

public class AddLibrarianMenu extends AddUpdateCheckUserMenu {

    private Library library = Library.getInstance();
    private UserManagementService userService = UserManagementService.getInstance();
    private Scanner scanner = new Scanner(System.in);

    @Override
    public void start() {
        printMenuHeader();

        Librarian librarian = null;
        List<Person> users = userService.getUsers();
        for (Person user : users) {
            if (user instanceof Librarian) {
                librarian = (Librarian) user;
                break;
            }
        }

        if (librarian != null) {
            System.out.println("Librarian already exists!");
            librarian.printInfo();

            while (true) {
                System.out.println(System.lineSeparator() + "Do you want to update the librarian info? y/n: ");
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("y")) {
                    updateUser(librarian);
                    break;
                } else if (input.equalsIgnoreCase("n")) {
                    break;

                } else {
                    System.out.println("Wrong choice provided! Please, try again.");
                }
            }
        } else {
            createUser("Librarian");
        }

        library.getMainMenu().start();
    }

    @Override
    public void printMenuHeader() {
        System.out.println(System.lineSeparator() +
                            "----------------------------------------------------");
        System.out.println("==============  Add Librarian Portal  ==============");
        System.out.println("----------------------------------------------------");
    }
}
