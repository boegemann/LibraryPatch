package org.ingo.librarypatch.model

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class BookTest {

    @Test
    fun `There are exactly three categories of books - COMIC,NOVEL and REFERENCE`() {
        assert(BookCategory.entries.count() == 3)
        assertNotNull(BookCategory.COMIC)
        assertNotNull(BookCategory.NOVEL)
        assertNotNull(BookCategory.REFERENCE)
    }

    @Test
    fun `I can instantiate a book without specifying borrowed by for a book not checked out`() {
        val book = Book(
            id = "12345",
            isbn = "978-3-16-148410-0",
            title = "German-English Dictionary",
            author = "Fred Meier",
            publisher = "Merriam-Webster",
            bookCategory = BookCategory.REFERENCE
        )

        with(book) {
            assertEquals("12345", id)
            assertEquals("978-3-16-148410-0", isbn)
            assertEquals("German-English Dictionary", title)
            assertEquals("Fred Meier", author)
            assertEquals("Merriam-Webster", publisher)
            assertEquals(BookCategory.REFERENCE, bookCategory)
            assertEquals("", borrowedBy)
        }
    }


    @Test
    fun `I can instantiate a book specifying 'borrowedBy' for a book currently checked out`() {

        val book = Book(
            id = "223344",
            isbn = "978-3-16-148410-0",
            title = "The Hobbit",
            author = "J.R.R. Tolkien",
            publisher = "Harper Collins",
            bookCategory = BookCategory.NOVEL,
            borrowedBy = "12345"
        )

        with(book) {
            assertEquals("223344", id)
            assertEquals("978-3-16-148410-0", isbn)
            assertEquals("The Hobbit", title)
            assertEquals("J.R.R. Tolkien", author)
            assertEquals("Harper Collins", publisher)
            assertEquals(BookCategory.NOVEL, bookCategory)
            assertEquals("12345", borrowedBy)
        }
    }
}