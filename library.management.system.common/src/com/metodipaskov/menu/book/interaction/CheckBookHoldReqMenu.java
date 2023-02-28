package com.metodipaskov.menu.book.interaction;

import com.metodipaskov.entities.Book;
import com.metodipaskov.entities.HoldRequest;
import com.metodipaskov.entities.Library;
import com.metodipaskov.menu.help.AddUpdateCheckBookMenu;
import com.metodipaskov.services.HoldRequestManagementService;
import com.metodipaskov.utils.HoldReqComparator;

import java.util.Collections;
import java.util.List;

public class CheckBookHoldReqMenu extends AddUpdateCheckBookMenu {

    private Library library = Library.getInstance();
    private HoldRequestManagementService holdRequestService = HoldRequestManagementService.getInstance();

    @Override
    public void start() {
        printMenuHeader();
        Book book = getBook();

        if (book != null) {
            List<HoldRequest> holdRequests = holdRequestService.getHoldRequestsForBook(book);
            if (holdRequests.size() > 0) {
                Collections.sort(holdRequests, new HoldReqComparator());

                for (HoldRequest holdRequest : holdRequests) {
                    holdRequest.printInfo();
                }
            } else {
                System.out.println("No hold requests available for book \'" + book.getTitle() + "\'.");
            }
        }

        library.getMainMenu().start();
    }

    @Override
    public void printMenuHeader() {
        System.out.println(System.lineSeparator() +
                "----------------------------------------------------");
        System.out.println("=============  Book Hold Requests Info  ============");
        System.out.println("----------------------------------------------------");
    }
}
