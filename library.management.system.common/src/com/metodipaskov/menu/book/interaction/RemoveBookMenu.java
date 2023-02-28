package com.metodipaskov.menu.book.interaction;

import com.metodipaskov.entities.Book;
import com.metodipaskov.entities.HoldRequest;
import com.metodipaskov.entities.Library;
import com.metodipaskov.entities.Loan;
import com.metodipaskov.menu.help.AddUpdateCheckBookMenu;
import com.metodipaskov.services.BookManagementService;
import com.metodipaskov.services.HoldRequestManagementService;
import com.metodipaskov.services.LoanManagementService;
import com.metodipaskov.utils.DatabaseInteractions;

import java.util.ListIterator;

public class RemoveBookMenu extends AddUpdateCheckBookMenu {

    private Library library = Library.getInstance();
    private BookManagementService bookService = BookManagementService.getInstance();
    private LoanManagementService loanService = LoanManagementService.getInstance();
    private HoldRequestManagementService holdRequestService = HoldRequestManagementService.getInstance();

    @Override
    public void start() {
        printMenuHeader();

        Book book = getBook();
        if (book != null) {
            int result = DatabaseInteractions.removeBook(book.getBookId());
            if (result < 0) { // if no book id is returned that means it is successfully deleted
                ListIterator<Loan> loans = loanService.getLoansForBook(book).listIterator();
                while (loans.hasNext()) {
                    loanService.removeLoan(loans.next());
                }

                ListIterator<HoldRequest> holdRequests = holdRequestService.getHoldRequestsForBook(book).listIterator();
                while (holdRequests.hasNext()) {
                    holdRequestService.removeHoldRequest(holdRequests.next());
                }

                bookService.removeBook(book);
            }
        }

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
