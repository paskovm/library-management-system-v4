package com.metodipaskov.menu.book.interaction;

import com.metodipaskov.entities.Book;
import com.metodipaskov.entities.HoldRequest;
import com.metodipaskov.entities.Library;
import com.metodipaskov.entities.actors.Borrower;
import com.metodipaskov.entities.actors.Person;
import com.metodipaskov.menu.help.AddUpdateCheckUserMenu;
import com.metodipaskov.services.HoldRequestManagementService;

public class PlaceBookOnHoldMenu extends AddUpdateCheckUserMenu {

    private Library library = Library.getInstance();
    private HoldRequestManagementService holdRequestService = HoldRequestManagementService.getInstance();

    @Override
    public void start() {
        printMenuHeader();
        Person user;
        Person loggedInPerson = library.getLoggedInPerson();

        if (loggedInPerson instanceof Borrower) {
            user = loggedInPerson;
        } else {
            user = getUser();
        }

        if (user != null) {
            if (user instanceof Borrower) {
                Book book = getBook();
                if (book != null) {
                    HoldRequest holdRequest = new HoldRequest((Borrower) user, book);
                    holdRequestService.createHoldRequest(holdRequest);
                }
            } else {
                System.out.println("The user who requested the hold request is not registered as borrower in the system!");
            }
        }

        library.getMainMenu().start();
    }

    @Override
    public void printMenuHeader() {
        System.out.println(System.lineSeparator() +
                            "----------------------------------------------------");
        System.out.println("===========  Book On Hold Request Portal  ==========");
        System.out.println("----------------------------------------------------");
    }
}
