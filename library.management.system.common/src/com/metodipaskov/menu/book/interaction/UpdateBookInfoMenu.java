package com.metodipaskov.menu.book.interaction;

import com.metodipaskov.entities.Book;
import com.metodipaskov.entities.Library;
import com.metodipaskov.menu.help.AddUpdateCheckBookMenu;

public class UpdateBookInfoMenu extends AddUpdateCheckBookMenu {

    private Library library = Library.getInstance();

    @Override
    public void start() {
        printMenuHeader();

        Book book = getBook();
        if (book != null) {
            updateBookInfo(book);
        }

        library.getMainMenu().start();
    }

    @Override
    public void printMenuHeader() {
        System.out.println(System.lineSeparator() +
                            "----------------------------------------------------");
        System.out.println("================  Update Book Portal  ==============");
        System.out.println("----------------------------------------------------");
    }
}
