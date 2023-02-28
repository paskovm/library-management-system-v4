package com.metodipaskov.menu.user.interaction;

import com.metodipaskov.entities.Library;
import com.metodipaskov.entities.actors.Borrower;
import com.metodipaskov.entities.actors.Person;
import com.metodipaskov.menu.help.AddUpdateCheckUserMenu;
import com.metodipaskov.services.LoanManagementService;

public class CheckBorrowerFineMenu extends AddUpdateCheckUserMenu {

    private Library library = Library.getInstance();
    private LoanManagementService loanService = LoanManagementService.getInstance();

    @Override
    public void start() {
        printMenuHeader();

        Person user = getUser();

        if (user != null) {
            if (user instanceof Borrower) {
                loanService.printBorrowersFines((Borrower) user);
            } else {
                System.out.println("The provided user is not borrower!");
            }
        }

        library.getMainMenu().start();
    }

    @Override
    public void printMenuHeader() {
        System.out.println(System.lineSeparator() +
                "----------------------------------------------------");
        System.out.println("===============  Borrowers Fine Info  ==============");
        System.out.println("----------------------------------------------------");
    }
}
