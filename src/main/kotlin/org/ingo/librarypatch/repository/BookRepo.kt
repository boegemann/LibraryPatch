package org.ingo.librarypatch.repository

import org.ingo.librarypatch.model.Book
import org.ingo.librarypatch.model.BookCategory

interface BookRepo {
    /**
     * Creates a new book record in the database and returns the created Book object
     */
    fun createBook(
        isbn: String,
        title: String,
        author: String,
        publisher: String,
        bookCategory: BookCategory,
        borrowedBy: String = ""
    ): Book

    /**
     * Finds all books by a specific author
     */
    fun findBooksByAuthor(author: String): List<Book>

    /**
     * Finds all books with the given Title
     */
    fun findBooksByTitle(title: String): List<Book>

    /**
     * Find a specific book by it's ISBN
     * Returns null if the book is not present
     */
    fun findBookByISBN(author: String): Book?

    /**
     * Check whether a book is already checked out by ISBN
     */
    fun isCheckedOut(isbn: String): Boolean

    /**
     * Marks the book as borrowed by the specified user id
     * Returns true if correctly checked out or false
     * if the book can't be checked out as already checked out
     */
    fun borrowBook(bookId: String, userId: String): Boolean

    /**
     * Marks the book as returned to the library
     */
    fun markBookAsReturned(bookId: String)

    /**
     * Returns the number of books lend out, e.g. books with a non-empty borrowedBy attribute
     */
    fun countBorrowedBooks(): Int

}