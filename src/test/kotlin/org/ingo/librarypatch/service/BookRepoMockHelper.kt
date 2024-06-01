package org.ingo.librarypatch.service

import io.mockk.every
import io.mockk.mockkClass
import org.ingo.librarypatch.model.Book
import org.ingo.librarypatch.model.BookCategory
import org.ingo.librarypatch.repository.BookRepo

object BookRepoMockHelper {
    fun createDefaultRepoMock(): BookRepo {
        val bookRepoMock = mockkClass(BookRepo::class)

        every { bookRepoMock.findBooksByAuthor("Thomas Mann") } returns emptyList()

        every { bookRepoMock.findBooksByAuthor("J.R.R. Tolkien") } returns listOf(
            Book(
                id = "1",
                isbn = "978-0-596-52068-7",
                title = "Lord of the Rings",
                author = "J.R.R. Tolkien",
                publisher = "Penguin Books",
                bookCategory = BookCategory.NOVEL,
                borrowedBy = ""
            ),
            Book(
                id = "2",
                isbn = "0-596-52068-9",
                title = "The Hobbit",
                author = "J.R.R. Tolkien",
                publisher = "Penguin Books",
                bookCategory = BookCategory.NOVEL,
                borrowedBy = "12345"
            )
        )

        every { bookRepoMock.findBooksByTitle("Fifty Shades of Grey") } returns emptyList()
        every { bookRepoMock.findBooksByTitle("German Dictionary") } returns listOf(
            Book(
                id = "3",
                isbn = "978-0877798576",
                title = "German Dictionary",
                author = "Merriam-Webster",
                publisher = "Collins",
                bookCategory = BookCategory.REFERENCE,
                borrowedBy = ""
            ), Book(
                id = "4",
                isbn = "978-0878898576",
                title = "German Dictionary",
                author = " Oxford University Press",
                publisher = "Oxford University Press",
                bookCategory = BookCategory.REFERENCE,
                borrowedBy = ""
            )
        )
        every { bookRepoMock.findBooksByTitle("Lord Of The Rings") } returns listOf(
            Book(
                id = "1",
                isbn = "978-0-596-52068-7",
                title = "Lord of the Rings",
                author = "Tolkien",
                publisher = "Penguin Books",
                bookCategory = BookCategory.NOVEL,
                borrowedBy = ""
            )
        )

        every { bookRepoMock.findBookByISBN("978-0-596-52068-7") } returns
                Book(
                    id = "1",
                    isbn = "978-0-596-52068-7",
                    title = "Lord of the Rings",
                    author = "Tolkien",
                    publisher = "Penguin Books",
                    bookCategory = BookCategory.NOVEL,
                    borrowedBy = ""
                )

        every { bookRepoMock.findBookByISBN("999-0-596-52068-7") } returns
                Book(
                    id = "1",
                    isbn = "978-0-596-52068-7",
                    title = "Lord of the Rings",
                    author = "Tolkien",
                    publisher = "Penguin Books",
                    bookCategory = BookCategory.NOVEL,
                    borrowedBy = "2"
                )

        every { bookRepoMock.findBookByISBN("978-0877798576") } returns
                Book(
                    id = "3",
                    isbn = "978-0877798576",
                    title = "German Dictionary",
                    author = "Merriam-Webster",
                    publisher = "Collins",
                    bookCategory = BookCategory.REFERENCE,
                    borrowedBy = ""
                )

        every { bookRepoMock.findBookByISBN("978-0-596-4444-7") } returns null

        every { bookRepoMock.borrowBook("978-0-596-52068-7", "1") } returns true

        every {bookRepoMock.isCheckedOut("978-0-596-52068-7")} returns false
        every {bookRepoMock.isCheckedOut("999-0-596-52068-9")} returns true

        every { bookRepoMock.countBorrowedBooks() } returns 500

        return bookRepoMock
    }
}