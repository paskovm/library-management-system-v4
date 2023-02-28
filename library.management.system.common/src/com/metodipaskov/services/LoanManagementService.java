package com.metodipaskov.services;

import com.metodipaskov.entities.Book;
import com.metodipaskov.entities.Loan;
import com.metodipaskov.entities.actors.Borrower;
import com.metodipaskov.entities.actors.Staff;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LoanManagementService {

    private static LoanManagementService instance;
    private List<Loan> loans;

    private LoanManagementService() {
        this.loans = new ArrayList<>();
    }

    public static LoanManagementService getInstance() {
        if (instance == null) {
            instance = new LoanManagementService();
        }
        return instance;
    }

    public void setLoans(List<Loan> l) {
        loans = l;
    }

    public List<Loan> getAllLoans() {
        return loans;
    }

    public List<Loan> getAllLoanedBooksForUser(Borrower borrower) {
        List<Loan> loansForUser = new ArrayList<>();
        for (Loan loan : loans) {
            if (loan.getBorrower().equals(borrower)) {
                loansForUser.add(loan);
            }
        }
        return loansForUser;
    }

    public List<Loan> getCurrentlyLoanedBooksForUser(Borrower borrower) {
        List<Loan> loansForUser = new ArrayList<>();
        for (Loan loan : loans) {
            if (loan.getBorrower().equals(borrower) && (loan.getReceiver() == null || loan.getReturnDate() == null)) {
                loansForUser.add(loan);
            }
        }
        return loansForUser;
    }

    public List<Loan> getLoansForBook(Book book) {
        List<Loan> bookLoans = new ArrayList<>();
        if (book != null) {
            for (Loan loan : loans) {
                if (loan.getBook().equals(book)) {
                    bookLoans.add(loan);
                }
            }
        }
        return bookLoans;
    }

    public void issueBook(Loan loan) {
        loans.add(loan);
        loan.getBook().setIssued(true);
        loan.getBorrower().addLoan(loan);
    }

    public void removeLoan(Loan loan) {
        loan.getBorrower().removeLoan(loan);
        loans.remove(loan);
    }

    public void returnBook(Borrower borrower, Book book, Staff receiver) {
        for (Loan loan : loans) {
            if (loan.getBorrower().equals(borrower) && loan.getBook().equals(book) &&
                    loan.getReceiver() == null && loan.getReturnDate() == null && !loan.isFinePaid()) {
                loan.setReceiver(receiver);
                loan.setReturnDate(LocalDate.now());
                loan.setFinePaid(true);
                loan.getBorrower().completeLoan(book, receiver);
                book.setIssued(false);
            }
        }
    }

    public void renewBook(Borrower borrower, Book book, Staff issuer) {
        for (Loan loan : loans) {
            if (loan.getBorrower().equals(borrower) && loan.getBook().equals(book) &&
                    loan.getReceiver() == null && loan.getReturnDate() == null && !loan.isFinePaid()) {
                loan.setIssuer(issuer);
                loan.setIssueDate(LocalDate.now());
                loan.getBorrower().updateLoan(book, issuer);
            }
        }
    }

    public void printBorrowersFines(Borrower borrower) {
        List<Loan> usersLoans = new ArrayList<>();
        for (Loan loan : loans) {
            if (borrower.equals(loan.getBorrower())) {
                usersLoans.add(loan);
            }
        }
        if (usersLoans.size() > 0) {
            double finesSum = 0;
            System.out.println(System.lineSeparator() + "======== Following Loans are available for user "
                    + borrower.getFirstName() + " " + borrower.getLastName() + " ========");
            for (Loan loan : usersLoans) {
                if (!loan.isFinePaid()) {
                    loan.printInfo();
                    finesSum += loan.calculateFine();
                }
            }
            System.out.println(System.lineSeparator() + "-----------------------");
            System.out.println("Fine(s) sum is = $" + finesSum);
        }
    }

    public double getBorrowersFine(Borrower borrower, Book book) {
        Loan usersLoan = null;
        for (Loan loan : loans) {
            if (borrower.equals(loan.getBorrower()) && book.equals(loan.getBook())) {
                usersLoan = loan;
                break;
            }
        }

        if (usersLoan == null) {
            return 0;
        }
        return usersLoan.calculateFine();
    }

}
