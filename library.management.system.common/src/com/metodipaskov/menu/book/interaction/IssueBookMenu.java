package com.metodipaskov.menu.book.interaction;

import com.metodipaskov.entities.Book;
import com.metodipaskov.entities.Library;
import com.metodipaskov.entities.Loan;
import com.metodipaskov.entities.actors.Borrower;
import com.metodipaskov.entities.actors.Person;
import com.metodipaskov.entities.actors.Staff;
import com.metodipaskov.menu.help.AddUpdateCheckUserMenu;
import com.metodipaskov.services.LoanManagementService;

public class IssueBookMenu extends AddUpdateCheckUserMenu {

    private Library library = Library.getInstance();
    private LoanManagementService loanService = LoanManagementService.getInstance();

    @Override
    public void start() {
        printMenuHeader();

        Person user = getUser();

        if (user != null) {
            if (user instanceof Borrower) {
                Book book = getBook();

                if (book != null) {
                    Staff staff = (Staff) library.getLoggedInPerson();
                    Loan loan = new Loan((Borrower) user, book, staff);
                    loanService.issueBook(loan);
                    System.out.println("Book successfully loaned.");
                    loan.printInfo();
                }

            } else {
                System.out.println("The user you provided is not borrower!");
                user.printInfo();
            }
        }


        library.getMainMenu().start();
    }

    @Override
    public void printMenuHeader() {
        System.out.println(System.lineSeparator() +
                            "----------------------------------------------------");
        System.out.println("===============  Loan a Book Portal  ===============");
        System.out.println("----------------------------------------------------");
    }
}
