package org.example.librarysystem.service;

import org.example.librarysystem.model.Book;
import org.example.librarysystem.model.BorrowedBook;
import org.example.librarysystem.model.User;
import org.example.librarysystem.repository.BookRepository;
import org.example.librarysystem.repository.BorrowedBookRepository;
import org.example.librarysystem.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class BorrowedBookService {
    private final BorrowedBookRepository borrowedBookRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public BorrowedBookService(BorrowedBookRepository borrowedBookRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.borrowedBookRepository = borrowedBookRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public BorrowedBook borrowBook(Long userId, Long bookId) {
        User user = userRepository.findById(userId).orElse(null);
        Book book = bookRepository.findById(bookId).orElse(null);
        if (user != null && book != null) {
            BorrowedBook borrowedBook = new BorrowedBook();
            borrowedBook.setUser(user);
            borrowedBook.setBook(book);
            borrowedBook.setBorrowDate(LocalDateTime.now());
            return borrowedBookRepository.save(borrowedBook);
        }
        return null;
    }
}
