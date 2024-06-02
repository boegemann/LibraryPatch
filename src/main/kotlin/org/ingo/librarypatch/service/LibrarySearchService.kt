package org.ingo.librarypatch.service

import org.ingo.librarypatch.model.Book
import org.ingo.librarypatch.repository.BookRepo


/**
 * Service class dealing with finding books in the library
 */
class LibrarySearchService(private val bookRepository: BookRepo) {

    /**
     * returns list of books for the given author
     */
    fun findBooksForAuthor(author: String): List<Book> {
        require(author.isNotBlank()){"Author has to be a non blank String for the search"}
        return bookRepository.findBooksByAuthor(author.trim())
    }
    /**
     * returns list of books for the given title
     */
    fun findBooksByTitle(title: String): List<Book> {
        require(title.isNotBlank()){"Title has to be a non blank String for the search"}
        return bookRepository.findBooksByTitle(title.trim())
    }

    /**
     * returns the book represented by the given isbn or null if not present
     */
    fun findBookByIsbn(isbn:String): Book? {
        require(isbn.isNotBlank()){"ISBN number has to be a non blank String for the search"}
        return bookRepository.findBookByISBN(isbn.trim())
    }


}