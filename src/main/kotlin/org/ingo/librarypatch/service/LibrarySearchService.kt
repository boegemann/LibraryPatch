package org.ingo.librarypatch.service

import org.ingo.librarypatch.model.Book
import org.ingo.librarypatch.repository.BookRepo

class LibrarySearchService(private val bookRepository: BookRepo) {

    fun findBooksForAuthor(author: String): List<Book> {
        require(author.isNotBlank()){"Author has to be a non blank String for the search"}
        return bookRepository.findBooksByAuthor(author.trim())
    }

    fun findBooksByTitle(title: String): List<Book> {
        require(title.isNotBlank()){"Title has to be a non blank String for the search"}
        return bookRepository.findBooksByTitle(title.trim())
    }

    fun findBookByIsbn(isbn:String): Book? {
        require(isbn.isNotBlank()){"ISBN number has to be a non blank String for the search"}
        return bookRepository.findBookByISBN(isbn.trim())
    }


}