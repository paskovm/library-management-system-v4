package com.metodipaskov.menu.book.interaction;

import com.metodipaskov.entities.Book;
import com.metodipaskov.entities.Library;
import com.metodipaskov.menu.help.AddUpdateCheckBookMenu;
import com.metodipaskov.services.BookManagementService;

import java.util.ArrayList;
import java.util.List;

public class CheckIssuedBooksMenu extends AddUpdateCheckBookMenu {

    private Library library = Library.getInstance();
    private BookManagementService bookService = BookManagementService.getInstance();

    @Override
    public void start() {
        printMenuHeader();

        List<Book> books = bookService.getAllBooksInLibrary();
        List<Book> issuedBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.isIssued()) {
                issuedBooks.add(book);
            }
        }

        if (books.size() > 0) {
            for (Book book : issuedBooks) {
                book.printInfo();
            }
        } else {
            System.out.println("N/A");
        }

        library.getMainMenu().start();
    }

    @Override
    public void printMenuHeader() {
        System.out.println(System.lineSeparator() +
                            "----------------------------------------------------");
        System.out.println("=================  All Issued Books  ===============");
        System.out.println("----------------------------------------------------");
    }
}
