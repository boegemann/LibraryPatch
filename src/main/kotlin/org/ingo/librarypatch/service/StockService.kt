package org.ingo.librarypatch.service

import org.ingo.librarypatch.model.UserRole
import org.ingo.librarypatch.repository.BookRepo
import org.ingo.librarypatch.repository.UserRepo

class StockService(val bookRepo: BookRepo, val userRepo: UserRepo) {

    fun getCountOfCheckedOutBooks(userId: String): Int {
        require(userRepo.findUserById(userId).role == UserRole.OWNER) { "Service not available" }
        return bookRepo.countBorrowedBooks()
    }
}