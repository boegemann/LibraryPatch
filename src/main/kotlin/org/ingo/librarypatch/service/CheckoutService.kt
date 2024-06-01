package org.ingo.librarypatch.service

import org.ingo.librarypatch.model.BookCategory
import org.ingo.librarypatch.repository.BookRepo
import org.ingo.librarypatch.repository.UserRepo

class CheckoutService(val bookRepo: BookRepo, val userRepo: UserRepo) {


    fun isBookAvailableToCheckOut(isbn: String): Boolean {
        require(isbn.isNotBlank()) { "ISBN must not be blank" }
        return !bookRepo.isCheckedOut(isbn)
    }

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