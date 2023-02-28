package com.metodipaskov.menu.main;

import com.metodipaskov.entities.Library;
import com.metodipaskov.entities.actors.Borrower;
import com.metodipaskov.entities.actors.Clerk;
import com.metodipaskov.entities.actors.Librarian;
import com.metodipaskov.entities.actors.Person;
import com.metodipaskov.menu.Menu;
import com.metodipaskov.services.UserManagementService;

import java.util.List;
import java.util.Scanner;

public class LogInMenu implements Menu {

    private Library  library = Library.getInstance();
    private UserManagementService userService = UserManagementService.getInstance();
    private static final String ADMIN_LOGIN_NAME = "admin";
    private static final String ADMIN_LOGIN_PASSWORD = "admin";

    @Override
    public void start() {
        printMenuHeader();

        Menu menuToNavigate;
        Person user = null;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please, Enter your log in username(email): ");
        String email = scanner.nextLine();
        System.out.println("Please, enter your password: ");
        String password = scanner.nextLine();

        if (email.isEmpty() || email.isBlank() || password.isEmpty() || password.isBlank()) {
            System.out.println("You provided empty value(s) for email/password. Please, try again." + System.lineSeparator());
            menuToNavigate = library.getMainMenu();

        } else {
            if (isAdmin(email, password)) {
                menuToNavigate = new AdminMenu();

            } else {
                List<Person> users = userService.getUsers();

                for (Person person : users) {
                    if (person.getEmail().equals(email) && person.getPassword().equals(password)) {
                        user = person;
                    }
                }

                if (user != null) {
                    library.setLoggedInPerson(user);

                    if (user instanceof Librarian) {
                        menuToNavigate = new LibrarianMenu();
                    } else if (user instanceof Clerk) {
                        menuToNavigate = new ClerkMenu();
                    } else if (user instanceof Borrower) {
                        menuToNavigate = new BorrowerMenu();
                    } else {
                        System.out.println("Problem detected!. The user type can't be determined! " +
                                "Please, contact the system administrator!");
                        menuToNavigate = library.getMainMenu();
                    }

                } else {
                    System.out.println("There is no registered user in the system with email: " +
                            email + " and the provided password.");
                    menuToNavigate = library.getMainMenu();
                }
            }
        }
        menuToNavigate.start();
    }

    private static boolean isAdmin(String email, String password) {
        if (ADMIN_LOGIN_NAME.equals(email) && ADMIN_LOGIN_PASSWORD.equals(password)) {
            return true;
        }
        return false;
    }

    @Override
    public void printMenuHeader() {
        System.out.println(System.lineSeparator() +
                            "----------------------------------------------------");
        System.out.println("==================== Log In Menu ===================");
        System.out.println("----------------------------------------------------");
    }
}
