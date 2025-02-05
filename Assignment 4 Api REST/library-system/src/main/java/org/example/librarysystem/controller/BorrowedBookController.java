package org.example.librarysystem.controller;

import org.example.librarysystem.model.BorrowedBook;
import org.example.librarysystem.service.BorrowedBookService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/borrowed")
public class BorrowedBookController {
    private final BorrowedBookService borrowedBookService;

    public BorrowedBookController(BorrowedBookService borrowedBookService) {
        this.borrowedBookService = borrowedBookService;
    }

    @PostMapping("/borrow")
    public BorrowedBook borrowBook(@RequestParam Long userId, @RequestParam Long bookId) {
        return borrowedBookService.borrowBook(userId, bookId);
    }
}
