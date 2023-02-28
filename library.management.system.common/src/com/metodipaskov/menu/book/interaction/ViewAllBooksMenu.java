package com.metodipaskov.menu.book.interaction;

import com.metodipaskov.entities.Book;
import com.metodipaskov.entities.Library;
import com.metodipaskov.menu.help.AddUpdateCheckBookMenu;
import com.metodipaskov.services.BookManagementService;

import java.util.List;

public class ViewAllBooksMenu extends AddUpdateCheckBookMenu {

    private Library library = Library.getInstance();
    private BookManagementService bookManagementService = BookManagementService.getInstance();

    @Override
    public void start() {
        printMenuHeader();

        List<Book> books = bookManagementService.getAllBooksInLibrary();
        for (Book book : books)
            book.printInfo();

        library.getMainMenu().start();
    }

    @Override
    public void printMenuHeader() {
        System.out.println(System.lineSeparator() +
                            "----------------------------------------------------");
        System.out.println("==============  All Books in Library  ==============");
        System.out.println("----------------------------------------------------");
    }
}
