package org.ingo.librarypatch.service

import org.ingo.librarypatch.model.BookCategory
import org.ingo.librarypatch.repository.BookRepo
import org.ingo.librarypatch.repository.UserRepo

/**
 * Service class dealing with checking books in and out of the library
 */
class CheckoutService(val bookRepo: BookRepo, val userRepo: UserRepo) {


    /**
     * Given the ISBN number of a book return a boolean indicating
     * whether the book is currently available and not checked out
     * Will throw Exception if the book is not part of the library
     */
    fun isBookAvailableToCheckOut(isbn: String): Boolean {
        require(isbn.isNotBlank()) { "ISBN must not be blank" }
        return !bookRepo.isCheckedOut(isbn)
    }

    /**
     * checkout a Book for a user, using passed in isbn and user id
     * Assumes the user has checked the availability beforehand
     * Throws Exceptions if
     * - userId or isbn are blank or represent non exiting entities
     * - book is already checked out
     * - book is a reference book
     */
    // @Transactional
    fun checkoutBook(isbn: String, userId: String) {
        require(userId.isNotBlank()) { "User id must not be blank" }
        require(userRepo.userIdExists(userId.trim())) { "No such user" }
        require(isbn.isNotBlank()) { "ISBN must not be blank" }

        val book = bookRepo.findBookByISBN(isbn.trim())
            ?: throw IllegalArgumentException("No book with ISBN: $isbn available in the library")

        if (book.borrowedBy.isNotBlank()) throw IllegalArgumentException("Book with ISBN: $isbn is already checked out")

        if (book.bookCategory == BookCategory.REFERENCE) {
            // ambiguous requirement
            // As a library user, I should be to prevented from borrowing reference books, so that they are always available.
            // Should the owner be allowed to check a reference book out??
            // The below commented section allows the owner to take one of them out
            // the actual implementation stops the ower as well so they are 'always available'

            /*
            val user = userRepo.findUserById(userId)
            if (user.role!=UserRole.OWNER){
                throw IllegalArgumentException("Reference books can not be checked out by customers")
            }
            */
            throw IllegalArgumentException("Reference books can not be checked out")

        }

        bookRepo.borrowBook(isbn.trim(), userId)

    }

}